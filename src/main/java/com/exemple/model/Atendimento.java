package com.exemple.model;

import com.exemple.util.Status;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Atendimento {
    protected Pedido pedido;
    protected Duration tempoDeEspera;
    protected Duration tempoDeAtendimento;
    protected Status status;
    protected LocalDateTime inicio;
    protected LocalDateTime fim;

    public void iniciarAtendimento() {
        inicio = LocalDateTime.now();
        status = Status.COMENDO;
    }

    public void finalizarAtendimento() {
        fim = LocalDateTime.now();
        tempoDeAtendimento = Duration.between(inicio, fim);
        status = Status.FINALIZADO;
    }

    public Duration calcularTempoTotal() {
        if (inicio != null && fim != null) {
            return Duration.between(inicio, fim).plus(tempoDeEspera);
        }
        return Duration.ZERO;
    }

    public void alteraStatus(Status novoStatus) {
        this.status = novoStatus;
    }

    public Status getStatus() {
        return status;
    }
}
