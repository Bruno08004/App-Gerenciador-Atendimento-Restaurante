package com.example.model;

import java.util.*;

public class FilaDeAtendimento<T extends Atendimento> {
    private PriorityQueue<Atendimento> fila;

    public FilaDeAtendimento() {
        this.fila = new PriorityQueue<>(Comparator.comparing(a -> a.getStatus().ordinal()));
    }

    public void adicionarAtendimento(Atendimento atendimento) {
        fila.add(atendimento);
    }

    public Atendimento removerAtendimento() {
        return fila.poll();
    }

    public int tamanho() {
        return fila.size();
    }

    public void reordenarFila() {
        List<Atendimento> lista = new ArrayList<>(fila);
        fila.clear();
        fila.addAll(lista);
    }

    public PriorityQueue<Atendimento> getFila() {
        return fila;
    }
}
