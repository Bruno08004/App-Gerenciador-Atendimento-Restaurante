package com.exemple.model;

import com.exemple.util.TipoCliente;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Cliente implements Atendivel{
    private int id;
    private String nome;
    private TipoCliente tipo;
    private List<String> preferencias;
    private LocalTime horaChegada;
    private String observacoesGerais;

    public Cliente(int id, String nome, TipoCliente tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.preferencias = new ArrayList<>();
        this.observacoesGerais = "";
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

    public LocalTime getHoraChegada() {
        return horaChegada;
    }

    public void setHoraChegada(LocalTime horaChegada) {
        this.horaChegada = horaChegada;
    }

    public String getObservacoesGerais() {
        return observacoesGerais;
    }

    public void setObservacoesGerais(String observacoesGerais) {
        this.observacoesGerais = observacoesGerais;
    }
}