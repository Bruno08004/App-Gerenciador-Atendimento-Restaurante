package com.example.application;

import com.example.controller.TelaInicialController;
import com.example.model.Garcom;
import com.example.model.ItemPedido;
import com.example.model.Restaurante;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Restaurante restaurante;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Inicializa dados de exemplo
        restaurante = new Restaurante("Meu Restaurante");
        restaurante.adicionarGarcom(new Garcom(1, "João", null));
        restaurante.adicionarGarcom(new Garcom(2, "Maria", null));
        restaurante.adicionarAoCardapio(new ItemPedido("Pizza", 1, 45.00));
        restaurante.adicionarAoCardapio(new ItemPedido("Refrigerante", 1, 7.50));

        // Carrega a tela inicial
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/TelaInicial.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        // Configura o controller
        TelaInicialController controller = fxmlLoader.getController();
        controller.setRestaurante(restaurante);

        // Configura a janela principal
        primaryStage.setTitle("Sistema de Restaurante");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}