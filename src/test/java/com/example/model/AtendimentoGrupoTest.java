package com.example.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AtendimentoGrupoTest {

    private GrupoClientes grupoMock;
    private Pedido pedidoMock;

    @BeforeEach
    void setUp() {
        grupoMock = new GrupoClientes(1, "Grupo Teste");
        pedidoMock = new Pedido();
    }

    @Test
    void construtorDeveLancarExcecaoSeGrupoNulo() {
        assertThrows(IllegalArgumentException.class, () -> new AtendimentoGrupo(null, pedidoMock));
    }

    @Test
    void construtorDeveLancarExcecaoSePedidoNulo() {
        assertThrows(IllegalArgumentException.class, () -> new AtendimentoGrupo(grupoMock, null));
    }

    @Test
    void construtorValidoDeveAssociarGrupoEPedido() {
        AtendimentoGrupo atendimento = new AtendimentoGrupo(grupoMock, pedidoMock);
        assertNotNull(atendimento.getGrupo());
        assertEquals(grupoMock, atendimento.getGrupo());
        assertEquals(pedidoMock, atendimento.getPedido());
    }
}