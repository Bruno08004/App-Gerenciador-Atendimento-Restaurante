package com.exemple.controller;

import com.exemple.model.*;
import com.exemple.util.Status;
import com.exemple.util.TipoCliente;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste para {@link TelaAtendimentoController}.
 */
public class TelaAtendimentoControllerTest {

    private TelaAtendimentoController controller;
    private Restaurante restaurante;
    private Garcom garcom;

    @BeforeEach
    public void setUp() {
        // Necessário para inicializar o JavaFX Toolkit
        new JFXPanel();

        controller = new TelaAtendimentoController();
        restaurante = new Restaurante("Restaurante Teste");
        garcom = new Garcom(1, "João", null);

        restaurante.adicionarGarcom(garcom);

        // Simula injeção dos componentes JavaFX
        controller.listViewFilaEspera = new ListView<>();
        controller.listViewAtendimentosDoGarcom = new ListView<>();
        controller.labelGarcomLogadoNome = new Label();

        controller.setRestaurante(restaurante);
        controller.setGarcomLogado(garcom);
    }

    @Test
    public void testSetRestauranteAndGarcomLogado() {
        assertEquals("Garçom: João", controller.labelGarcomLogadoNome.getText());
        assertNotNull(controller.listViewFilaEspera);
        assertNotNull(controller.listViewAtendimentosDoGarcom);
    }

    @Test
    public void testHandleAtenderProximoDaFilaGeral_Cliente() {
        Cliente cliente = new Cliente(1, "Maria", TipoCliente.COMUM);
        cliente.setHoraChegada(LocalTime.now());
        restaurante.getFilaDeEsperaGeral().add(cliente);

        // Simula ação de atender próximo da fila
        controller.handleAtenderProximoDaFilaGeral(null);

        // O cliente deve ter sido removido da fila de espera e adicionado à fila do garçom
        assertTrue(restaurante.getFilaDeEsperaGeral().isEmpty());
        assertEquals(1, garcom.getFilaAtendimentoIndividual().tamanho());
    }

    @Test
    public void testHandleAtenderProximoDaFilaGeral_Grupo() {
        GrupoClientes grupo = new GrupoClientes(1, "Grupo 1");
        grupo.setHoraChegada(LocalTime.now());
        restaurante.getFilaDeEsperaGeral().add(grupo);

        controller.handleAtenderProximoDaFilaGeral(null);

        assertTrue(restaurante.getFilaDeEsperaGeral().isEmpty());
        assertEquals(1, garcom.getFilaAtendimentoGrupo().tamanho());
    }

    @Test
    public void testHandleAtenderProximoDaFilaGeral_SemGarcom() {
        controller.setGarcomLogado(null);
        restaurante.getFilaDeEsperaGeral().add(new Cliente(2, "Carlos", TipoCliente.COMUM));
        // Não deve lançar exceção
        controller.handleAtenderProximoDaFilaGeral(null);
    }

    @Test
    public void testHandleFinalizarAtendimento() {
        Cliente cliente = new Cliente(3, "Ana", TipoCliente.PRIORITARIO);
        cliente.setHoraChegada(LocalTime.now());
        garcom.atenderCliente(cliente);

        // Atualiza listas para refletir o atendimento
        controller.setGarcomLogado(garcom);

        // Seleciona o atendimento na ListView
        Atendimento atendimento = garcom.getFilaAtendimentoIndividual().getFila().peek();
        String nome = ((AtendimentoIndividual) atendimento).getCliente().getNome();
        String texto = nome + " - Status: " + atendimento.getStatus();
        controller.listViewAtendimentosDoGarcom.getItems().add(texto);
        controller.listViewAtendimentosDoGarcom.getSelectionModel().select(texto);

        controller.handleFinalizarAtendimento(null);

        assertEquals(Status.FINALIZADO, atendimento.getStatus());
    }

    @Test
    public void testHandleReordenarFila() {
        Cliente c1 = new Cliente(4, "Zé", TipoCliente.COMUM);
        Cliente c2 = new Cliente(5, "Bia", TipoCliente.PRIORITARIO);
        restaurante.getFilaDeEsperaGeral().addAll(List.of(c1, c2));

        controller.handleReordenarFila(null);

        // Após reordenar, o cliente prioritário deve vir antes
        assertEquals("Bia", restaurante.getFilaDeEsperaGeral().get(0).getNome());
    }
}