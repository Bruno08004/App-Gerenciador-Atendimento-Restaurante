package com.exemple.model;

import com.exemple.util.TipoCliente;

import java.util.ArrayList;
import java.util.List;

public class Cliente implements Atendivel{
    private int id;
    private String nome;
    private TipoCliente tipo;
    private List<String> preferencias;

    public Cliente(int id, String nome, TipoCliente tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.preferencias = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public TipoCliente getTipoCliente() {
        return tipo;
    }

    @Override
    public List<String> getPreferencias() {
        return preferencias;
    }

    public void adicionarPreferencia(String item) {
        preferencias.add(item);
    }

    public void removerPreferencia(String item) {
        preferencias.remove(item);
    }
}