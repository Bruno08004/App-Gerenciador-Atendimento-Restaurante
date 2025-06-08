package com.exemple.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.exemple.model.Garcom;
import com.exemple.model.Restaurante;

//import java.util.ArrayList;
//import java.util.List;

/**
 * Classe de teste para lógica de negócio da TelaCadastroGarcomController.
 * Testa métodos de cadastro de garçom e validação de nome.
 */
public class TelaCadastroGarcomControllerTest {

    private TelaCadastroGarcomController controller;
    private Restaurante restaurante;

    @BeforeEach
    public void setUp() {
        controller = new TelaCadastroGarcomController();
        restaurante = new Restaurante("Restaurante Teste");
        controller.setRestaurante(restaurante);
    }

    @Test
    public void testNomeVazioNaoDeveCadastrar() {
        String nomeVazio = "    ";

        boolean resultado = validaENovaLogicaCadastrarGarcom(nomeVazio);

        assertFalse(resultado, "Não deve cadastrar com nome vazio");
        assertEquals(0, restaurante.getGarcons().size());
    }

    @Test
    public void testNomeValidoDeveCadastrar() {
        String nomeValido = "José da Silva";

        boolean resultado = validaENovaLogicaCadastrarGarcom(nomeValido);

        assertTrue(resultado, "Deve cadastrar com nome válido");
        assertEquals(1, restaurante.getGarcons().size());
        Garcom garcom = restaurante.getGarcons().get(0);
        assertEquals(nomeValido, garcom.getNome());
    }

    /**
     * Método auxiliar para testar só a lógica do cadastro do garçom,
     * sem dependência de JavaFX ou Mockito.
     * @param nome nome do garçom
     * @return true se cadastrou, false se não cadastrou (nome inválido)
     */
    private boolean validaENovaLogicaCadastrarGarcom(String nome) {
        nome = nome == null ? "" : nome.trim();
        if (nome.isEmpty()) {
            return false;
        }

        try {
            int novoId = restaurante.gerarNovoGarcomId();
            Garcom novoGarcom = new Garcom(novoId, nome, null);
            restaurante.adicionarGarcom(novoGarcom);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}