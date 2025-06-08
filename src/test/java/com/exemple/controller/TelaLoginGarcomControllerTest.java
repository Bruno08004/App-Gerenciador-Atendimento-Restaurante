package com.exemple.controller;

import com.exemple.model.Garcom;
import com.exemple.model.Restaurante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TelaLoginGarcomControllerLogicTest {

    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante("Restaurante Teste");
        restaurante.adicionarGarcom(new Garcom(1, "João", null));
    }

    @Test
    void validarLoginGarcomRetornaGarcomCorreto() {
        Garcom garcom = restaurante.validarLoginGarcom(1, "João");
        assertNotNull(garcom);
        assertEquals(1, garcom.getId());
        assertEquals("João", garcom.getNome());
    }

    @Test
    void validarLoginGarcomRetornaNullParaIdOuNomeIncorretos() {
        assertNull(restaurante.validarLoginGarcom(2, "João"));
        assertNull(restaurante.validarLoginGarcom(1, "Maria"));
        assertNull(restaurante.validarLoginGarcom(999, "Inexistente"));
    }

    @Test
    void validarLoginGarcomNaoLancaExcecaoParaEntradasInvalidas() {
        assertDoesNotThrow(() -> restaurante.validarLoginGarcom(-1, null));
        assertNull(restaurante.validarLoginGarcom(-1, null));
    }
}