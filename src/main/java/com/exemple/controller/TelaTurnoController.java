package com.exemple.controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaTurnoController {
    public void exibir() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        Button btnIniciarTurno = new Button("Iniciar Turno");
        Button btnEncerrarTurno = new Button("Encerrar Turno");

        layout.getChildren().addAll(btnIniciarTurno, btnEncerrarTurno);

        stage.setTitle("Controle de Turno");
        stage.setScene(new Scene(layout, 250, 150));
        stage.show();
    }
}
