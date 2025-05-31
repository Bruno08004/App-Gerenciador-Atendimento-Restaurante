package com.exemple.controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaPrincipalController extends Application {
    @Override
    public void start(Stage primaryStage) {
        VBox layout = new VBox(10);

        Button btnGarcons = new Button("Gerenciar Garçons");
        Button btnAtendimento = new Button("Iniciar Atendimento");
        Button btnCardapio = new Button("Visualizar Cardápio");
        Button btnTurno = new Button("Iniciar/Encerrar Turno");

        layout.getChildren().addAll(btnGarcons, btnAtendimento, btnCardapio, btnTurno);

        primaryStage.setTitle("Sistema de Restaurante");
        primaryStage.setScene(new Scene(layout, 300, 200));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}