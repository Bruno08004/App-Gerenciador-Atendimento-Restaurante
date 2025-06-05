package com.exemple.util.persistence;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.exemple.model.Garcom;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Classe utilitária para persistência de dados dos garçons em arquivo JSON.
 * <p>
 * Permite salvar e carregar a lista de garçons do sistema utilizando a biblioteca Gson.
 * Trata exceções de IO e exibe mensagens apropriadas no console.
 * </p>
 *
 * <b>Principais responsabilidades:</b>
 * <ul>
 *   <li>Salvar a lista de garçons em arquivo JSON.</li>
 *   <li>Carregar a lista de garçons do arquivo JSON.</li>
 *   <li>Tratar exceções de leitura e escrita de arquivos.</li>
 * </ul>
 *
 * <b>Dependências:</b>
 * <ul>
 *   <li>Modelos: Garcom.</li>
 *   <li>Biblioteca: Gson.</li>
 *   <li>Java: FileWriter, FileReader, IOException, List.</li>
 * </ul>
 *
 * @author
 * @version 1.0
 */
public class Persistencia {
    /** Caminho do arquivo JSON utilizado para persistência dos garçons */
    private static final String CAMINHO_ARQUIVO = "garcons.json";

    /**
     * Salva a lista de garçons no arquivo JSON.
     * Trata exceções de IO e exibe mensagens de erro no console.
     *
     * @param garcons Lista de garçons a ser salva
     */
    public static void salvarGarcons(List<Garcom> garcons) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(CAMINHO_ARQUIVO)) {
            gson.toJson(garcons, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar garçons: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao salvar garçons: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carrega a lista de garçons do arquivo JSON.
     * Trata exceções de IO e retorna uma lista vazia em caso de erro.
     *
     * @return Lista de garçons carregada do arquivo, ou lista vazia se houver erro
     */
    public static List<Garcom> carregarGarcons() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(CAMINHO_ARQUIVO)) {
            return gson.fromJson(reader, new TypeToken<List<Garcom>>(){}.getType());
        } catch (IOException e) {
            System.err.println("Erro ao carregar garçons: " + e.getMessage());
            // Retorna lista vazia se o arquivo não existir ou não puder ser lido
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Erro inesperado ao carregar garçons: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}