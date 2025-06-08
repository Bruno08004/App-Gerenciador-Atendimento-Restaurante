package com.exemple.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.exemple.model.Restaurante;

class TelaPrincipalControllerTest {

    private TelaPrincipalController controller;
    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        controller = new TelaPrincipalController();
        restaurante = new Restaurante("Restaurante Teste");
        controller.setRestaurante(restaurante);
    }

    @Test
    void setRestauranteAtribuiCorretamente() {
        Restaurante novoRestaurante = new Restaurante("Novo Restaurante");
        controller.setRestaurante(novoRestaurante);
        // Não há getter, mas podemos testar se não lança exceção e aceita o objeto
        assertDoesNotThrow(() -> controller.setRestaurante(novoRestaurante));
    }

    // Como os métodos handle... são fortemente acoplados ao JavaFX, testamos apenas a lógica de atribuição
    // e garantimos que o método setRestaurante aceita diferentes instâncias sem lançar exceção.

    // Para testar a navegação, seria necessário um teste de integração com JavaFX, que foge do escopo unitário.
}