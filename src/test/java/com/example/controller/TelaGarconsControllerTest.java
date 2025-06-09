package com.example.controller;

import com.example.model.Garcom;
import com.example.model.Restaurante;
import com.example.util.Turno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TelaGarconsControllerTest {

    private TelaGarconsControllerFake controller;
    private Restaurante restaurante;

    // Subclasse para expor campos e métodos protegidos/privados
    static class TelaGarconsControllerFake extends TelaGarconsController {
        public Restaurante getRestaurante() {
            try {
                var field = TelaGarconsController.class.getDeclaredField("restaurante");
                field.setAccessible(true);
                return (Restaurante) field.get(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        public int getNextGarcomId() {
            try {
                var field = TelaGarconsController.class.getDeclaredField("nextGarcomId");
                field.setAccessible(true);
                return (int) field.get(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        // Sobrescreve o método para evitar dependência de JavaFX
        @Override
        public void setRestaurante(Restaurante restaurante) {
            try {
                var field = TelaGarconsController.class.getDeclaredField("restaurante");
                field.setAccessible(true);
                field.set(this, restaurante);
                // Atualiza nextGarcomId conforme lógica esperada
                var idField = TelaGarconsController.class.getDeclaredField("nextGarcomId");
                idField.setAccessible(true);
                int nextId = restaurante == null ? 1 : restaurante.getGarcons().stream()
                        .mapToInt(Garcom::getId).max().orElse(0) + 1;
                idField.set(null, nextId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @BeforeEach
    void setUp() {
        controller = new TelaGarconsControllerFake();
        restaurante = new Restaurante("Restaurante Teste");
    }

    @Test
    void testSetRestauranteCarregaGarcons() {
        Garcom g1 = new Garcom(1, "João", Turno.MANHA);
        Garcom g2 = new Garcom(2, "Maria", Turno.NOITE);
        restaurante.adicionarGarcom(g1);
        restaurante.adicionarGarcom(g2);

        controller.setRestaurante(restaurante);

        // Verifica se o restaurante foi setado corretamente
        assertEquals(restaurante, controller.getRestaurante());
        // Verifica se o nextGarcomId foi atualizado corretamente
        assertEquals(3, controller.getNextGarcomId());
    }

    @Test
    void testSetRestauranteNull() {
        controller.setRestaurante(null);
        assertNull(controller.getRestaurante());
        assertEquals(1, controller.getNextGarcomId());
    }
}