package com.exemple.model;

public class AtendimentoIndividual extends Atendimento {
    private Cliente cliente;

    public AtendimentoIndividual(Cliente cliente, Pedido pedido) {
        super(pedido);
        if (cliente == null) throw new IllegalArgumentException("Cliente não pode ser nulo.");
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
