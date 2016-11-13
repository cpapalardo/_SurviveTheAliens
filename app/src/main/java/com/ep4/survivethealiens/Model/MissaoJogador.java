package com.ep4.survivethealiens.Model;

/**
 * Created by Carla on 12/11/2016.
 */

public class MissaoJogador {
    private int Id;
    private int Id_Jogador;
    private int Id_Missao;
    private boolean Liberada;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdJogador() {
        return Id_Jogador;
    }

    public void setIdJogador(int idJogador) {
        Id_Jogador = idJogador;
    }

    public int getIdMissao() {
        return Id_Missao;
    }

    public void setIdMissao(int idMissao) {
        Id_Missao = idMissao;
    }

    public boolean isLiberada() {
        return Liberada;
    }

    public void setLiberada(boolean liberada) {
        this.Liberada = liberada;
    }
}
