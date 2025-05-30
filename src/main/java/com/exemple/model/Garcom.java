package com.exemple.model;

import com.exemple.util.Turno;

public class Garcom {
    private String nome;
    private int atendimentosClientes;
    private int atendimentosGrupos;
    private final int LIMITE_CLIENTES = 5;
    private final int LIMITE_GRUPOS = 3;
    private Turno turnoAtual;

    public Garcom(String nome) {
        this.nome = nome;
    }

    public void iniciarNovoTurno(Turno turno) {
        this.turnoAtual = turno;
        this.atendimentosClientes = 0;
        this.atendimentosGrupos = 0;
    }

    public boolean podeAtender(Atendivel atendivel) {
        if (atendivel instanceof Cliente) {
            return atendimentosClientes < LIMITE_CLIENTES;
        } else if (atendivel instanceof GrupoClientes) {
            return atendimentosGrupos < LIMITE_GRUPOS;
        }
        return false;
    }

    public boolean registrarAtendimento(Atendivel atendivel) {
        if (podeAtender(atendivel)) {
            if (atendivel instanceof Cliente) {
                atendimentosClientes++;
            } else if (atendivel instanceof GrupoClientes) {
                atendimentosGrupos++;
            }
            return true;
        }
        return false;
    }

    public String getNome() {
        return nome;
    }

    public Turno getTurnoAtual() {
        return turnoAtual;
    }
}
