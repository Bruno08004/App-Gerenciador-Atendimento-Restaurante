package com.exemple.model;

import com.exemple.util.Status;

public class AtendimentoIndividual extends Atendimento {
    private Cliente cliente;

    public AtendimentoIndividual(Cliente cliente, Pedido pedido) {
        this.cliente = cliente;
        this.pedido = pedido;
        this.status = Status.AGUARDANDO_PEDIDO;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
