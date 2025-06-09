package com.example.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.*;
import com.example.util.Status;
import com.example.util.TipoCliente;

//import java.util.List;
//import java.util.ArrayList;

/**
 * Classe de teste para lógica de negócio da TelaAtendimentoController.
 * Testa métodos de gerenciamento de fila, cadastro e finalização de
 * atendimentos.
 */
public class TelaAtendimentoControllerTest {

    private Restaurante restauranteMock;
    private Garcom garcomMock;

    @BeforeEach
    public void setUp() {
        restauranteMock = new Restaurante("Restaurante Teste");
        garcomMock = new Garcom(1, "João", null);

        restauranteMock.adicionarGarcom(garcomMock);
    }

    @Test
    public void testGarcomPodeAtenderMaisGrupos() {
        assertTrue(garcomMock.podeAtenderMaisGrupos());

        // Simular o garçom atendendo 3 grupos para atingir o limite
        GrupoClientes grupo1 = new GrupoClientes(1, "Grupo1");
        grupo1.setHoraChegada(java.time.LocalTime.now());
        GrupoClientes grupo2 = new GrupoClientes(2, "Grupo2");
        grupo2.setHoraChegada(java.time.LocalTime.now());
        GrupoClientes grupo3 = new GrupoClientes(3, "Grupo3");
        grupo3.setHoraChegada(java.time.LocalTime.now());

        garcomMock.atenderGrupo(grupo1);
        garcomMock.atenderGrupo(grupo2);
        garcomMock.atenderGrupo(grupo3);

        assertFalse(garcomMock.podeAtenderMaisGrupos());
    }

    @Test
    public void testCadastrarNovoCliente() {
        Cliente cliente = new Cliente(1, "Maria", TipoCliente.PRIORITARIO);
        // Simula fila de espera geral
        restauranteMock.getFilaDeEsperaGeral().add(cliente);

        assertTrue(restauranteMock.getFilaDeEsperaGeral().contains(cliente));
        assertEquals("Maria", restauranteMock.getFilaDeEsperaGeral().get(0).getNome());
    }

    @Test
    public void testCadastrarNovoGrupo() {
        GrupoClientes grupo = new GrupoClientes(1, "Família Silva");
        grupo.setHoraChegada(java.time.LocalTime.now());
        garcomMock.atenderGrupo(grupo);

        assertEquals(1, garcomMock.getFilaAtendimentoGrupo().tamanho());
        AtendimentoGrupo atendimento = (AtendimentoGrupo) garcomMock.getFilaAtendimentoGrupo().getFila().peek();
        assertEquals("Família Silva", atendimento.getGrupo().getNomeGrupo());
    }

    @Test
    public void testFinalizarAtendimento() {
        GrupoClientes grupo = new GrupoClientes(1, "Grupo Teste");
        grupo.setHoraChegada(java.time.LocalTime.now());
        garcomMock.atenderGrupo(grupo);
        AtendimentoGrupo atendimento = (AtendimentoGrupo) garcomMock.getFilaAtendimentoGrupo().getFila().peek();

        assertEquals(Status.EM_ATENDIMENTO, atendimento.getStatus());

        atendimento.finalizarAtendimento();

        assertEquals(Status.FINALIZADO, atendimento.getStatus());
    }

    @Test
    public void testReordenarFilaPorPrioridade() {
        Cliente normal = new Cliente(1, "Cliente Normal", TipoCliente.COMUM);
        Cliente prioritario = new Cliente(2, "Cliente Prioritário", TipoCliente.PRIORITARIO);

        restauranteMock.getFilaDeEsperaGeral().add(normal);
        restauranteMock.getFilaDeEsperaGeral().add(prioritario);

        // Reordenar: prioritários primeiro
        restauranteMock.getFilaDeEsperaGeral().sort((a, b) -> (a.getTipoCliente() == TipoCliente.PRIORITARIO ? 0 : 1)
                - (b.getTipoCliente() == TipoCliente.PRIORITARIO ? 0 : 1));

        assertEquals(TipoCliente.PRIORITARIO, restauranteMock.getFilaDeEsperaGeral().get(0).getTipoCliente());
    }
}