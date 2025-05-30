package com.exemple.model;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class FilaDeAtendimento<T extends Atendimento> {
    private Queue<T> fila;

    public FilaDeAtendimento() {
        this.fila = new LinkedList<>();
    }

    public void enfileirar(T atendimento) {
        fila.offer(atendimento);
    }

    public T desenfileirar() {
        return fila.poll();
    }

    public int tamanho() {
        return fila.size();
    }

    public void reordenarPorPrioridade() {
        if (fila.isEmpty()) return;

        var listaOrdenada = new LinkedList<>(fila);
        listaOrdenada.sort(Comparator.comparingInt(atendimento -> {
            if (atendimento instanceof AtendimentoIndividual) {
                return ((AtendimentoIndividual) atendimento).getCliente().getPrioridade();
            } else if (atendimento instanceof AtendimentoGrupo) {
                return ((AtendimentoGrupo) atendimento).getGrupo().getPrioridade();
            }
            return Integer.MAX_VALUE;
        }));

        fila.clear();
        fila.addAll(listaOrdenada);
    }

    public Queue<T> getFila() {
        return fila;
    }
}
