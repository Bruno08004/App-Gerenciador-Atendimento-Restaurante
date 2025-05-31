package com.exemple.util.persistence;


import com.exemple.model.Garcom;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {
    private static final String CAMINHO_ARQUIVO = "garcons.json";

    public static void salvarGarcons(List<Garcom> garcons) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(CAMINHO_ARQUIVO)) {
            gson.toJson(garcons, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Garcom> carregarGarcons() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(CAMINHO_ARQUIVO)) {
            return gson.fromJson(reader, new TypeToken<List<Garcom>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
