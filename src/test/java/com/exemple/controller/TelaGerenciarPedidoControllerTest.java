package com.exemple.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.exemple.model.AtendimentoIndividual;
import com.exemple.model.Cliente;
import com.exemple.model.Garcom;
import com.exemple.model.ItemPedido;
import com.exemple.model.ObservacaoDoPedido;
import com.exemple.model.Pedido;
import com.exemple.model.Restaurante;
import com.exemple.util.TipoCliente;
import com.exemple.util.Turno;

class TelaGerenciarPedidoControllerTest {

    private Restaurante restaurante;
    private Garcom garcom;
    private Pedido pedido;
    private AtendimentoIndividual atendimento;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante("Restaurante Teste");
        garcom = new Garcom(1, "Garçom Teste", Turno.MANHA);
        pedido = new Pedido();
        cliente = new Cliente(1, "Cliente Teste", TipoCliente.COMUM);
        atendimento = new AtendimentoIndividual(cliente, pedido);
        restaurante.adicionarGarcom(garcom);
        restaurante.adicionarAoCardapio(new ItemPedido("Pizza", 1, 30.0));
    }

    @Test
    void adicionarItemAoPedidoAdicionaCorretamente() {
        ItemPedido item = restaurante.getCardapio().get(0);
        int quantidade = 2;
        ItemPedido itemParaPedido = new ItemPedido(item.getNome(), quantidade, item.getPreco());
        pedido.adicionarItem(itemParaPedido);
        // Verifica se o item foi adicionado corretamente
        assertTrue(pedido.getItens().contains(itemParaPedido));
        assertEquals(1, pedido.getItens().size());
        assertEquals(60.0, pedido.calcularTotal(), 0.01);
    }

    @Test
    void adicionarObservacaoAoItemFunciona() {
        ItemPedido item = new ItemPedido("Suco", 1, 8.0);
        pedido.adicionarItem(item);
        ObservacaoDoPedido obs = new ObservacaoDoPedido("Sem açúcar");
        item.adicionarObservacao(obs);
        assertTrue(item.getObservacoes().contains(obs));
        assertEquals("Sem açúcar", item.getObservacoes().get(0).getDescricao());
    }

    @Test
    void calcularTotalPedidoCorreto() {
        pedido.adicionarItem(new ItemPedido("Pizza", 2, 30.0));
        pedido.adicionarItem(new ItemPedido("Suco", 1, 8.0));
        assertEquals(68.0, pedido.calcularTotal(), 0.01);
    }

    @Test
    void pedidoInicialmenteVazio() {
        assertTrue(pedido.getItens().isEmpty());
        assertEquals(0.0, pedido.calcularTotal(), 0.01);
    }
}