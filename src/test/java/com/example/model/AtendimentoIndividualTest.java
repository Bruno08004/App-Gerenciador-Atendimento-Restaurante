package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.util.TipoCliente;

class AtendimentoIndividualTest {

    private Cliente clienteMock;
    private Pedido pedidoMock;

    @BeforeEach
    void setUp() {
        // Ajuste o construtor conforme sua classe Cliente
        clienteMock = new Cliente(1, "Cliente Teste", TipoCliente.COMUM);
        pedidoMock = new Pedido();
    }

    @Test
    void construtorDeveLancarExcecaoSeClienteNulo() {
        assertThrows(IllegalArgumentException.class, () -> new AtendimentoIndividual(null, pedidoMock));
    }

    @Test
    void construtorDeveLancarExcecaoSePedidoNulo() {
        assertThrows(IllegalArgumentException.class, () -> new AtendimentoIndividual(clienteMock, null));
    }

    @Test
    void construtorValidoDeveAssociarClienteEPedido() {
        AtendimentoIndividual atendimento = new AtendimentoIndividual(clienteMock, pedidoMock);
        assertNotNull(atendimento.getCliente());
        assertEquals(clienteMock, atendimento.getCliente());
        assertEquals(pedidoMock, atendimento.getPedido());
    }
}