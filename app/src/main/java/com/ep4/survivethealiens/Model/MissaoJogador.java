package com.ep4.survivethealiens.Model;

/**
 * Created by Carla on 12/11/2016.
 */

public class MissaoJogador {
    private int Id;
    private int IdJogador;
    private int IdMissao;
    private boolean liberada;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdJogador() {
        return IdJogador;
    }

    public void setIdJogador(int idJogador) {
        IdJogador = idJogador;
    }

    public int getIdMissao() {
        return IdMissao;
    }

    public void setIdMissao(int idMissao) {
        IdMissao = idMissao;
    }

    public boolean isLiberada() {
        return liberada;
    }

    public void setLiberada(boolean liberada) {
        this.liberada = liberada;
    }
}
