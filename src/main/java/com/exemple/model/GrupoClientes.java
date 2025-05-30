package com.exemple.model;

import java.util.ArrayList;
import java.util.List;

public class GrupoClientes implements Atendivel {
    private int id;
    private String nomeGrupo;
    private List<Cliente> clientes;
    private List<Pedido> pedidos;

    public GrupoClientes(int id, String nomeGrupo) {
        this.id = id;
        this.nomeGrupo = nomeGrupo;
        this.clientes = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    @Override
    public String getIdentificacao() {
        return "Grupo: " + nomeGrupo;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public int getId() {
        return id;
    }

}
