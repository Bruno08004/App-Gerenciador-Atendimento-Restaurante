package com.exemple.model;

import com.exemple.util.TipoCliente;

import java.util.*;

public class FilaDeAtendimento<T extends Atendimento> {
    private List<T> elementos;

    public FilaDeAtendimento() {
        elementos = new ArrayList<>();
    }

    public void enfileirar(T atendimento) {
        elementos.add(atendimento);
    }

    public T desenfileirar() {
        return elementos.isEmpty() ? null : elementos.remove(0);
    }

    public void reordenarPorPrioridade() {
        elementos.sort((a1, a2) -> {
            TipoCliente t1 = null, t2 = null;

            if (a1 instanceof AtendimentoIndividual) {
                t1 = ((AtendimentoIndividual) a1).getCliente().getTipo();
            } else if (a1 instanceof AtendimentoGrupo grupo1 && grupo1.getGrupo().getClientes().size() > 0) {
                t1 = grupo1.getGrupo().getClientes().get(0).getTipo();
            }

            if (a2 instanceof AtendimentoIndividual) {
                t2 = ((AtendimentoIndividual) a2).getCliente().getTipo();
            } else if (a2 instanceof AtendimentoGrupo grupo2 && grupo2.getGrupo().getClientes().size() > 0) {
                t2 = grupo2.getGrupo().getClientes().get(0).getTipo();
            }

            return Integer.compare(t2.ordinal(), t1.ordinal());
        });
    }

    public int tamanho() {
        return elementos.size();
    }

    public List<T> getElementos() {
        return elementos;
    }
}
