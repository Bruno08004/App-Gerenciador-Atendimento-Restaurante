package com.exemple.controller;

import com.exemple.model.*;
import com.exemple.util.Status;
import com.exemple.util.TipoCliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Optional;

public class TelaAtendimentoController {

    private Restaurante restaurante;
    private Garcom garcomLogado;

    @FXML
    private ListView<String> listViewFilaEspera;
    @FXML
    private ListView<String> listViewAtendimentosDoGarcom;
    @FXML
    private Label labelGarcomLogadoNome;

    private ObservableList<Atendivel> filaDeEspera;
    private ObservableList<String> listaTextoFilaEspera;
    private ObservableList<Atendimento> atendimentosDoGarcom;
    private ObservableList<String> listaTextoAtendimentosDoGarcom;

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        this.filaDeEspera = FXCollections.observableArrayList(restaurante.getFilaDeEsperaGeral());
        this.listaTextoFilaEspera = FXCollections.observableArrayList();
        atualizarListViewFilaEspera();
    }

    public void setGarcomLogado(Garcom garcom) {
        this.garcomLogado = garcom;
        if (garcomLogado != null) {
            labelGarcomLogadoNome.setText("Garçom: " + garcomLogado.getNome());
            this.atendimentosDoGarcom = FXCollections.observableArrayList();
            this.listaTextoAtendimentosDoGarcom = FXCollections.observableArrayList();
            atualizarListas();
        }
    }

    @FXML
    public void initialize() {
        listViewFilaEspera.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> atualizarBotoes());
        listViewAtendimentosDoGarcom.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> atualizarBotoes());
    }

    private void atualizarBotoes() {
        // Implementação opcional para habilitar/desabilitar botões
    }

    private void atualizarListViewFilaEspera() {
        listaTextoFilaEspera.clear();
        filaDeEspera.forEach(atendivel ->
                listaTextoFilaEspera.add(atendivel.getNome() + " (" + atendivel.getTipoCliente() + ")")
        );
        listViewFilaEspera.setItems(listaTextoFilaEspera);
    }

    private void atualizarListViewAtendimentosDoGarcom() {
        listaTextoAtendimentosDoGarcom.clear();
        atendimentosDoGarcom.forEach(at -> {
            String nome = at instanceof AtendimentoIndividual ?
                    ((AtendimentoIndividual)at).getCliente().getNome() :
                    ((AtendimentoGrupo)at).getGrupo().getNomeGrupo();
            listaTextoAtendimentosDoGarcom.add(nome + " - Status: " + at.getStatus());
        });
        listViewAtendimentosDoGarcom.setItems(listaTextoAtendimentosDoGarcom);
    }

    private void atualizarListas() {
        if (restaurante == null || garcomLogado == null) return;

        filaDeEspera.setAll(restaurante.getFilaDeEsperaGeral());
        atendimentosDoGarcom.clear();

        garcomLogado.getFilaAtendimentoIndividual().getFila().stream()
                .filter(at -> at.getStatus() != Status.FINALIZADO)
                .forEach(atendimentosDoGarcom::add);

        garcomLogado.getFilaAtendimentoGrupo().getFila().stream()
                .filter(at -> at.getStatus() != Status.FINALIZADO)
                .forEach(atendimentosDoGarcom::add);

        atualizarListViewFilaEspera();
        atualizarListViewAtendimentosDoGarcom();
    }

    @FXML
    public void handleAtenderProximoDaFilaGeral(ActionEvent event) {
        if (garcomLogado == null) {
            showAlert(Alert.AlertType.ERROR, "Nenhum garçom logado para distribuir atendimento.");
            return;
        }
        if (restaurante.getFilaDeEsperaGeral().isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Não há clientes ou grupos na fila de espera geral para distribuir.");
            return;
        }

        Atendivel atendivel = restaurante.getFilaDeEsperaGeral().removeFirst();
        try {
            if (atendivel instanceof Cliente cliente) {
                if (garcomLogado.podeAtenderMaisClientesIndividuais()) {
                    cliente.setHoraChegada(LocalTime.now());
                    garcomLogado.atenderCliente(cliente);
                } else {
                    showAlert(Alert.AlertType.WARNING, garcomLogado.getNome() + ": Limite de clientes individuais atingido.");
                    restaurante.getFilaDeEsperaGeral().addFirst(atendivel);
                    return;
                }
            } else if (atendivel instanceof GrupoClientes grupo) {
                if (garcomLogado.podeAtenderMaisGrupos()) {
                    grupo.setHoraChegada(LocalTime.now());
                    garcomLogado.atenderGrupo(grupo);
                } else {
                    showAlert(Alert.AlertType.WARNING, garcomLogado.getNome() + ": Limite de grupos atingido.");
                    restaurante.getFilaDeEsperaGeral().addFirst(atendivel);
                    return;
                }
            }
            atualizarListas();
            showAlert(Alert.AlertType.INFORMATION, atendivel.getNome() + " adicionado à sua fila de atendimentos.");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao atender: " + e.getMessage());
            restaurante.getFilaDeEsperaGeral().addFirst(atendivel);
        }
        atualizarListas();
    }

    @FXML
    public void handleCadastrarAtenderNovoCliente(ActionEvent event) {
        if (garcomLogado == null) {
            showAlert(Alert.AlertType.ERROR, "Nenhum garçom logado para cadastrar e atender.");
            return;
        }
        if (!garcomLogado.podeAtenderMaisClientesIndividuais()) {
            showAlert(Alert.AlertType.WARNING, garcomLogado.getNome() + ": Limite de clientes individuais atingido.");
            return;
        }

        Dialog<Cliente> dialog = createClienteDialog();
        Optional<Cliente> result = dialog.showAndWait();

        result.ifPresent(cliente -> {
            garcomLogado.atenderCliente(cliente);
            atualizarListas();
            showAlert(Alert.AlertType.INFORMATION, "Cliente " + cliente.getNome() + " cadastrado e adicionado à fila.");
        });
    }

    private Dialog<Cliente> createClienteDialog() {
        Dialog<Cliente> dialog = new Dialog<>();
        dialog.setTitle("Cadastrar e Atender Novo Cliente");
        dialog.setHeaderText("Informações do Novo Cliente");

        ButtonType cadastrarButtonType = new ButtonType("Cadastrar e Atender", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(cadastrarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome do Cliente");
        ComboBox<TipoCliente> tipoComboBox = new ComboBox<>(FXCollections.observableArrayList(TipoCliente.values()));
        tipoComboBox.getSelectionModel().selectFirst();
        TextArea observacoesArea = new TextArea();
        observacoesArea.setPromptText("Observações (ex: alergia a glúten)");
        observacoesArea.setWrapText(true);
        observacoesArea.setPrefRowCount(3);

        grid.add(new Label("Nome:"), 0, 0);
        grid.add(nomeField, 1, 0);
        grid.add(new Label("Tipo:"), 0, 1);
        grid.add(tipoComboBox, 1, 1);
        grid.add(new Label("Observações:"), 0, 2);
        grid.add(observacoesArea, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == cadastrarButtonType && !nomeField.getText().trim().isEmpty()) {
                Cliente novoCliente = new Cliente(restaurante.gerarNovoClienteId(),
                        nomeField.getText().trim(), tipoComboBox.getSelectionModel().getSelectedItem());
                novoCliente.setObservacoesGerais(observacoesArea.getText().trim());
                novoCliente.setHoraChegada(LocalTime.now());
                return novoCliente;
            }
            return null;
        });

        return dialog;
    }

    @FXML
    public void handleCadastrarAtenderNovoGrupo(ActionEvent event) {
        if (garcomLogado == null) {
            showAlert(Alert.AlertType.ERROR, "Nenhum garçom logado para cadastrar e atender.");
            return;
        }
        if (!garcomLogado.podeAtenderMaisGrupos()) {
            showAlert(Alert.AlertType.WARNING, garcomLogado.getNome() + ": Limite de grupos atingido.");
            return;
        }

        Dialog<GrupoClientes> dialog = createGrupoDialog();
        Optional<GrupoClientes> result = dialog.showAndWait();

        result.ifPresent(grupo -> {
            garcomLogado.atenderGrupo(grupo);
            atualizarListas();
            showAlert(Alert.AlertType.INFORMATION, "Grupo " + grupo.getNomeGrupo() + " cadastrado e adicionado à fila.");
        });
    }

    private Dialog<GrupoClientes> createGrupoDialog() {
        Dialog<GrupoClientes> dialog = new Dialog<>();
        dialog.setTitle("Cadastrar e Atender Novo Grupo");
        dialog.setHeaderText("Informações do Novo Grupo");

        ButtonType cadastrarButtonType = new ButtonType("Cadastrar e Atender", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(cadastrarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nomeGrupoField = new TextField();
        nomeGrupoField.setPromptText("Nome do Grupo");
        Spinner<Integer> numClientesSpinner = new Spinner<>(1, 100, 1);
        TextArea observacoesArea = new TextArea();
        observacoesArea.setPromptText("Observações (ex: mesa no canto)");
        observacoesArea.setWrapText(true);
        observacoesArea.setPrefRowCount(3);

        grid.add(new Label("Nome do Grupo:"), 0, 0);
        grid.add(nomeGrupoField, 1, 0);
        grid.add(new Label("Número de Pessoas:"), 0, 1);
        grid.add(numClientesSpinner, 1, 1);
        grid.add(new Label("Observações:"), 0, 2);
        grid.add(observacoesArea, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == cadastrarButtonType && !nomeGrupoField.getText().trim().isEmpty()) {
                GrupoClientes novoGrupo = new GrupoClientes(
                        restaurante.gerarNovoGrupoId(), nomeGrupoField.getText().trim());
                novoGrupo.setObservacoesGerais(observacoesArea.getText().trim());
                for (int i = 0; i < numClientesSpinner.getValue(); i++) {
                    Cliente membro = new Cliente(0, "Membro " + (i + 1), TipoCliente.COMUM);
                    membro.setHoraChegada(LocalTime.now());
                    novoGrupo.adicionarCliente(membro);
                }
                novoGrupo.setHoraChegada(LocalTime.now());
                return novoGrupo;
            }
            return null;
        });

        return dialog;
    }

    @FXML
    public void handleReordenarFila(ActionEvent event) {
        restaurante.getFilaDeEsperaGeral().sort(Comparator
                .comparing((Atendivel a) -> a.getTipoCliente() == TipoCliente.PRIORITARIO ? 0 : 1)
                .thenComparing(Atendivel::getNome));
        filaDeEspera.setAll(restaurante.getFilaDeEsperaGeral());
        atualizarListViewFilaEspera();
        showAlert(Alert.AlertType.INFORMATION, "Fila de espera reordenada.");
    }

    @FXML
    public void handleFinalizarAtendimento(ActionEvent event) {
        String selectedString = listViewAtendimentosDoGarcom.getSelectionModel().getSelectedItem();
        if (selectedString == null) {
            showAlert(Alert.AlertType.WARNING, "Selecione um atendimento para finalizar.");
            return;
        }

        Atendimento selecionado = atendimentosDoGarcom.stream()
                .filter(at -> {
                    String nomeExibido = at instanceof AtendimentoIndividual ?
                            ((AtendimentoIndividual)at).getCliente().getNome() :
                            ((AtendimentoGrupo)at).getGrupo().getNomeGrupo();
                    String texto = nomeExibido + " - Status: " + at.getStatus();
                    return texto.equals(selectedString) && at.getStatus() != Status.FINALIZADO;
                })
                .findFirst()
                .orElse(null);

        if (selecionado != null) {
            selecionado.finalizarAtendimento();
            restaurante.registrarAtendimentoFinalizado(selecionado);
            atualizarListas();
            showAlert(Alert.AlertType.INFORMATION, "Atendimento finalizado.");
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Atendimento inválido ou já finalizado.");
        }
    }

    @FXML
    public void handleGerenciarPedido(ActionEvent event) {
        String selectedString = listViewAtendimentosDoGarcom.getSelectionModel().getSelectedItem();
        if (selectedString == null) {
            showAlert(Alert.AlertType.WARNING, "Selecione um atendimento para gerenciar o pedido.");
            return;
        }

        Atendimento selecionado = atendimentosDoGarcom.stream()
                .filter(at -> {
                    String nomeExibido = at instanceof AtendimentoIndividual ?
                            ((AtendimentoIndividual)at).getCliente().getNome() :
                            ((AtendimentoGrupo)at).getGrupo().getNomeGrupo();
                    String texto = nomeExibido + " - Status: " + at.getStatus();
                    return texto.equals(selectedString) && at.getStatus() == Status.EM_ATENDIMENTO;
                })
                .findFirst()
                .orElse(null);

        if (selecionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaGerenciadorPedido.fxml"));
                Parent root = loader.load();

                TelaGerenciarPedidoController controller = loader.getController();
                controller.setRestaurante(restaurante);
                controller.setGarcomLogado(garcomLogado);
                controller.setAtendimentoAtual(selecionado);

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Gerenciar Pedido");
                stage.show();
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erro ao carregar tela de pedido: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Selecione um atendimento em andamento.");
        }
    }

    @FXML
    public void handleVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaGarcomLogado.fxml"));
            Parent root = loader.load();

            TelaGarcomLogadoController controller = loader.getController();
            controller.setRestaurante(restaurante);
            controller.setGarcomLogado(garcomLogado);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Funções do Garçom");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao voltar: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        new Alert(type, message).showAndWait();
    }
}