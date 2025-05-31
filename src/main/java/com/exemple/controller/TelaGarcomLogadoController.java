package com.exemple.controller;

import com.exemple.model.Garcom;
import com.exemple.model.Restaurante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaGarcomLogadoController {

    private Restaurante restaurante;
    private Garcom garcomLogado;

    @FXML
    private Label labelNomeGarcom; // Para exibir o nome do garçom logado

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public void setGarcomLogado(Garcom garcom) {
        this.garcomLogado = garcom;
        if (garcomLogado != null) {
            labelNomeGarcom.setText("Garçom: " + garcomLogado.getNome());
        }
    }

    @FXML
    public void handleIniciarAtendimento(ActionEvent event) {
        if (garcomLogado == null) {
            new Alert(Alert.AlertType.WARNING, "Nenhum garçom logado. Por favor, faça login primeiro.").showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaAtendimento.fxml"));
            Parent root = loader.load();

            TelaAtendimentoController controller = loader.getController();
            controller.setRestaurante(restaurante);
            controller.setGarcomLogado(garcomLogado); // Passa o garçom logado para a tela de atendimento

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Atendimento - Garçom: " + garcomLogado.getNome());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar tela de Atendimento: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    public void handleControleTurno(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaTurno.fxml"));
            Parent root = loader.load();

            TelaTurnoController controller = loader.getController();
            controller.setRestaurante(restaurante);
            // Poderíamos passar o garçom logado para a tela de turno se ela precisasse saber quem está gerenciando
            // controller.setGarcomLogado(garcomLogado);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Controle de Turno");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar tela de Turno: " + e.getMessage()).showAndWait();
        }
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
