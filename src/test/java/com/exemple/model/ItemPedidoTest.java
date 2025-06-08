package com.exemple.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemPedidoTest {

    private ItemPedido item;

    @BeforeEach
    void setUp() {
        item = new ItemPedido("Pizza", 2, 30.0);
    }

    @Test
    void testConstrutorValido() {
        assertEquals("Pizza", item.getNome());
        assertEquals(2, item.getQuantidade());
        assertEquals(30.0, item.getPreco());
        assertTrue(item.getObservacoes().isEmpty());
    }

    @Test
    void testConstrutorNomeInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new ItemPedido(null, 1, 10.0));
        assertThrows(IllegalArgumentException.class, () -> new ItemPedido("", 1, 10.0));
        assertThrows(IllegalArgumentException.class, () -> new ItemPedido("   ", 1, 10.0));
    }

    @Test
    void testConstrutorQuantidadeInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new ItemPedido("Pizza", 0, 10.0));
        assertThrows(IllegalArgumentException.class, () -> new ItemPedido("Pizza", -1, 10.0));
    }

    @Test
    void testConstrutorPrecoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new ItemPedido("Pizza", 1, -5.0));
    }

    @Test
    void testAdicionarObservacaoValida() {
        ObservacaoDoPedido obs = new ObservacaoDoPedido("Sem cebola");
        item.adicionarObservacao(obs);
        assertEquals(1, item.getObservacoes().size());
        assertEquals("Sem cebola", item.getObservacoes().get(0).getDescricao());
    }

    @Test
    void testAdicionarObservacaoNula() {
        assertThrows(NullPointerException.class, () -> item.adicionarObservacao(null));
    }

    @Test
    void testCalcularSubtotal() {
        assertEquals(60.0, item.calcularSubtotal(), 0.001);
    }
}