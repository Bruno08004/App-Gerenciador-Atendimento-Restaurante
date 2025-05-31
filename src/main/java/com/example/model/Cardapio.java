package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class Cardapio {
    private List<ItemPedido> itens;

    public Cardapio() {
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    public ItemPedido buscarItem(String nome) {
        return itens.stream().filter(i -> i.getNome().equalsIgnoreCase(nome)).findFirst().orElse(null);
    }
}
