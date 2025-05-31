package com.exemple.model;

import com.exemple.util.TipoCliente;

import java.util.List;

public interface Atendivel {
    String getNome();

    TipoCliente getTipoCliente();

    List<String> getPreferencias();


}
