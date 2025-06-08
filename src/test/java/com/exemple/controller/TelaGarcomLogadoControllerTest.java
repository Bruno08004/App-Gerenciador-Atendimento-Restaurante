package com.exemple.controller;

import com.exemple.model.Garcom;
import com.exemple.model.Restaurante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de lógica de negócio para TelaGarcomLogadoController, sem JavaFX e sem Mockito.
 */
public class TelaGarcomLogadoControllerTest {

    private TelaGarcomLogadoController controller;
    private Restaurante restaurante;
    private Garcom garcom;

    // Classe fake para simular Label sem JavaFX
    public static class FakeLabel {
        private String text;
        public void setText(String text) { this.text = text; }
        public String getText() { return text; }
    }

    @BeforeEach
    public void setUp() throws Exception {
        controller = new TelaGarcomLogadoController();
        restaurante = new Restaurante("Restaurante Teste");
        garcom = new Garcom(1, "João", null);

        // Injeta o FakeLabel no lugar do labelGarcomLogadoNome usando reflection
        var field = controller.getClass().getDeclaredField("labelGarcomLogadoNome");
        field.setAccessible(true);
        field.set(controller, new FakeLabel());
    }

    @Test
    public void testSetRestaurante() {
        controller.setRestaurante(restaurante);
        // Não há getter, mas não deve lançar exceção
    }

    @Test
    public void testSetGarcomLogado() throws Exception {
        controller.setGarcomLogado(garcom);
        // Verifica se o texto do FakeLabel foi atualizado corretamente
        var field = controller.getClass().getDeclaredField("labelGarcomLogadoNome");
        field.setAccessible(true);
        FakeLabel label = (FakeLabel) field.get(controller);
        assertEquals("Garçom: João", label.getText());
    }

    @Test
    public void testSetGarcomLogadoNull() {
        controller.setGarcomLogado(null);
        // Não deve lançar exceção
    }
}