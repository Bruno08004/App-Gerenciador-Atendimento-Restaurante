package com.example.model;

import java.time.Duration;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.util.Status;

class AtendimentoTest {

    private Pedido pedidoMock;

    // Classe concreta para testar a classe abstrata Atendimento
    static class AtendimentoConcreto extends Atendimento {
        public AtendimentoConcreto(Pedido pedido) {
            super(pedido);
        }
    }

    @BeforeEach
    void setUp() {
        pedidoMock = new Pedido();
    }

    @Test
    void construtorDeveLancarExcecaoSePedidoNulo() {
        assertThrows(IllegalArgumentException.class, () -> new AtendimentoConcreto(null));
    }

    @Test
    void iniciarAtendimentoDeveLancarExcecaoSeHoraChegadaNula() {
        Atendimento atendimento = new AtendimentoConcreto(pedidoMock);
        assertThrows(NullPointerException.class, () -> atendimento.iniciarAtendimento(null));
    }

    @Test
    void finalizarAtendimentoDeveLancarExcecaoSeNaoIniciado() {
        Atendimento atendimento = new AtendimentoConcreto(pedidoMock);
        assertThrows(NullPointerException.class, atendimento::finalizarAtendimento);
    }

    @Test
    void calcularTempoTotalDeveLancarExcecaoSeNaoFinalizado() {
        Atendimento atendimento = new AtendimentoConcreto(pedidoMock);
        LocalTime chegada = LocalTime.now().minusMinutes(10);
        atendimento.iniciarAtendimento(chegada);
        assertThrows(NullPointerException.class, atendimento::calcularTempoTotal);
    }

    @Test
    void cicloCompletoAtendimento() {
        Atendimento atendimento = new AtendimentoConcreto(pedidoMock);
        LocalTime chegada = LocalTime.now().minusMinutes(15);
        atendimento.iniciarAtendimento(chegada);

        assertEquals(Status.EM_ATENDIMENTO, atendimento.getStatus());
        assertNotNull(atendimento.getInicio());
        assertNotNull(atendimento.getTempoDeEspera());
        assertTrue(atendimento.getTempoDeEspera().toMinutes() >= 0);

        atendimento.finalizarAtendimento();

        assertEquals(Status.FINALIZADO, atendimento.getStatus());
        assertNotNull(atendimento.getFim());
        assertNotNull(atendimento.getTempoDeAtendimento());
        assertTrue(atendimento.getTempoDeAtendimento().toMillis() >= 0);

        Duration total = atendimento.calcularTempoTotal();
        assertNotNull(total);
        assertTrue(total.toMillis() >= 0);
    }
}