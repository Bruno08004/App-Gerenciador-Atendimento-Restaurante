package com.exemple.model;

public class ItemPedido {
    private int id;
    private String descricao;
    private String observacoes;

    public ItemPedido(int id, String descricao, String observacoes) {
        this.id = id + 1;
        this.descricao = descricao;
        this.observacoes = observacoes;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getObservacoes() {
        return observacoes;
    }
}

