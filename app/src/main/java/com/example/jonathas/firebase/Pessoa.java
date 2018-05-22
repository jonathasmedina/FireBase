package com.example.jonathas.firebase;

import java.util.ArrayList;

/**
 * Created by Jonathas on 27/11/2016.
 */

public class Pessoa {
    String nome, endereco, id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Pessoa(){

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
