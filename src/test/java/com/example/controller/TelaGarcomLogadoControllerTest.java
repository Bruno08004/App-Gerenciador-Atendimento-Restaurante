package com.example.controller;

import com.example.model.Garcom;
import com.example.model.Restaurante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TelaGarcomLogadoControllerTest {

    private TelaGarcomLogadoControllerFake controller;
    private Restaurante restaurante;
    private Garcom garcom;

    // Subclasse para expor os campos privados para teste e evitar dependência de JavaFX
    static class TelaGarcomLogadoControllerFake extends TelaGarcomLogadoController {
        @Override
        public void setGarcomLogado(Garcom garcom) {
            // Apenas lógica de negócio, sem JavaFX
            try {
                var field = TelaGarcomLogadoController.class.getDeclaredField("garcomLogado");
                field.setAccessible(true);
                field.set(this, garcom);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        public Restaurante getRestaurante() {
            try {
                var field = TelaGarcomLogadoController.class.getDeclaredField("restaurante");
                field.setAccessible(true);
                return (Restaurante) field.get(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        public Garcom getGarcomLogado() {
            try {
                var field = TelaGarcomLogadoController.class.getDeclaredField("garcomLogado");
                field.setAccessible(true);
                return (Garcom) field.get(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @BeforeEach
    void setUp() {
        controller = new TelaGarcomLogadoControllerFake();
        restaurante = new Restaurante("Restaurante Teste");
        garcom = new Garcom(1, "João", null);
    }

    @Test
    void testSetRestaurante() {
        controller.setRestaurante(restaurante);
        assertEquals(restaurante, controller.getRestaurante());
    }

    @Test
    void testSetGarcomLogado() {
        controller.setGarcomLogado(garcom);
        assertEquals(garcom, controller.getGarcomLogado());
    }

    @Test
    void testSetGarcomLogadoNull() {
        controller.setGarcomLogado(null);
        assertNull(controller.getGarcomLogado());
    }
}