package com.example.model;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.util.TipoCliente;

class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1, "João", TipoCliente.COMUM);
    }

    @Test
    void construtorDeveLancarExcecaoSeNomeOuTipoNulos() {
        assertThrows(IllegalArgumentException.class, () -> new Cliente(2, null, TipoCliente.COMUM));
        assertThrows(IllegalArgumentException.class, () -> new Cliente(3, "Maria", null));
    }

    @Test
    void adicionarPreferenciaNaoPermiteNulo() {
        assertThrows(NullPointerException.class, () -> cliente.adicionarPreferencia(null));
    }

    @Test
    void removerPreferenciaNaoPermiteNulo() {
        assertThrows(NullPointerException.class, () -> cliente.removerPreferencia(null));
    }

    @Test
    void adicionarERemoverPreferenciaFunciona() {
        cliente.adicionarPreferencia("Sem lactose");
        assertTrue(cliente.getPreferencias().contains("Sem lactose"));
        cliente.removerPreferencia("Sem lactose");
        assertFalse(cliente.getPreferencias().contains("Sem lactose"));
    }

    @Test
    void getNomeRetornaNomeCorreto() {
        assertEquals("João", cliente.getNome());
    }

    @Test
    void getTipoClienteRetornaTipoCorreto() {
        assertEquals(TipoCliente.COMUM, cliente.getTipoCliente());
    }

    @Test
    void setEGetHoraChegadaFunciona() {
        LocalTime agora = LocalTime.now();
        cliente.setHoraChegada(agora);
        assertEquals(agora, cliente.getHoraChegada());
    }

    @Test
    void setEGetObservacoesGeraisFunciona() {
        cliente.setObservacoesGerais("Cliente alérgico a amendoim");
        assertEquals("Cliente alérgico a amendoim", cliente.getObservacoesGerais());
    }

    @Test
    void preferenciasInicialmenteVazia() {
        assertNotNull(cliente.getPreferencias());
        assertTrue(cliente.getPreferencias().isEmpty());
    }
}