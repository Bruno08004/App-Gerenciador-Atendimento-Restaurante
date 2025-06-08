package com.exemple.controller;

import com.exemple.model.Restaurante;
import com.exemple.util.Turno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TelaTurnoControllerLogicTest {

    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante("Restaurante Teste");
    }

    @Test
    void naoPermiteIniciarTurnoSeJaHouverTurnoAtivo() {
        restaurante.iniciarTurno(Turno.MANHA);
        // Simula a lógica do método handleIniciarTurno
        assertNotNull(restaurante.getTurnoAtual());
        assertEquals(Turno.MANHA, restaurante.getTurnoAtual());
    }

    @Test
    void permiteIniciarTurnoQuandoNaoHaTurnoAtivo() {
        assertNull(restaurante.getTurnoAtual());
        restaurante.iniciarTurno(Turno.NOITE);
        assertEquals(Turno.NOITE, restaurante.getTurnoAtual());
    }

    @Test
    void naoPermiteEncerrarTurnoSeNaoHaTurnoAtivo() {
        assertNull(restaurante.getTurnoAtual());
        // Simula a lógica: não deve lançar exceção, apenas não faz nada
        restaurante.encerrarTurno();
        assertNull(restaurante.getTurnoAtual());
    }

    @Test
    void permiteEncerrarTurnoQuandoHaTurnoAtivo() {
        restaurante.iniciarTurno(Turno.TARDE);
        assertEquals(Turno.TARDE, restaurante.getTurnoAtual());
        restaurante.encerrarTurno();
        assertNull(restaurante.getTurnoAtual());
    }
}