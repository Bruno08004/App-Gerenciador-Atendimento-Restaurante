package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CardapioTest {

    private Cardapio cardapio;

    @BeforeEach
    void setUp() {
        cardapio = new Cardapio();
    }

    @Test
    void adicionarItemNaoPermiteNulo() {
        assertThrows(NullPointerException.class, () -> cardapio.adicionarItem(null));
    }

    @Test
    void adicionarItemAdicionaCorretamente() {
        ItemPedido item = new ItemPedido("Pizza", 1, 30.0);
        cardapio.adicionarItem(item);
        assertEquals(item, cardapio.buscarItem("Pizza"));
    }

    @Test
    void buscarItemRetornaNullSeNaoExistir() {
        assertNull(cardapio.buscarItem("Inexistente"));
    }

    @Test
    void buscarItemNaoDiferenciaMaiusculasMinusculas() {
        ItemPedido item = new ItemPedido("Suco", 1, 8.0);
        cardapio.adicionarItem(item);
        assertNotNull(cardapio.buscarItem("suco"));
        assertNotNull(cardapio.buscarItem("SUCO"));
    }
}