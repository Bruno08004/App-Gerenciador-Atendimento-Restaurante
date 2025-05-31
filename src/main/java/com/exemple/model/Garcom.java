package com.exemple.model;

import com.exemple.util.Turno;

public class Garcom {
    private final int id;
    private final String nome;
    private Turno turnoAtual;

    private final FilaDeAtendimento<AtendimentoIndividual> filaAtendimentoIndividual;
    private final FilaDeAtendimento<AtendimentoGrupo> filaAtendimentoGrupo;

    public Garcom(int id, String nome, Turno turnoAtual) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome do garçom não pode ser vazio.");
        this.id = id;
        this.nome = nome;
        this.turnoAtual = turnoAtual;
        this.filaAtendimentoIndividual = new FilaDeAtendimento<>();
        this.filaAtendimentoGrupo = new FilaDeAtendimento<>();
    }

    public boolean podeAtenderMaisClientesIndividuais() {
        return filaAtendimentoIndividual.tamanho() < 5;
    }

    public boolean podeAtenderMaisGrupos() {
        return filaAtendimentoGrupo.tamanho() < 3;
    }

    public void atenderCliente(Cliente cliente) {
        if (!podeAtenderMaisClientesIndividuais()) {
            System.out.println("Limite de atendimentos individuais atingido.");
            return;
        }
        Pedido pedido = new Pedido();
        AtendimentoIndividual atendimento = new AtendimentoIndividual(cliente, pedido);
        atendimento.iniciarAtendimento(cliente.getHoraChegada());
        filaAtendimentoIndividual.adicionarAtendimento(atendimento);
    }

    public void atenderGrupo(GrupoClientes grupo) {
        if (!podeAtenderMaisGrupos()) {
            System.out.println("Limite de atendimentos em grupo atingido.");
            return;
        }
        Pedido pedido = new Pedido();
        AtendimentoGrupo atendimento = new AtendimentoGrupo(grupo, pedido);
        atendimento.iniciarAtendimento(grupo.getHoraChegada());
        filaAtendimentoGrupo.adicionarAtendimento(atendimento);
    }

    public void reordenarFilaIndividuais() {
        filaAtendimentoIndividual.reordenarFila();
    }

    public void reordenarFilaGrupos() {
        filaAtendimentoGrupo.reordenarFila();
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Turno getTurnoAtual() {
        return turnoAtual;
    }

    public void setTurnoAtual(Turno turnoAtual) {
        this.turnoAtual = turnoAtual;
    }

    public FilaDeAtendimento<AtendimentoIndividual> getFilaAtendimentoIndividual() {
        return filaAtendimentoIndividual;
    }

    public FilaDeAtendimento<AtendimentoGrupo> getFilaAtendimentoGrupo() {
        return filaAtendimentoGrupo;
    }
}
