package com.exemple.model;

import com.exemple.util.Turno;
import com.exemple.util.persistence.Persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Restaurante {
    private final String nome;
    private final List<Garcom> garcons;
    private final List<ItemPedido> cardapio;
    private final List<Atendimento> historicoAtendimentos;
    private Turno turnoAtual;

    private int nextGarcomId = 1;
    private int nextClienteId = 1;
    private int nextGrupoId = 1;

    private final List<Atendivel> filaDeEsperaGeral;

    public Restaurante(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do restaurante não pode ser nulo ou vazio.");
        }
        this.nome = nome;
        this.garcons = new ArrayList<>();
        this.cardapio = new ArrayList<>();
        this.historicoAtendimentos = new ArrayList<>();
        this.filaDeEsperaGeral = new ArrayList<>();
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
        Persistencia.salvarGarcons(garcons);
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
        System.out.println("Nenhum garçom disponível no momento para atender " + atendivel.getNome());
    }

    public void registrarAtendimentoFinalizado(Atendimento atendimento) {
        if (atendimento != null) {
            historicoAtendimentos.add(atendimento);
        }
        for (Garcom g : garcons) {
            if (atendimento instanceof AtendimentoIndividual && g.getFilaAtendimentoIndividual().getFila().contains(atendimento)) {
                g.removerAtendimentoFinalizado(atendimento);
                System.out.println("Atendimento individual de " + ((AtendimentoIndividual) atendimento).getCliente().getNome() + " finalizado e removido da fila do garçom " + g.getNome());
                return;
            } else if (atendimento instanceof AtendimentoGrupo && g.getFilaAtendimentoGrupo().getFila().contains(atendimento)) {
                g.removerAtendimentoFinalizado(atendimento);
                System.out.println("Atendimento de grupo " + ((AtendimentoGrupo) atendimento).getGrupo().getNomeGrupo() + " finalizado e removido da fila do garçom " + g.getNome());
                return;
            }
        }
    }
    public Pedido buscarPedidoPorId(int pedidoId) {
        // 1. Buscar nos atendimentos finalizados
        Optional<Atendimento> atendimentoHistorico = historicoAtendimentos.stream()
                .filter(a -> a.getPedido().getId() == pedidoId)
                .findFirst();
        if (atendimentoHistorico.isPresent()) {
            return atendimentoHistorico.get().getPedido();
        }


        for (Garcom garcom : garcons) {
            Optional<Atendimento> atendimentoIndividualAtivo = garcom.getFilaAtendimentoIndividual().getFila().stream()
                    .filter(a -> a.getPedido().getId() == pedidoId)
                    .findFirst();
            if (atendimentoIndividualAtivo.isPresent()) {
                return atendimentoIndividualAtivo.get().getPedido();
            }

            Optional<Atendimento> atendimentoGrupoAtivo = garcom.getFilaAtendimentoGrupo().getFila().stream()
                    .filter(a -> a.getPedido().getId() == pedidoId)
                    .findFirst();
            if (atendimentoGrupoAtivo.isPresent()) {
                return atendimentoGrupoAtivo.get().getPedido();
            }
        }
        return null; // Pedido não encontrado
    }

    public Garcom validarLoginGarcom(int id, String nome) {
        Optional<Garcom> garcom = garcons.stream()
                .filter(g -> g.getId() == id && g.getNome().equalsIgnoreCase(nome))
                .findFirst();
        return garcom.orElse(null);
    }


    public void adicionarAoCardapio(ItemPedido item) {
        if (item == null) throw new IllegalArgumentException("Item do cardápio não pode ser nulo.");
        cardapio.add(item);
    }

    public int gerarNovoGarcomId() {
        return nextGarcomId++;
    }

    public int gerarNovoClienteId() {
        return nextClienteId++;
    }

    public int gerarNovoGrupoId() {
        return nextGrupoId++;
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

    public List<Atendivel> getFilaDeEsperaGeral() {
        return filaDeEsperaGeral;
    }
}
