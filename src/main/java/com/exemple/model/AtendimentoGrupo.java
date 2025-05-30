package com.exemple.model;

import com.exemple.util.Status;

public class AtendimentoGrupo extends Atendimento {

    private GrupoClientes grupo;

    public AtendimentoGrupo(GrupoClientes grupo) {
        this.grupo = grupo;
        this.status = Status.AGUARDANDO_PEDIDO;
    }

    public GrupoClientes getGrupo() {
        return grupo;
    }
}
