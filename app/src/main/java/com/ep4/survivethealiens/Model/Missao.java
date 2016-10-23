package com.ep4.survivethealiens.Model;

/**
 * Created by aluno on 07/10/2016.
 */
public class Missao {
    private int Id;
    private boolean Concluida;
    private String Nome;
    private Capitulo Capitulo;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public boolean isConcluida() {
        return Concluida;
    }

    public void setConcluida(boolean concluida) {
        this.Concluida = concluida;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        this.Nome = nome;
    }

    public Capitulo getCapitulo() {
        return Capitulo;
    }

    public void setCapitulo(Capitulo capitulo) {
        this.Capitulo = capitulo;
    }
}

