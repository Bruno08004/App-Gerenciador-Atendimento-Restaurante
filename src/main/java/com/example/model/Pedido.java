package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int contador = 0;
    private int id;
    private List<ItemPedido> itens;

    public Pedido() {
        this.id = ++contador;
        this.itens = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void adicionarItem(ItemPedido item) {
        if (item != null && !itens.isEmpty()) {
            itens.add(item);
        }
    }

    public void removerItem(ItemPedido item) {
        itens.remove(item);
    }

    public double calcularTotal() {
        return itens.stream()
                .mapToDouble(ItemPedido::calcularSubtotal)  // Lambda
                .sum();
    }
}