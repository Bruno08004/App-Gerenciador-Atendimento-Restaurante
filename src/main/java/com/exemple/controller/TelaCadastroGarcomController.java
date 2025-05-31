package com.exemple.controller;

import com.exemple.model.Garcom;
import com.exemple.model.Restaurante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaCadastroGarcomController {

    @FXML
    private TextField nomeCompletoField;

    private Restaurante restaurante;

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    @FXML
    public void handleCadastrar(ActionEvent event) {
        String nome = nomeCompletoField.getText().trim();

        if (nome.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Por favor, digite o nome completo do garçom.").showAndWait();
            return;
        }

        int novoId = restaurante.gerarNovoGarcomId(); // Usa o gerador de ID do Restaurante
        Garcom novoGarcom = new Garcom(novoId, nome, null); // Inicia sem turno
        restaurante.adicionarGarcom(novoGarcom);

        new Alert(Alert.AlertType.INFORMATION, "Garçom " + nome + " cadastrado com sucesso!\nSeu ID de Login é: " + novoId).showAndWait();

        // Volta para a tela de login após o cadastro
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
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar a tela de login.").showAndWait();
        }
    }

    @FXML
    public void handleVoltarLogin(ActionEvent event) {
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
            new Alert(Alert.AlertType.ERROR, "Erro ao voltar para a tela de login.").showAndWait();
        }
    }
}