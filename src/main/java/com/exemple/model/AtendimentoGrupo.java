package com.exemple.model;

public class AtendimentoGrupo extends Atendimento {
    private GrupoClientes grupo;

    public AtendimentoGrupo(GrupoClientes grupo, Pedido pedido) {
        super(pedido);
        if (grupo == null) throw new IllegalArgumentException("Grupo não pode ser nulo.");
        this.grupo = grupo;
    }

    public GrupoClientes getGrupo() {
        return grupo;
    }
}
