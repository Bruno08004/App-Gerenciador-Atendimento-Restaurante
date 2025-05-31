package com.exemple.controller;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaCardapioController {
    public void exibir() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        ListView<String> listaItens = new ListView<>();
        Button btnAdicionar = new Button("Adicionar Item");
        Button btnRemover = new Button("Remover Item");
        TextField filtro = new TextField();
        filtro.setPromptText("Filtrar por nome...");

        layout.getChildren().addAll(filtro, listaItens, btnAdicionar, btnRemover);

        stage.setTitle("Cardápio");
        stage.setScene(new Scene(layout, 300, 300));
        stage.show();
    }
}
