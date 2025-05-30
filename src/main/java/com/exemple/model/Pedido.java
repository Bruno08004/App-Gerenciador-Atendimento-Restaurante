package com.exemple.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private List<ItemPedido> itens;
    private List<ObservacaoDoPedido> observacoes;

    public Pedido(int id) {
        this.id = id;
        this.itens = new ArrayList<>();
        this.observacoes = new ArrayList<>();
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    public void adicionarObservacao(ObservacaoDoPedido observacao) {
        observacoes.add(observacao);
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public List<ObservacaoDoPedido> getObservacoes() {
        return observacoes;
    }

    public int getId() {
        return id;
    }
}