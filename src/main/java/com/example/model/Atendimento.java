package com.example.model;

import com.example.util.Status;

import java.time.Duration;
import java.time.LocalTime;

public abstract class Atendimento {
    private Pedido pedido;
    private Duration tempoDeEspera;
    private Duration tempoDeAtendimento;
    private Status status;
    private LocalTime inicio;
    private LocalTime fim;

    public Atendimento(Pedido pedido) {
        if (pedido == null) throw new IllegalArgumentException("Pedido não pode ser nulo.");
        this.pedido = pedido;
        this.status = Status.AGUARDANDO;
    }

    public void iniciarAtendimento(LocalTime horaChegada) {
        this.inicio = LocalTime.now();
        this.tempoDeEspera = Duration.between(horaChegada, inicio);
        this.status = Status.EM_ATENDIMENTO;
    }

    public void finalizarAtendimento() {
        this.fim = LocalTime.now();
        this.tempoDeAtendimento = Duration.between(inicio, fim);
        this.status = Status.FINALIZADO;
    }

    public Duration calcularTempoTotal() {
        return Duration.between(inicio, fim);
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Duration getTempoDeEspera() {
        return tempoDeEspera;
    }

    public Duration getTempoDeAtendimento() {
        return tempoDeAtendimento;
    }

    public Status getStatus() {
        return status;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public LocalTime getFim() {
        return fim;
    }
}
