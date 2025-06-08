package com.exemple.model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.exemple.util.TipoCliente;

class AtendivelTest {

    @Test
    void metodosDevemRetornarValoresCorretos() {
        Atendivel atendivel = new Atendivel() {
            @Override
            public String getNome() {
                return "Teste";
            }

            @Override
            public TipoCliente getTipoCliente() {
                return TipoCliente.COMUM;
            }

            @Override
            public List<String> getPreferencias() {
                return Arrays.asList("Sem glúten", "Vegano");
            }
        };

        assertEquals("Teste", atendivel.getNome());
        assertEquals(TipoCliente.COMUM, atendivel.getTipoCliente());
        assertTrue(atendivel.getPreferencias().contains("Sem glúten"));
        assertTrue(atendivel.getPreferencias().contains("Vegano"));
    }

    @Test
    void metodosDevemLancarUnsupportedOperationExceptionQuandoNaoImplementados() {
        Atendivel atendivel = new Atendivel() {
            @Override
            public String getNome() {
                throw new UnsupportedOperationException();
            }

            @Override
            public TipoCliente getTipoCliente() {
                throw new UnsupportedOperationException();
            }

            @Override
            public List<String> getPreferencias() {
                throw new UnsupportedOperationException();
            }
        };

        assertThrows(UnsupportedOperationException.class, atendivel::getNome);
        assertThrows(UnsupportedOperationException.class, atendivel::getTipoCliente);
        assertThrows(UnsupportedOperationException.class, atendivel::getPreferencias);
    }
}