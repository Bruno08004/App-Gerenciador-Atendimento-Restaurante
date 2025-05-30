package com.exemple.model;

import com.exemple.util.Status;

public class AtendimentoGrupo extends Atendimento {
    private GrupoClientes grupo;

    public AtendimentoGrupo(GrupoClientes grupo, Pedido pedido) {
        this.grupo = grupo;
        this.pedido = pedido;
        this.status = Status.AGUARDANDO_PEDIDO;
    }

    public GrupoClientes getGrupo() {
        return grupo;
    }
}
