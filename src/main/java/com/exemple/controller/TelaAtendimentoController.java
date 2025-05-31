package com.exemple.view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaAtendimentoController {
    public void exibir() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        Button btnCadastrarCliente = new Button("Novo Cliente");
        Button btnCadastrarGrupo = new Button("Novo Grupo");
        Button btnDistribuir = new Button("Distribuir Atendimento");
        Button btnReordenar = new Button("Reordenar Fila");
        Button btnFinalizar = new Button("Finalizar Atendimento");

        layout.getChildren().addAll(btnCadastrarCliente, btnCadastrarGrupo, btnDistribuir, btnReordenar, btnFinalizar);

        stage.setTitle("Atendimento");
        stage.setScene(new Scene(layout, 300, 250));
        stage.show();
    }
}
