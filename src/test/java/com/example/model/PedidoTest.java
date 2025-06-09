package com.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
    }

    @Test
    void testIdUnico() {
        Pedido p2 = new Pedido();
        assertNotEquals(pedido.getId(), p2.getId());
    }

    @Test
    void testAdicionarItem() {
        ItemPedido item = new ItemPedido("Pizza", 2, 30.0);
        pedido.adicionarItem(item);
        assertEquals(1, pedido.getItens().size());
        assertEquals(item, pedido.getItens().get(0));
    }

    @Test
    void testAdicionarItemNulo() {
        assertThrows(NullPointerException.class, () -> pedido.adicionarItem(null));
    }

    @Test
    void testRemoverItem() {
        ItemPedido item = new ItemPedido("Pizza", 2, 30.0);
        pedido.adicionarItem(item);
        pedido.removerItem(item);
        assertTrue(pedido.getItens().isEmpty());
    }

    @Test
    void testRemoverItemNulo() {
        assertThrows(NullPointerException.class, () -> pedido.removerItem(null));
    }

    @Test
    void testCalcularTotal() {
        pedido.adicionarItem(new ItemPedido("Pizza", 2, 30.0)); // 60.0
        pedido.adicionarItem(new ItemPedido("Suco", 1, 8.0));   // 8.0
        assertEquals(68.0, pedido.calcularTotal(), 0.001);
    }

    @Test
    void testCalcularTotalSemItens() {
        assertEquals(0.0, pedido.calcularTotal(), 0.001);
    }
}