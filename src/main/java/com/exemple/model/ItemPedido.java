package com.exemple.model;

public class ItemPedido {
    private String nome;
    private double preco;

    public ItemPedido(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}