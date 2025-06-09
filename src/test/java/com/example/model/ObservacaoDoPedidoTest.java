package com.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObservacaoDoPedidoTest {

    @Test
    void testConstrutorComDescricaoValida() {
        ObservacaoDoPedido obs = new ObservacaoDoPedido("Sem cebola");
        assertEquals("Sem cebola", obs.getDescricao());
    }

    @Test
    void testConstrutorComDescricaoNula() {
        assertThrows(IllegalArgumentException.class, () -> new ObservacaoDoPedido(null));
    }

    @Test
    void testConstrutorComDescricaoVazia() {
        assertThrows(IllegalArgumentException.class, () -> new ObservacaoDoPedido(""));
    }

    @Test
    void testConstrutorComDescricaoEspacos() {
        assertThrows(IllegalArgumentException.class, () -> new ObservacaoDoPedido("   "));
    }
}