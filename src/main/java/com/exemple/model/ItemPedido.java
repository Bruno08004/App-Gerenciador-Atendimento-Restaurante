package com.exemple.model;

import java.util.ArrayList;
import java.util.List;

public class ItemPedido {
    private String nome;
    private int quantidade;
    private double preco;
    private List<ObservacaoDoPedido> observacoes;

    public ItemPedido(String nome, int quantidade, double preco) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.observacoes = new ArrayList<>();
    }

    public void adicionarObservacao(ObservacaoDoPedido obs) {
        observacoes.add(obs);
    }

    public double calcularSubtotal() {
        return quantidade * preco;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public List<ObservacaoDoPedido> getObservacoes() {
        return observacoes;
    }
}