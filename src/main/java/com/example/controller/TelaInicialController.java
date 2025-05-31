package com.example.controller;

import com.example.model.Restaurante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class TelaInicialController {
    private Restaurante restaurante;

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    @FXML
    public void handleIniciar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MenuPrincipal.fxml"));
            Parent root = loader.load();

            TelaPrincipalController controller = loader.getController();
            controller.setRestaurante(restaurante);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Menu Principal");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erro ao carregar o menu principal: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}