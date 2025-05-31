package com.exemple.controller;

import com.exemple.model.Restaurante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaPrincipalController {
    private Restaurante restaurante;

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    @FXML
    public void handleGerenciarGarcons(ActionEvent event) { // Agora "Gerenciar Garçons" no menu
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaGarcons.fxml"));
            Parent root = loader.load();

            TelaGarconsController controller = loader.getController();
            controller.setRestaurante(restaurante);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gerenciar Garçons");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar tela de Garçons: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    public void handleVisualizarCardapio(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaCardapio.fxml"));
            Parent root = loader.load();

            TelaCardapioController controller = loader.getController();
            controller.setRestaurante(restaurante);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cardápio");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar tela de Cardápio: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    public void handleSouGarcom(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaLoginGarcom.fxml"));
            Parent root = loader.load();

            TelaLoginGarcomController loginController = loader.getController();
            loginController.setRestaurante(restaurante);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login do Garçom");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar a tela de login do garçom: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    public void handleSouCliente(ActionEvent event) { // NOVO MÉTODO
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaCliente.fxml"));
            Parent root = loader.load();

            TelaClienteController clienteController = loader.getController();
            clienteController.setRestaurante(restaurante);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Opções do Cliente");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar a tela do cliente: " + e.getMessage()).showAndWait();
        }
    }
}