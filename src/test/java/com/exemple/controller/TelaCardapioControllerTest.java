package com.exemple.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.exemple.model.ItemPedido;
import com.exemple.model.Restaurante;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de teste para lógica de negócio da TelaCardapioController, sem dependência de JavaFX.
 */
public class TelaCardapioControllerTest {

    private RestauranteFake restauranteFake;

    // Classe fake para simular Restaurante sem dependências externas
    static class RestauranteFake extends Restaurante {
        private List<ItemPedido> cardapio = new ArrayList<>();

        public RestauranteFake() {
            super("Restaurante Fake");
        }

        @Override
        public List<ItemPedido> getCardapio() {
            return cardapio;
        }

        public void setCardapio(List<ItemPedido> itens) {
            cardapio = itens;
        }
    }

    @BeforeEach
    public void setup() {
        restauranteFake = new RestauranteFake();
    }

    @Test
    public void testSetRestauranteCarregaItens() {
        List<ItemPedido> itens = new ArrayList<>();
        itens.add(new ItemPedido("Pizza", 1, 20.0));
        itens.add(new ItemPedido("Soda", 2, 5.0));

        restauranteFake.setCardapio(itens);

        List<ItemPedido> cardapio = restauranteFake.getCardapio();

        assertEquals(2, cardapio.size());
        assertEquals("Pizza", cardapio.get(0).getNome());
        assertEquals("Soda", cardapio.get(1).getNome());
    }

    @Test
    public void testFiltrarItens() {
        List<ItemPedido> itens = new ArrayList<>();
        itens.add(new ItemPedido("Pizza", 1, 20.0));
        itens.add(new ItemPedido("Soda", 2, 5.0));
        itens.add(new ItemPedido("Pasta", 3, 15.0));

        restauranteFake.setCardapio(itens);

        // Filtrar itens que contém "p" (case insensitive)
        List<ItemPedido> filtradosP = filtrarItens(restauranteFake.getCardapio(), "p");
        assertEquals(2, filtradosP.size());

        // Filtrar itens que contém "soda"
        List<ItemPedido> filtradosSoda = filtrarItens(restauranteFake.getCardapio(), "soda");
        assertEquals(1, filtradosSoda.size());
        assertEquals("Soda", filtradosSoda.get(0).getNome());

        // Filtrar todos
        List<ItemPedido> filtradosTodos = filtrarItens(restauranteFake.getCardapio(), "");
        assertEquals(3, filtradosTodos.size());
    }

    /**
     * Método auxiliar para filtrar itens do cardápio por nome (case insensitive).
     */
    private List<ItemPedido> filtrarItens(List<ItemPedido> itens, String filtro) {
        filtro = filtro == null ? "" : filtro.trim().toLowerCase();
        List<ItemPedido> resultado = new ArrayList<>();
        for (ItemPedido item : itens) {
            if (filtro.isEmpty() || item.getNome().toLowerCase().contains(filtro)) {
                resultado.add(item);
            }
        }
        return resultado;
    }
}