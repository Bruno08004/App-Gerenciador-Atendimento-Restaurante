package com.exemple.model;

import com.exemple.util.TipoCliente;

import java.util.ArrayList;
import java.util.List;

public class Cliente implements Atendivel {
    private String nome;
    private TipoCliente tipo;
    private List<ItemPedido> preferencias;

    public Cliente(String nome, String telefone, TipoCliente tipo) {
        this.nome = nome;
        this.tipo = tipo;
        this.preferencias = new ArrayList<>();
    }

    public void adicionarPreferencial(ItemPedido item) {
        preferencias.add(item);
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    @Override
    public String getIdentificacao() {
        return "Cliente: " + nome;
    }

    public String getNome() {
        return nome;
    }

    public List<ItemPedido> getPreferencias() {
        return preferencias;
    }
}
