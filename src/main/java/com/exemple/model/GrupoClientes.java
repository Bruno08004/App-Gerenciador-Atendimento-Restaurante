package com.exemple.model;

import com.exemple.util.TipoCliente;

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

    public int getId() {
        return id;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public void removerPedido(Pedido pedido) {
        pedidos.remove(pedido);
    }

    @Override
    public String getNome() { return nomeGrupo; }

    @Override
    public TipoCliente getTipoCliente() {
        return clientes.stream().anyMatch(c -> c.getTipoCliente() == TipoCliente.PRIORITARIO) ? TipoCliente.PRIORITARIO : TipoCliente.COMUM;
    }

    @Override
    public List<String> getPreferencias() {
        return clientes.stream().flatMap(c -> c.getPreferencias().stream()).distinct().toList();
    }
}