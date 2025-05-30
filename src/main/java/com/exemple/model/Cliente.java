package com.exemple.model;

import com.exemple.util.TipoCliente;


public class Cliente implements Atendivel {
    private String nome;
    private TipoCliente tipo;

    public Cliente(String nome, TipoCliente tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public TipoCliente getTipo() {
        return tipo;
    }


    @Override
    public int getPrioridade() {
        return tipo == TipoCliente.ESPECIAL ? 0 : 1;
    }


}