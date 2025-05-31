package com.exemple.application;

import com.exemple.controller.TelaInicialController;
import com.exemple.model.Cliente;
import com.exemple.model.Garcom;
import com.exemple.model.GrupoClientes;
import com.exemple.model.ItemPedido;
import com.exemple.model.Restaurante;
import com.exemple.util.TipoCliente;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalTime;

public class Main extends Application {
    private Restaurante restaurante;

    @Override
    public void start(Stage primaryStage) throws Exception {
        restaurante = new Restaurante("Meu Restaurante");
        // Adiciona garçons iniciais para teste (alguns com IDs específicos)
        restaurante.adicionarGarcom(new Garcom(restaurante.gerarNovoGarcomId(), "João Silva", null));
        restaurante.adicionarGarcom(new Garcom(restaurante.gerarNovoGarcomId(), "Maria Oliveira", null));
        restaurante.adicionarGarcom(new Garcom(1001, "Carlos Souza", null)); // Garçom com ID pré-definido

        restaurante.adicionarAoCardapio(new ItemPedido("Pizza Margherita", 1, 45.00));
        restaurante.adicionarAoCardapio(new ItemPedido("Refrigerante Coca-Cola", 1, 7.50));
        restaurante.adicionarAoCardapio(new ItemPedido("Lasanha Bolonhesa", 1, 38.00));

        // Adicionar alguns clientes e grupos à fila de espera geral para teste
        Cliente c1 = new Cliente(restaurante.gerarNovoClienteId(), "Ana Paula", TipoCliente.COMUM);
        c1.setHoraChegada(LocalTime.now().minusMinutes(10));
        restaurante.getFilaDeEsperaGeral().add(c1);

        Cliente c2 = new Cliente(restaurante.gerarNovoClienteId(), "Pedro Souza", TipoCliente.PRIORITARIO);
        c2.setHoraChegada(LocalTime.now().minusMinutes(5));
        restaurante.getFilaDeEsperaGeral().add(c2);

        GrupoClientes g1 = new GrupoClientes(restaurante.gerarNovoGrupoId(), "Família Garcia");
        g1.adicionarCliente(new Cliente(restaurante.gerarNovoClienteId(), "João G.", TipoCliente.COMUM));
        g1.adicionarCliente(new Cliente(restaurante.gerarNovoClienteId(), "Maria G.", TipoCliente.COMUM));
        g1.setHoraChegada(LocalTime.now().minusMinutes(8));
        restaurante.getFilaDeEsperaGeral().add(g1);


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/TelaInicial.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        TelaInicialController telaInicialController = fxmlLoader.getController();
        telaInicialController.setRestaurante(restaurante);

        primaryStage.setTitle("Sistema de Gerenciamento de Restaurante");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}