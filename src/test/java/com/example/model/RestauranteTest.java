package com.example.model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.util.Turno;

class RestauranteTest {

    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante("Restaurante Teste");
    }

    @Test
    void construtorDeveLancarExcecaoSeNomeNuloOuVazio() {
        assertThrows(IllegalArgumentException.class, () -> new Restaurante(null));
        assertThrows(IllegalArgumentException.class, () -> new Restaurante(""));
        assertThrows(IllegalArgumentException.class, () -> new Restaurante("   "));
    }

    @Test
    void adicionarGarcomNaoPermiteNulo() {
        assertThrows(IllegalArgumentException.class, () -> restaurante.adicionarGarcom(null));
    }

    @Test
    void adicionarGarcomAdicionaNaLista() {
        Garcom garcom = new Garcom(1, "João", null);
        restaurante.adicionarGarcom(garcom);
        assertTrue(restaurante.getGarcons().contains(garcom));
    }

    @Test
    void buscarGarcomPorIdRetornaCorretoOuNull() {
        Garcom garcom = new Garcom(10, "Maria", null);
        restaurante.adicionarGarcom(garcom);
        assertEquals(garcom, restaurante.buscarGarcomPorId(10));
        assertNull(restaurante.buscarGarcomPorId(999));
    }

    @Test
    void adicionarAoCardapioNaoPermiteNulo() {
        assertThrows(IllegalArgumentException.class, () -> restaurante.adicionarAoCardapio(null));
    }

    @Test
    void adicionarAoCardapioAdicionaNaLista() {
        ItemPedido item = new ItemPedido("Pizza", 1, 30.0);
        restaurante.adicionarAoCardapio(item);
        assertTrue(restaurante.getCardapio().contains(item));
    }

    @Test
    void iniciarTurnoNaoPermiteNulo() {
        assertThrows(IllegalArgumentException.class, () -> restaurante.iniciarTurno(null));
    }

    @Test
    void iniciarEEncerrarTurnoFunciona() {
        Garcom garcom = new Garcom(1, "João", null);
        restaurante.adicionarGarcom(garcom);
        restaurante.iniciarTurno(Turno.NOITE);
        assertEquals(Turno.NOITE, restaurante.getTurnoAtual());
        assertEquals(Turno.NOITE, restaurante.getGarcons().get(0).getTurnoAtual());

        restaurante.encerrarTurno();
        assertNull(restaurante.getTurnoAtual());
        assertNull(restaurante.getGarcons().get(0).getTurnoAtual());
    }

    @Test
    void gerarNovosIdsSaoSequenciais() {
        int id1 = restaurante.gerarNovoGarcomId();
        int id2 = restaurante.gerarNovoGarcomId();
        assertEquals(id1 + 1, id2);

        int c1 = restaurante.gerarNovoClienteId();
        int c2 = restaurante.gerarNovoClienteId();
        assertEquals(c1 + 1, c2);

        int g1 = restaurante.gerarNovoGrupoId();
        int g2 = restaurante.gerarNovoGrupoId();
        assertEquals(g1 + 1, g2);
    }

    @Test
    void validarLoginGarcomRetornaCorretoOuNull() {
        Garcom garcom = new Garcom(5, "Carlos", null);
        restaurante.adicionarGarcom(garcom);
        assertEquals(garcom, restaurante.validarLoginGarcom(5, "Carlos"));
        assertNull(restaurante.validarLoginGarcom(5, "OutroNome"));
        assertNull(restaurante.validarLoginGarcom(999, "Carlos"));
    }

    @Test
    void getFilaDeEsperaGeralRetornaLista() {
        List<Atendivel> fila = restaurante.getFilaDeEsperaGeral();
        assertNotNull(fila);
        assertTrue(fila.isEmpty());
    }
}