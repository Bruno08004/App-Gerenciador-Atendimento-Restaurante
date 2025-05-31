package com.exemple.controller;

import com.exemple.model.Cliente;
import com.exemple.model.GrupoClientes;
import com.exemple.model.Restaurante;
import com.exemple.model.Pedido; // Importar Pedido
import com.exemple.model.ItemPedido; // Importar ItemPedido
import com.exemple.util.TipoCliente;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea; // Importar TextArea

import java.io.IOException;
import java.time.LocalTime;
import java.util.Optional;

public class TelaClienteController {

    @FXML
    private TextField nomeField;
    @FXML
    private ComboBox<TipoCliente> tipoComboBox;
    @FXML
    private TextField pedidoIdField; // Campo para ID do pedido
    @FXML
    private Label labelStatusPedido; // Label para status do pedido
    @FXML
    private TextArea textAreaDetalhesPedido; // Área para detalhes do pedido

    private Restaurante restaurante;

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        tipoComboBox.setItems(FXCollections.observableArrayList(TipoCliente.values()));
        tipoComboBox.getSelectionModel().selectFirst();
        limparDetalhesPedido(); // Limpa a área de detalhes ao iniciar
    }

    @FXML
    public void handleRegistrarChegada(ActionEvent event) {
        String nome = nomeField.getText().trim();
        TipoCliente tipo = tipoComboBox.getSelectionModel().getSelectedItem();

        if (nome.isEmpty() || tipo == null) {
            new Alert(Alert.AlertType.WARNING, "Por favor, preencha seu nome e selecione o tipo de cliente.").showAndWait();
            return;
        }

        Cliente novoCliente = new Cliente(restaurante.gerarNovoClienteId(), nome, tipo);
        novoCliente.setHoraChegada(LocalTime.now());
        restaurante.getFilaDeEsperaGeral().add(novoCliente); // Adiciona na fila geral do restaurante

        new Alert(Alert.AlertType.INFORMATION, "Olá, " + nome + "! Sua chegada foi registrada. Por favor, aguarde ser chamado(a).").showAndWait();

        // Opcional: Limpar campos após o registro
        nomeField.clear();
        tipoComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void handleRegistrarGrupo(ActionEvent event) {
        Dialog<GrupoClientes> dialog = new Dialog<>();
        dialog.setTitle("Registrar Chegada de Grupo");
        dialog.setHeaderText("Cadastre as informações do seu Grupo");

        ButtonType registrarButtonType = new ButtonType("Registrar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(registrarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nomeGrupoField = new TextField();
        nomeGrupoField.setPromptText("Nome do Grupo (Ex: Família Silva)");
        Spinner<Integer> numClientesSpinner = new Spinner<>(1, 100, 1); // Número de pessoas no grupo

        grid.add(new Label("Nome do Grupo:"), 0, 0);
        grid.add(nomeGrupoField, 1, 0);
        grid.add(new Label("Número de Pessoas:"), 0, 1);
        grid.add(numClientesSpinner, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == registrarButtonType) {
                if (!nomeGrupoField.getText().trim().isEmpty() && numClientesSpinner.getValue() > 0) {
                    GrupoClientes novoGrupo = new GrupoClientes(restaurante.gerarNovoGrupoId(), nomeGrupoField.getText().trim());
                    for (int i = 0; i < numClientesSpinner.getValue(); i++) {
                        novoGrupo.adicionarCliente(new Cliente(0, "Membro " + (i + 1), TipoCliente.COMUM));
                    }
                    novoGrupo.setHoraChegada(LocalTime.now());
                    return novoGrupo;
                }
            }
            return null;
        });

        Optional<GrupoClientes> result = dialog.showAndWait();
        result.ifPresent(grupo -> {
            restaurante.getFilaDeEsperaGeral().add(grupo); // Adiciona na fila geral
            new Alert(Alert.AlertType.INFORMATION, "A chegada do Grupo '" + grupo.getNomeGrupo() + "' com " + grupo.getClientes().size() + " pessoas foi registrada! Por favor, aguardem ser chamados.").showAndWait();
        });
    }

    @FXML
    public void handleBuscarPedido(ActionEvent event) {
        limparDetalhesPedido();

        try {
            int pedidoId = Integer.parseInt(pedidoIdField.getText().trim());
            Pedido pedidoEncontrado = restaurante.buscarPedidoPorId(pedidoId);

            if (pedidoEncontrado != null) {
                // Buscar o status do atendimento associado ao pedido
                Optional<String> statusOptional = restaurante.getHistoricoAtendimentos().stream()
                        .filter(at -> at.getPedido().getId() == pedidoEncontrado.getId())
                        .map(at -> at.getStatus().toString())
                        .findFirst();

                String status = statusOptional.orElse("Em Andamento");
                labelStatusPedido.setText("Status: " + status);

                // Montar os detalhes do pedido
                StringBuilder detalhes = new StringBuilder();
                detalhes.append("ID do Pedido: ").append(pedidoEncontrado.getId()).append("\n");
                detalhes.append("Itens:\n");

                for (ItemPedido item : pedidoEncontrado.getItens()) {
                    detalhes.append("- ").append(item.getNome())
                            .append(" (x").append(item.getQuantidade())
                            .append(") - R$ ").append(String.format("%.2f", item.calcularSubtotal()))
                            .append("\n");

                    if (!item.getObservacoes().isEmpty()) {
                        detalhes.append("  Observações: ");
                        item.getObservacoes().forEach(obs -> detalhes.append(obs.getDescricao()).append("; "));
                        detalhes.append("\n");
                    }
                }

                detalhes.append("\nTOTAL: R$ ").append(String.format("%.2f", pedidoEncontrado.calcularTotal()));
                textAreaDetalhesPedido.setText(detalhes.toString());

            } else {
                // Pedido não encontrado
                labelStatusPedido.setText("Status: Pedido não encontrado");
                textAreaDetalhesPedido.setText("Não foi possível encontrar um pedido com o ID " + pedidoId + ".");
                new Alert(Alert.AlertType.INFORMATION, "Pedido não encontrado.").showAndWait();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Por favor, digite um ID de pedido válido (apenas números).").showAndWait();
            limparDetalhesPedido();
        }
    }


    private void limparDetalhesPedido() {
        labelStatusPedido.setText("Status: -");
        textAreaDetalhesPedido.clear();
    }

    @FXML
    public void handleVoltarMenuPrincipal(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MenuPrincipal.fxml"));
            Parent root = loader.load();

            TelaPrincipalController menuController = loader.getController();
            menuController.setRestaurante(restaurante);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu Principal");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao voltar para o menu principal: " + e.getMessage()).showAndWait();
        }
    }
}