package com.exemple.model;

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

    public void removerItem(ItemPedido item) {
        itens.remove(item);
    }

    public List<ItemPedido> getItens() {
        return itens;
    }
}
