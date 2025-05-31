package com.example.controller;

import com.example.model.Restaurante;
import com.example.util.Turno;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class TelaTurnoController {

    private Restaurante restaurante;

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    @FXML
    public void handleIniciarTurno(ActionEvent event) {
        if (restaurante == null) {
            new Alert(Alert.AlertType.ERROR, "Erro: Instância de Restaurante não definida.").showAndWait();
            return;
        }

        if (restaurante.getTurnoAtual() != null) {
            new Alert(Alert.AlertType.INFORMATION, "Um turno já está ativo: " + restaurante.getTurnoAtual().name()).showAndWait();
            return;
        }

        ChoiceDialog<Turno> dialog = new ChoiceDialog<>(Turno.MANHA, Turno.values());
        dialog.setTitle("Iniciar Turno");
        dialog.setHeaderText("Selecione o turno para iniciar:");
        dialog.setContentText("Turno:");

        Optional<Turno> result = dialog.showAndWait();
        result.ifPresent(turno -> {
            restaurante.iniciarTurno(turno);
            new Alert(Alert.AlertType.INFORMATION, "Turno " + turno.name() + " iniciado com sucesso!").showAndWait();
        });
    }

    @FXML
    public void handleEncerrarTurno(ActionEvent event) {
        if (restaurante == null) {
            new Alert(Alert.AlertType.ERROR, "Erro: Instância de Restaurante não definida.").showAndWait();
            return;
        }

        if (restaurante.getTurnoAtual() == null) {
            new Alert(Alert.AlertType.INFORMATION, "Nenhum turno ativo para encerrar.").showAndWait();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja encerrar o turno atual (" + restaurante.getTurnoAtual().name() + ")?");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            restaurante.encerrarTurno();
            new Alert(Alert.AlertType.INFORMATION, "Turno encerrado com sucesso!").showAndWait();
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