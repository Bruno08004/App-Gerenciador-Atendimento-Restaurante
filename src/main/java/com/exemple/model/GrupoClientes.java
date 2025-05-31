package com.exemple.model;

import com.exemple.util.TipoCliente;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class GrupoClientes implements Atendivel {
    private int id;
    private String nomeGrupo;
    private List<Cliente> clientes;
    private List<Pedido> pedidos;
    private LocalTime horaChegada;
    private String observacoesGerais;

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

    public LocalTime getHoraChegada() {
        return horaChegada;
    }

    public void setHoraChegada(LocalTime horaChegada) {
        this.horaChegada = horaChegada;
    }


    public String getObservacoesGerais() {
        return observacoesGerais;
    }

    public void setObservacoesGerais(String observacoesGerais) {
        this.observacoesGerais = observacoesGerais;
    }
}