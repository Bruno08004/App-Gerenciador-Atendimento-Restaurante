package com.exemple.model;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.exemple.util.Status;

class FilaDeAtendimentoTest {

    private FilaDeAtendimento<Atendimento> fila;
    private Atendimento atendimento1;
    private Atendimento atendimento2;

    // Classe concreta para testar Atendimento
    static class AtendimentoConcreto extends Atendimento {
        public AtendimentoConcreto(Pedido pedido) {
            super(pedido);
        }
    }

    @BeforeEach
    void setUp() {
        fila = new FilaDeAtendimento<>();
        atendimento1 = new AtendimentoConcreto(new Pedido());
        atendimento2 = new AtendimentoConcreto(new Pedido());
    }

    @Test
    void adicionarAtendimentoNaoPermiteNulo() {
        assertThrows(NullPointerException.class, () -> fila.adicionarAtendimento(null));
    }

    @Test
    void adicionarAtendimentoAdicionaNaFila() {
        fila.adicionarAtendimento(atendimento1);
        assertEquals(1, fila.tamanho());
        assertTrue(fila.getFila().contains(atendimento1));
    }

    @Test
    void removerAtendimentoRemoveComPrioridade() {
        atendimento1.iniciarAtendimento(LocalTime.now().minusMinutes(10));
        atendimento2.iniciarAtendimento(LocalTime.now().minusMinutes(5));
        fila.adicionarAtendimento(atendimento1);
        fila.adicionarAtendimento(atendimento2);

        Atendimento removido = fila.removerAtendimento();
        assertNotNull(removido);
        assertEquals(Status.EM_ATENDIMENTO, removido.getStatus());
        assertEquals(1, fila.tamanho());
    }

    @Test
    void removerAtendimentoEspecificoRemoveCorretamente() {
        fila.adicionarAtendimento(atendimento1);
        fila.adicionarAtendimento(atendimento2);
        fila.removerAtendimentoEspecifico(atendimento1);
        assertFalse(fila.getFila().contains(atendimento1));
        assertEquals(1, fila.tamanho());
    }

    @Test
    void contarAtendimentosAtivosContaSomenteNaoFinalizados() {
        fila.adicionarAtendimento(atendimento1);
        fila.adicionarAtendimento(atendimento2);
        atendimento1.iniciarAtendimento(LocalTime.now());
        atendimento1.finalizarAtendimento();
        assertEquals(1, fila.contarAtendimentosAtivos());
    }

    @Test
    void limparFilaEsvaziaFila() {
        fila.adicionarAtendimento(atendimento1);
        fila.adicionarAtendimento(atendimento2);
        fila.limparFila();
        assertEquals(0, fila.tamanho());
    }

    @Test
    void reordenarFilaMantemElementos() {
        fila.adicionarAtendimento(atendimento1);
        fila.adicionarAtendimento(atendimento2);
        fila.reordenarFila();
        assertEquals(2, fila.tamanho());
        assertTrue(fila.getFila().contains(atendimento1));
        assertTrue(fila.getFila().contains(atendimento2));
    }
}