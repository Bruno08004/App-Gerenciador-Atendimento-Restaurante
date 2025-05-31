package com.example.model;

import com.example.util.Turno;

import java.util.ArrayList;
import java.util.List;

public class Restaurante {
    private final String nome;
    private final List<Garcom> garcons;
    private final List<ItemPedido> cardapio;
    private final List<Atendimento> historicoAtendimentos;
    private Turno turnoAtual;

    public Restaurante(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do restaurante não pode ser nulo ou vazio.");
        }
        this.nome = nome;
        this.garcons = new ArrayList<>();
        this.cardapio = new ArrayList<>();
        this.historicoAtendimentos = new ArrayList<>();
    }

    public void iniciarTurno(Turno turno) {
        if (turno == null) throw new IllegalArgumentException("Turno não pode ser nulo.");
        this.turnoAtual = turno;
        for (Garcom g : garcons) {
            g.setTurnoAtual(turno);
        }
    }

    public void encerrarTurno() {
        for (Garcom g : garcons) {
            g.setTurnoAtual(null);
        }
        this.turnoAtual = null;
    }

    public void adicionarGarcom(Garcom garcom) {
        if (garcom == null) throw new IllegalArgumentException("Garçom não pode ser nulo.");
        garcons.add(garcom);
    }

    public Garcom buscarGarcomPorId(int id) {
        return garcons.stream().filter(g -> g.getId() == id).findFirst().orElse(null);
    }

    public void distribuirAtendimento(Atendivel atendivel) {
        if (atendivel instanceof Cliente cliente) {
            for (Garcom g : garcons) {
                if (g.podeAtenderMaisClientesIndividuais()) {
                    g.atenderCliente(cliente);
                    return;
                }
            }
        } else if (atendivel instanceof GrupoClientes grupo) {
            for (Garcom g : garcons) {
                if (g.podeAtenderMaisGrupos()) {
                    g.atenderGrupo(grupo);
                    return;
                }
            }
        } else {
            throw new IllegalArgumentException("Tipo de atendível desconhecido.");
        }
    }

    public void registrarAtendimentoFinalizado(Atendimento atendimento) {
        if (atendimento != null) {
            historicoAtendimentos.add(atendimento);
        }
    }

    public void adicionarAoCardapio(ItemPedido item) {
        if (item == null) throw new IllegalArgumentException("Item do cardápio não pode ser nulo.");
        cardapio.add(item);
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public List<Garcom> getGarcons() {
        return new ArrayList<>(garcons);
    }

    public List<ItemPedido> getCardapio() {
        return new ArrayList<>(cardapio);
    }

    public List<Atendimento> getHistoricoAtendimentos() {
        return new ArrayList<>(historicoAtendimentos);
    }

    public Turno getTurnoAtual() {
        return turnoAtual;
    }
}
