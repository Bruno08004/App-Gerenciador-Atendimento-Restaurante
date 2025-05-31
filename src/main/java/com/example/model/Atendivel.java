package com.example.model;

import com.example.util.TipoCliente;

import java.util.List;

public interface Atendivel {
    String getNome();

    TipoCliente getTipoCliente();

    List<String> getPreferencias();


}
