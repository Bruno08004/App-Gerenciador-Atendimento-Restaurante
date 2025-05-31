package com.exemple.controller;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaGarconsController {
    public void exibir() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        ListView<String> listaGarcons = new ListView<>();
        Button btnAdicionar = new Button("Adicionar Garçom");
        Button btnAlterarTurno = new Button("Alterar Turno");

        layout.getChildren().addAll(listaGarcons, btnAdicionar, btnAlterarTurno);

        stage.setTitle("Gerenciar Garçons");
        stage.setScene(new Scene(layout, 300, 300));
        stage.show();
    }
}

