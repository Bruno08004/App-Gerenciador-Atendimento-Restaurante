package com.exemple.controller;

import com.exemple.model.Cliente;
import com.exemple.model.GrupoClientes;
import com.exemple.model.Pedido;
import com.exemple.model.Restaurante;
import com.exemple.model.AtendimentoIndividual;
import com.exemple.util.TipoCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class TelaClienteControllerTest {

    private Restaurante restaurante;

    @BeforeEach
    public void setUp() {
        restaurante = new Restaurante("Restaurante Teste");
    }

    @Test
    public void testRegistrarChegadaClienteIndividual() {
        String nome = "Maria";
        TipoCliente tipo = TipoCliente.PRIORITARIO;

        Cliente novoCliente = new Cliente(restaurante.gerarNovoClienteId(), nome, tipo);
        novoCliente.setHoraChegada(LocalTime.now());
        restaurante.getFilaDeEsperaGeral().add(novoCliente);

        assertEquals(1, restaurante.getFilaDeEsperaGeral().size());
        assertEquals("Maria", restaurante.getFilaDeEsperaGeral().get(0).getNome());
        assertEquals(TipoCliente.PRIORITARIO, restaurante.getFilaDeEsperaGeral().get(0).getTipoCliente());
    }

    @Test
    public void testRegistrarChegadaGrupo() {
        String nomeGrupo = "Família Silva";
        int numPessoas = 3;

        GrupoClientes grupo = new GrupoClientes(restaurante.gerarNovoGrupoId(), nomeGrupo);
        for (int i = 0; i < numPessoas; i++) {
            Cliente membro = new Cliente(restaurante.gerarNovoClienteId(), "Membro " + (i + 1), TipoCliente.COMUM);
            grupo.adicionarCliente(membro);
        }
        grupo.setHoraChegada(LocalTime.now());
        restaurante.getFilaDeEsperaGeral().add(grupo);

        assertEquals(1, restaurante.getFilaDeEsperaGeral().size());
        GrupoClientes grupoRegistrado = (GrupoClientes) restaurante.getFilaDeEsperaGeral().get(0);
        assertEquals(nomeGrupo, grupoRegistrado.getNomeGrupo());
        assertEquals(numPessoas, grupoRegistrado.getClientes().size());
    }

    @Test
    public void testBuscarPedidoPorIdNaoEncontrado() {
        int pedidoId = 999;
        Pedido pedido = restaurante.buscarPedidoPorId(pedidoId);
        assertNull(pedido);
    }

    @Test
    public void testBuscarPedidoPorIdEncontrado() {
        //Pedido pedido = new Pedido();
        Cliente cliente = new Cliente(restaurante.gerarNovoClienteId(), "João", TipoCliente.COMUM);

        // Defina a hora de chegada ANTES de atender o cliente!
        cliente.setHoraChegada(LocalTime.now());

        // Cria e adiciona um garçom ao restaurante
        com.exemple.model.Garcom garcom = new com.exemple.model.Garcom(1, "Garçom Teste", null);
        restaurante.adicionarGarcom(garcom);

        // Faz o garçom atender o cliente (isso cria o atendimento e adiciona à fila
        // corretamente)
        garcom.atenderCliente(cliente);

        // Recupera o atendimento criado pelo método acima
        AtendimentoIndividual atendimentoCriado = (AtendimentoIndividual) garcom.getFilaAtendimentoIndividual()
                .getFila().peek();

        // Finaliza o atendimento antes de registrar
        atendimentoCriado.finalizarAtendimento();

        // Registra o atendimento finalizado corretamente
        restaurante.registrarAtendimentoFinalizado(atendimentoCriado);

        Pedido encontrado = restaurante.buscarPedidoPorId(atendimentoCriado.getPedido().getId());
        assertNotNull(encontrado);
        assertEquals(atendimentoCriado.getPedido().getId(), encontrado.getId());
    }
}