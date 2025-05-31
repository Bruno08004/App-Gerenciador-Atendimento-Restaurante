package com.exemple.controller;

import com.exemple.model.Garcom;
import com.exemple.model.Restaurante;
import com.exemple.util.Turno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Optional;

public class TelaGarconsController {

    @FXML
    private TableView<Garcom> tableViewGarcons;
    @FXML
    private TableColumn<Garcom, Integer> colId;
    @FXML
    private TableColumn<Garcom, String> colNome;
    @FXML
    private TableColumn<Garcom, Turno> colTurno;

    private ObservableList<Garcom> garconsObservableList;
    private Restaurante restaurante;
    private static int nextGarcomId = 1;

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        if (this.restaurante != null) {
            carregarGarcons();
        }
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTurno.setCellValueFactory(new PropertyValueFactory<>("turnoAtual"));

        garconsObservableList = FXCollections.observableArrayList();
        tableViewGarcons.setItems(garconsObservableList);
    }

    private void carregarGarcons() {
        if (restaurante != null) {
            garconsObservableList.clear();
            garconsObservableList.addAll(restaurante.getGarcons());
            nextGarcomId = restaurante.getGarcons().stream().mapToInt(Garcom::getId).max().orElse(0) + 1;
        }
    }

    @FXML
    public void handleAdicionarGarcom(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Adicionar Garçom");
        dialog.setHeaderText("Novo Garçom");
        dialog.setContentText("Nome do Garçom:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nome -> {
            if (!nome.trim().isEmpty()) {
                Garcom novoGarcom = new Garcom(nextGarcomId++, nome, null);
                if (restaurante != null) {
                    restaurante.adicionarGarcom(novoGarcom);
                    carregarGarcons();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Erro: Instância de Restaurante não definida.").showAndWait();
                }
            }
        });
    }

    @FXML
    public void handleAlterarTurno(ActionEvent event) {
        Garcom garcomSelecionado = tableViewGarcons.getSelectionModel().getSelectedItem();
        if (garcomSelecionado != null) {
            ChoiceDialog<Turno> dialog = new ChoiceDialog<>(garcomSelecionado.getTurnoAtual(), Turno.values());
            dialog.setTitle("Alterar Turno");
            dialog.setHeaderText("Alterar Turno para " + garcomSelecionado.getNome());
            dialog.setContentText("Escolha o novo turno:");

            Optional<Turno> result = dialog.showAndWait();
            result.ifPresent(turno -> {
                garcomSelecionado.setTurnoAtual(turno);
                tableViewGarcons.refresh();
            });
        } else {
            new Alert(Alert.AlertType.WARNING, "Selecione um garçom para alterar o turno.").showAndWait();
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
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao voltar para o menu principal: " + e.getMessage()).showAndWait();
        }
    }
}