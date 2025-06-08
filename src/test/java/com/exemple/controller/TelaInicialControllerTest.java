package com.exemple.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.exemple.model.Restaurante;

class TelaInicialControllerTest {

    private TelaInicialController controller;
    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        controller = new TelaInicialController();
        restaurante = new Restaurante("Restaurante Teste");
    }

    @Test
    void setRestauranteAtribuiCorretamente() {
        controller.setRestaurante(restaurante);
        // Não há getter, mas podemos garantir que não lança exceção e aceita o objeto
        assertDoesNotThrow(() -> controller.setRestaurante(restaurante));
    }

    // Como os métodos de navegação dependem do JavaFX, testamos apenas a lógica de atribuição.
    // Para testar navegação e exibição de erro seria necessário um teste de integração com JavaFX.
}