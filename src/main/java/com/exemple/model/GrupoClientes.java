package com.exemple.model;

import java.util.List;

public class GrupoClientes implements Atendivel {
    private List<Cliente> clientes;
    private String nomeGrupo;

    public GrupoClientes(List<Cliente> clientes, String nomeGrupo) {
        this.clientes = clientes;
        this.nomeGrupo = nomeGrupo;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    @Override
    public int getPrioridade() {
        return clientes.stream().anyMatch(c -> c.getPrioridade() == 0) ? 0 : 1;
    }
}