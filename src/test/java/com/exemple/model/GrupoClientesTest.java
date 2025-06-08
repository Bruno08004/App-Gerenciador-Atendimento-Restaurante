package com.exemple.model;

import com.exemple.util.TipoCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GrupoClientesTest {

    private GrupoClientes grupo;

    @BeforeEach
    void setUp() {
        grupo = new GrupoClientes(1, "Família Silva");
    }

    @Test
    void testConstrutorNomeNulo() {
        assertThrows(IllegalArgumentException.class, () -> new GrupoClientes(2, null));
    }

    @Test
    void testAdicionarClienteValido() {
        Cliente cliente = new Cliente(1, "Maria", TipoCliente.COMUM);
        grupo.adicionarCliente(cliente);
        assertEquals(1, grupo.getClientes().size());
        assertEquals("Maria", grupo.getClientes().get(0).getNome());
    }

    @Test
    void testAdicionarClienteNulo() {
        assertThrows(NullPointerException.class, () -> grupo.adicionarCliente(null));
    }

    @Test
    void testAdicionarPedidoValido() {
        Pedido pedido = new Pedido();
        grupo.adicionarPedido(pedido);
        assertEquals(1, grupo.getPedidos().size());
        assertEquals(pedido, grupo.getPedidos().get(0));
    }

    @Test
    void testAdicionarPedidoNulo() {
        assertThrows(NullPointerException.class, () -> grupo.adicionarPedido(null));
    }

    @Test
    void testRemoverPedidoValido() {
        Pedido pedido = new Pedido();
        grupo.adicionarPedido(pedido);
        grupo.removerPedido(pedido);
        assertTrue(grupo.getPedidos().isEmpty());
    }

    @Test
    void testRemoverPedidoNulo() {
        assertThrows(NullPointerException.class, () -> grupo.removerPedido(null));
    }

    @Test
    void testGetNome() {
        assertEquals("Família Silva", grupo.getNome());
    }

    @Test
    void testGetTipoClienteComum() {
        grupo.adicionarCliente(new Cliente(1, "João", TipoCliente.COMUM));
        grupo.adicionarCliente(new Cliente(2, "Maria", TipoCliente.COMUM));
        assertEquals(TipoCliente.COMUM, grupo.getTipoCliente());
    }

    @Test
    void testGetTipoClientePrioritario() {
        grupo.adicionarCliente(new Cliente(1, "João", TipoCliente.PRIORITARIO));
        grupo.adicionarCliente(new Cliente(2, "Maria", TipoCliente.COMUM));
        assertEquals(TipoCliente.PRIORITARIO, grupo.getTipoCliente());
    }

    @Test
    void testGetPreferencias() {
        Cliente c1 = new Cliente(1, "João", TipoCliente.COMUM);
        Cliente c2 = new Cliente(2, "Maria", TipoCliente.COMUM);
        c1.getPreferencias().add("Vegano");
        c2.getPreferencias().add("Sem glúten");
        grupo.adicionarCliente(c1);
        grupo.adicionarCliente(c2);
        List<String> prefs = grupo.getPreferencias();
        assertTrue(prefs.contains("Vegano"));
        assertTrue(prefs.contains("Sem glúten"));
        assertEquals(2, prefs.size());
    }

    @Test
    void testHoraChegada() {
        LocalTime agora = LocalTime.now();
        grupo.setHoraChegada(agora);
        assertEquals(agora, grupo.getHoraChegada());
    }

    @Test
    void testObservacoesGerais() {
        grupo.setObservacoesGerais("Mesa perto da janela");
        assertEquals("Mesa perto da janela", grupo.getObservacoesGerais());
    }
}