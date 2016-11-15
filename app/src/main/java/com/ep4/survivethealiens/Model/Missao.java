package com.ep4.survivethealiens.Model;

/**
 * Created by aluno on 07/10/2016.
 */
public class Missao {
    private int Id;
    private boolean Concluida;
    private String Nome;
    private Capitulo Capitulo;
    private int Numero;
    private boolean liberada;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isConcluida() {
        return Concluida;
    }

    public void setConcluida(boolean concluida) {
        Concluida = concluida;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Capitulo getCapitulo() {
        return Capitulo;
    }

    public void setCapitulo(Capitulo capitulo) {
        Capitulo = capitulo;
    }

    public int getNumero() {
        return Numero;
    }

    public void setNumero(int numero) {
        Numero = numero;
    }

    public boolean isLiberada() {
        return liberada;
    }

    public void setLiberada(boolean liberada) {
        this.liberada = liberada;
    }
}

