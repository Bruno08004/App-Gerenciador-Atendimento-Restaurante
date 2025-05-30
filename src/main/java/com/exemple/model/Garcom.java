package com.exemple.model;

import com.exemple.util.Turno;

public class Garcom {
    private int id;
    private String nome;
    private Turno turnoAtual;
    private FilaDeAtendimento<AtendimentoIndividual> filaAtendimentoCliente;
    private FilaDeAtendimento<AtendimentoGrupo> filaAtendimentoGrupo;

    public Garcom(int id, String nome, Turno turnoAtual) {
        this.id = id;
        this.nome = nome;
        this.turnoAtual = turnoAtual;
        this.filaAtendimentoCliente = new FilaDeAtendimento<>();
        this.filaAtendimentoGrupo = new FilaDeAtendimento<>();
    }

    public boolean podeAtenderMaisCliente() {
        return filaAtendimentoCliente.tamanho() < 5;
    }

    public boolean podeAtenderMaisGrupo() {
        return filaAtendimentoGrupo.tamanho() < 3;
    }

    public void iniciarAtendimentoCliente(Cliente cliente) {
        if (podeAtenderMaisCliente()) {
            AtendimentoIndividual atendimento = new AtendimentoIndividual(cliente);
            atendimento.iniciarAtendimento();
            filaAtendimentoCliente.enfileirar(atendimento);
        }
    }

    public void iniciarAtendimentoGrupo(GrupoClientes grupo) {
        if (podeAtenderMaisGrupo()) {
            AtendimentoGrupo atendimento = new AtendimentoGrupo(grupo);
            atendimento.iniciarAtendimento();
            filaAtendimentoGrupo.enfileirar(atendimento);
        }
    }

    public void reordenarFilaClientes() {
        filaAtendimentoCliente.reordenarPorPrioridade();
        filaAtendimentoGrupo.reordenarPorPrioridade();
    }

    public Turno getTurnoAtual() {
        return turnoAtual;
    }

    public void setTurnoAtual(Turno turnoAtual) {
        this.turnoAtual = turnoAtual;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}