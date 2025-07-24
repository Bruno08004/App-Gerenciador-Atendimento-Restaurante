package com.example.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegistroUtil {

    private static final String CAMINHO_ARQUIVO = "historico_atendimentos.txt";

    public static void registrarAtendimento(Atendimento atendimento, String nomeGarcom) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO, true))) {

            String nome = atendimento instanceof AtendimentoIndividual
                    ? ((AtendimentoIndividual) atendimento).getCliente().getNome()
                    : ((AtendimentoGrupo) atendimento).getGrupo().getNomeGrupo();

            String tipo = atendimento instanceof AtendimentoIndividual ? "Individual" : "Grupo";
            String status = atendimento.getStatus().toString();

            long minutos = atendimento.getTempoDeAtendimento() != null
                    ? atendimento.getTempoDeAtendimento().toMinutes()
                    : 0;

            long segundos = atendimento.getTempoDeAtendimento() != null
                    ? atendimento.getTempoDeAtendimento().toSeconds() % 60
                    : 0;

            String inicio = atendimento.getInicio() != null
                    ? atendimento.getInicio().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                    : "--:--:--";

            String fim = atendimento.getFim() != null
                    ? atendimento.getFim().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                    : "--:--:--";

            String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            StringBuilder itensBuilder = new StringBuilder();
            for (ItemPedido item : atendimento.getPedido().getItens()) {
                itensBuilder.append(item.getNome())
                        .append(" (x").append(item.getQuantidade()).append("), ");
            }

            String itens = itensBuilder.length() > 0
                    ? itensBuilder.substring(0, itensBuilder.length() - 2)  // remove última vírgula
                    : "Nenhum item";

            String linha = String.format("""
                    ########################################################################
                    [%s] Atendimento %s
                    → Nome: %s
                    → Garçom: %s
                    → Pedido ID: %d
                    → Itens: %s
                    → Início: %s | Fim: %s
                    → Tempo: %d min %d seg
                    → Data: %s
                    ########################################################################
                    """, status, tipo, nome, nomeGarcom, atendimento.getPedido().getId(),
                    itens, inicio, fim, minutos, segundos, data);

            writer.write(linha);
        } catch (IOException e) {
            System.err.println("Erro ao registrar atendimento: " + e.getMessage());
        }
    }
}
