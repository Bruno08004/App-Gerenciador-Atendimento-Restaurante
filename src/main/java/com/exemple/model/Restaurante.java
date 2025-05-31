package com.exemple.model;

import com.exemple.util.Turno;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um restaurante com garçons, cardápio e controle de turnos.
 */
public class Restaurante {
    private final String nome;
    private final List<Garcom> garcons;
    private final List<ItemPedido> cardapio;
    private final List<Atendimento> historicoAtendimentos;
    private Turno turnoAtual;

    public Restaurante(String nome) {
        this.nome = nome;
        this.garcons = new ArrayList<>();
        this.cardapio = new ArrayList<>();
        this.historicoAtendimentos = new ArrayList<>();
    }

    public void iniciarTurno(Turno turno) {
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

    public Garcom buscarGarcomPorId(int id) {
        for (Garcom g : garcons) {
            if (g.getId() == id) return g;
        }
        return null;
    }

    public void distribuirCliente(Cliente cliente) {
        for (Garcom g : garcons) {
            if (g.podeAtenderMaisCliente()) {
                g.iniciarAtendimentoCliente(cliente);
                break;
            }
        }
    }

    public void distribuirGrupo(GrupoClientes grupo) {
        for (Garcom g : garcons) {
            if (g.podeAtenderMaisGrupo()) {
                g.iniciarAtendimentoGrupo(grupo);
                break;
            }
        }
    }

    public void registrarAtendimentoFinalizado(Atendimento atendimento) {
        historicoAtendimentos.add(atendimento);
    }


    // Métodos auxiliares
    public void adicionarGarcom(Garcom garcom) {
        garcons.add(garcom);
    }

    public void adicionarAoCardapio(ItemPedido item) {
        cardapio.add(item);
    }

    // Getters
    public List<ItemPedido> getCardapio() {
        return cardapio;
    }

    public List<Garcom> getGarcons() {
        return garcons;
    }

    public String getNome() {
        return nome;
    }

    public Turno getTurnoAtual() {
        return turnoAtual;
    }

    public List<Atendimento> getHistoricoAtendimentos() {
        return historicoAtendimentos;
    }
}
