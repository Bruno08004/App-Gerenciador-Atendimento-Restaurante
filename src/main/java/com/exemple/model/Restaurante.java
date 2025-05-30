package com.exemple.model;

import java.util.ArrayList;
import java.util.List;

public class Restaurante {
    private List<Garcom> garcons;
    private FilaDeAtendimento fila;
    private List<Atendimento> historicoAtendimentos;

    public Restaurante() {
        garcons = new ArrayList<>();
        fila = new FilaDeAtendimento();
        historicoAtendimentos = new ArrayList<>();
    }

    public void adicionarGarcom(Garcom garcom) {
        garcons.add(garcom);
    }

    public void adicionarNaFila(Atendivel atendivel) {
        fila.adicionarNaFila(atendivel);
    }

    public boolean distribuirAtendimento() {
        if (!fila.filaVazia()) {
            Atendivel atendivel = fila.proximoAtendimento();
            for (Garcom garcom : garcons) {
                if (garcom.podeAtender(atendivel)) {
                    garcom.registrarAtendimento(atendivel);
                    Atendimento atendimento = new Atendimento(atendivel, garcom);
                    historicoAtendimentos.add(atendimento);
                    return true;
                }
            }
            // Se nenhum garçom puder atender, devolve para fila
            fila.adicionarNaFila(atendivel);
        }
        return false;
    }

    public List<Atendimento> getHistoricoAtendimentos() {
        return historicoAtendimentos;
    }

    public FilaDeAtendimento getFila() {
        return fila;
    }
}
