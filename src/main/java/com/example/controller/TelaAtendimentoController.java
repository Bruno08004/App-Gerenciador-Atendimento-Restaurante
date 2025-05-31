package com.example.controller;

import com.example.model.*;
import com.example.util.Status;
import com.example.util.TipoCliente;
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
    private ObservableList<Atendivel> filaDeEspera;
    private ObservableList<Atendimento> atendimentosEmAndamento;
    private static int nextClienteId = 1;
    private static int nextGrupoId = 1;

    @FXML
    private ListView<String> listViewFilaEspera;
    @FXML
    private ListView<String> listViewAtendimentosEmAndamento;

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        filaDeEspera = FXCollections.observableArrayList();
        atendimentosEmAndamento = FXCollections.observableArrayList();
        atualizarListas();
    }

    @FXML
    public void initialize() {
        filaDeEspera = FXCollections.observableArrayList();
        listViewFilaEspera.setItems(FXCollections.observableArrayList());
        atendimentosEmAndamento = FXCollections.observableArrayList();
        listViewAtendimentosEmAndamento.setItems(FXCollections.observableArrayList());
    }

    private void atualizarListas() {
        if (restaurante != null) {
            filaDeEspera.clear();
            // Supondo que você terá uma fila de espera geral no restaurante
            // Por enquanto, mostraremos apenas os que foram adicionados via UI
            // filaDeEspera.addAll(restaurante.getFilaDeEsperaGeral());

            atendimentosEmAndamento.clear();
            restaurante.getGarcons().forEach(garcom -> {
                garcom.getFilaAtendimentoIndividual().getFila().forEach(atendimento -> {
                    if (atendimento.getStatus() == Status.EM_ATENDIMENTO || atendimento.getStatus() == Status.AGUARDANDO) {
                        atendimentosEmAndamento.add(atendimento);
                    }
                });
                garcom.getFilaAtendimentoGrupo().getFila().forEach(atendimento -> {
                    if (atendimento.getStatus() == Status.EM_ATENDIMENTO || atendimento.getStatus() == Status.AGUARDANDO) {
                        atendimentosEmAndamento.add(atendimento);
                    }
                });
            });
        }
        atualizarListViewFilaEspera();
        atualizarListViewAtendimentosEmAndamento();
    }

    private void atualizarListViewFilaEspera() {
        listViewFilaEspera.getItems().clear();
        filaDeEspera.forEach(atendivel -> {
            listViewFilaEspera.getItems().add(atendivel.getNome() + " (" + atendivel.getTipoCliente() + ")");
        });
    }

    private void atualizarListViewAtendimentosEmAndamento() {
        listViewAtendimentosEmAndamento.getItems().clear();
        atendimentosEmAndamento.forEach(atendimento -> {
            String nomeClienteOuGrupo = "";
            if (atendimento instanceof AtendimentoIndividual) {
                nomeClienteOuGrupo = ((AtendimentoIndividual) atendimento).getCliente().getNome();
            } else if (atendimento instanceof AtendimentoGrupo) {
                nomeClienteOuGrupo = ((AtendimentoGrupo) atendimento).getGrupo().getNomeGrupo();
            }
            listViewAtendimentosEmAndamento.getItems().add(nomeClienteOuGrupo + " - Status: " + atendimento.getStatus());
        });
    }

    @FXML
    public void handleNovoCliente(ActionEvent event) {
        Dialog<Cliente> dialog = new Dialog<>();
        dialog.setTitle("Novo Cliente");
        dialog.setHeaderText("Cadastrar Novo Cliente");

        ButtonType cadastrarButtonType = new ButtonType("Cadastrar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(cadastrarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome do Cliente");
        ComboBox<TipoCliente> tipoComboBox = new ComboBox<>(FXCollections.observableArrayList(TipoCliente.values()));
        tipoComboBox.getSelectionModel().selectFirst();

        grid.add(new Label("Nome:"), 0, 0);
        grid.add(nomeField, 1, 0);
        grid.add(new Label("Tipo:"), 0, 1);
        grid.add(tipoComboBox, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == cadastrarButtonType) {
                if (!nomeField.getText().trim().isEmpty() && tipoComboBox.getSelectionModel().getSelectedItem() != null) {
                    Cliente novoCliente = new Cliente(nextClienteId++, nomeField.getText().trim(), tipoComboBox.getSelectionModel().getSelectedItem());
                    novoCliente.setHoraChegada(LocalTime.now());
                    return novoCliente;
                }
            }
            return null;
        });

        Optional<Cliente> result = dialog.showAndWait();
        result.ifPresent(cliente -> {
            filaDeEspera.add(cliente);
            atualizarListViewFilaEspera();
            new Alert(Alert.AlertType.INFORMATION, "Cliente " + cliente.getNome() + " cadastrado e adicionado à fila de espera.").showAndWait();
        });
    }

    @FXML
    public void handleNovoGrupo(ActionEvent event) {
        Dialog<GrupoClientes> dialog = new Dialog<>();
        dialog.setTitle("Novo Grupo");
        dialog.setHeaderText("Cadastrar Novo Grupo de Clientes");

        ButtonType cadastrarButtonType = new ButtonType("Cadastrar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(cadastrarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nomeGrupoField = new TextField();
        nomeGrupoField.setPromptText("Nome do Grupo");
        Spinner<Integer> numClientesSpinner = new Spinner<>(1, 100, 1);

        grid.add(new Label("Nome do Grupo:"), 0, 0);
        grid.add(nomeGrupoField, 1, 0);
        grid.add(new Label("Número de Clientes:"), 0, 1);
        grid.add(numClientesSpinner, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == cadastrarButtonType) {
                if (!nomeGrupoField.getText().trim().isEmpty() && numClientesSpinner.getValue() > 0) {
                    GrupoClientes novoGrupo = new GrupoClientes(nextGrupoId++, nomeGrupoField.getText().trim());
                    for (int i = 0; i < numClientesSpinner.getValue(); i++) {
                        novoGrupo.adicionarCliente(new Cliente(0, "Cliente " + (i + 1), TipoCliente.COMUM));
                    }
                    novoGrupo.setHoraChegada(LocalTime.now());
                    return novoGrupo;
                }
            }
            return null;
        });

        Optional<GrupoClientes> result = dialog.showAndWait();
        result.ifPresent(grupo -> {
            filaDeEspera.add(grupo);
            atualizarListViewFilaEspera();
            new Alert(Alert.AlertType.INFORMATION, "Grupo " + grupo.getNomeGrupo() + " cadastrado e adicionado à fila de espera.").showAndWait();
        });
    }

    @FXML
    public void handleDistribuirAtendimento(ActionEvent event) {
        if (restaurante == null) {
            new Alert(Alert.AlertType.ERROR, "Erro: Instância de Restaurante não definida. Não é possível distribuir atendimentos.").showAndWait();
            return;
        }

        if (filaDeEspera.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Não há clientes ou grupos na fila de espera para distribuir.").showAndWait();
            return;
        }

        Atendivel atendivel = filaDeEspera.remove(0);
        try {
            restaurante.distribuirAtendimento(atendivel);
            atualizarListas();
            new Alert(Alert.AlertType.INFORMATION, atendivel.getNome() + " foi distribuído para um garçom disponível.").showAndWait();
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao distribuir atendimento: " + e.getMessage()).showAndWait();
            filaDeEspera.add(0, atendivel); // Coloca de volta na fila se não puder ser distribuído
            atualizarListViewFilaEspera();
        }
    }

    @FXML
    public void handleReordenarFila(ActionEvent event) {
        filaDeEspera.sort(Comparator
                .comparing((Atendivel a) -> a.getTipoCliente() == TipoCliente.PRIORITARIO ? 0 : 1)
                .thenComparing(Atendivel::getNome));
        atualizarListViewFilaEspera();
        new Alert(Alert.AlertType.INFORMATION, "Fila de espera reordenada (prioritários primeiro, depois por nome).").showAndWait();
    }

    @FXML
    public void handleFinalizarAtendimento(ActionEvent event) {
        String selectedAtendimentoString = listViewAtendimentosEmAndamento.getSelectionModel().getSelectedItem();
        if (selectedAtendimentoString == null) {
            new Alert(Alert.AlertType.WARNING, "Selecione um atendimento em andamento para finalizar.").showAndWait();
            return;
        }

        Atendimento atendimentoSelecionado = null;
        for (Atendimento at : atendimentosEmAndamento) {
            String nome = "";
            if (at instanceof AtendimentoIndividual) {
                nome = ((AtendimentoIndividual) at).getCliente().getNome();
            } else if (at instanceof AtendimentoGrupo) {
                nome = ((AtendimentoGrupo) at).getGrupo().getNomeGrupo();
            }
            if ((nome + " - Status: " + at.getStatus()).equals(selectedAtendimentoString)) {
                atendimentoSelecionado = at;
                break;
            }
        }

        if (atendimentoSelecionado != null) {
            if (atendimentoSelecionado.getStatus() == Status.FINALIZADO) {
                new Alert(Alert.AlertType.INFORMATION, "Este atendimento já foi finalizado.").showAndWait();
                return;
            }
            atendimentoSelecionado.finalizarAtendimento();
            restaurante.registrarAtendimentoFinalizado(atendimentoSelecionado);
            atualizarListas();
            new Alert(Alert.AlertType.INFORMATION, "Atendimento finalizado com sucesso!").showAndWait();
        }
    }

    @FXML
    public void handleVoltar(ActionEvent event) {
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