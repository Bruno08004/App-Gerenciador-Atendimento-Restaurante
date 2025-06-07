package com.exemple.controller;

import com.exemple.model.Restaurante;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste para {@link TelaCadastroGarcomController}.
 */
public class TelaCadastroGarcomControllerTest {

    private TelaCadastroGarcomController controller;
    private Restaurante restaurante;

    @BeforeEach
    public void setUp() {
        // Inicializa o JavaFX Toolkit
        new JFXPanel();

        controller = new TelaCadastroGarcomController();
        restaurante = new Restaurante("Restaurante Teste");
        controller.setRestaurante(restaurante);

        // Simula o campo de texto do nome
        controller.nomeCompletoField = new TextField();
    }

    @Test
    public void testHandleCadastrar_NomeVazio() {
        controller.nomeCompletoField.setText("");
        // Não deve lançar exceção ao tentar cadastrar com nome vazio
        assertDoesNotThrow(() -> controller.handleCadastrar(new ActionEvent()));
    }

    @Test
    public void testHandleCadastrar_NomeValido() {
        controller.nomeCompletoField.setText("João da Silva");
        assertDoesNotThrow(() -> controller.handleCadastrar(new ActionEvent()));
        // O garçom deve ter sido adicionado ao restaurante
        assertEquals(1, restaurante.getGarcons().size());
        assertEquals("João da Silva", restaurante.getGarcons().get(0).getNome());
    }

    @Test
    public void testSetRestaurante() {
        Restaurante novoRestaurante = new Restaurante("Outro Restaurante");
        controller.setRestaurante(novoRestaurante);
        assertEquals(novoRestaurante, controller.restaurante);
    }
}