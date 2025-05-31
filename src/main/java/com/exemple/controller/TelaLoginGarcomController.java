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

public class TelaLoginGarcomController {

    @FXML
    private TextField idField;
    @FXML
    private TextField nomeField;

    private Restaurante restaurante;

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String nome = nomeField.getText().trim();

            Garcom garcomLogado = restaurante.validarLoginGarcom(id, nome);

            if (garcomLogado != null) {
                new Alert(Alert.AlertType.INFORMATION, "Login realizado com sucesso! Bem-vindo(a), " + garcomLogado.getNome() + ".").showAndWait();

                // Redireciona para a nova tela de funções do garçom logado
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaGarcomLogado.fxml"));
                Parent root = loader.load();

                TelaGarcomLogadoController garcomLogadoController = loader.getController();
                garcomLogadoController.setRestaurante(restaurante);
                garcomLogadoController.setGarcomLogado(garcomLogado); // Passa o garçom logado

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Funções do Garçom: " + garcomLogado.getNome());
                stage.show();
            } else {
                new Alert(Alert.AlertType.ERROR, "ID ou Nome do garçom inválidos.").showAndWait();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Por favor, digite um ID válido (apenas números).").showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar as funções do garçom.").showAndWait();
        }
    }

    @FXML
    public void handleRegistrar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaCadastroGarcom.fxml"));
            Parent root = loader.load();

            TelaCadastroGarcomController cadastroController = loader.getController();
            cadastroController.setRestaurante(restaurante);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cadastro de Garçom");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar a tela de cadastro.").showAndWait();
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
            new Alert(Alert.AlertType.ERROR, "Erro ao voltar para o menu principal.").showAndWait();
        }
    }
}