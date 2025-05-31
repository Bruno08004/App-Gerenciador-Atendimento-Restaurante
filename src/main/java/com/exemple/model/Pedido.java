package com.exemple.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int contador = 0;
    private int id;
    private List<String> itens;

    public Pedido() {
        this.id = ++contador;
        this.itens = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<String> getItens() {
        return itens;
    }

    public void adicionarItem(String item) {
        if (item != null && !item.isEmpty()) {
            itens.add(item);
        }
    }

    public void removerItem(String item) {
        itens.remove(item);
    }

    public double calcularTotal() {
        return itens.stream().mapToDouble(ItemPedido::calcularSubtotal).sum();
    }
}