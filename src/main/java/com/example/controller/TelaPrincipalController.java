package com.example.controller;

import com.example.model.Restaurante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class TelaPrincipalController {
    private Restaurante restaurante;

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    @FXML
    public void handleGerenciarGarcons(ActionEvent event) {
        showAlert("Gerenciar Garçons", "Funcionalidade em desenvolvimento");
    }

    @FXML
    public void handleIniciarAtendimento(ActionEvent event) {
        showAlert("Iniciar Atendimento", "Funcionalidade em desenvolvimento");
    }

    @FXML
    public void handleVisualizarCardapio(ActionEvent event) {
        showAlert("Visualizar Cardápio", "Funcionalidade em desenvolvimento");
    }

    @FXML
    public void handleControleTurno(ActionEvent event) {
        showAlert("Controle de Turno", "Funcionalidade em desenvolvimento");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}