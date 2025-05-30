package com.exemple.model;

import com.exemple.util.Status;

public class AtendimentoIndividual extends Atendimento {
    private Cliente cliente;

    public AtendimentoIndividual(Cliente cliente) {
        this.cliente = cliente;
        this.status = Status.AGUARDANDO_PEDIDO;
    }


    public Cliente getCliente() {
        return cliente;
    }
}
