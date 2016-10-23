package com.ep4.survivethealiens.Model;

/**
 * Created by aluno on 07/10/2016.
 */
public class Capitulo {
    private int Id;
    private boolean Disponivel;
    private int Numero;
    private String Nome;
    private String Descricao;
    private Historia Historia;

    public int getId() { return Id; }

    public void setId(int id) { this.Id = id; }

    public boolean isDisponivel() { return Disponivel; }

    public void setDisponivel(boolean disponivel) { this.Disponivel = disponivel; }

    public int getNumero() { return Numero; }

    public void setNumero(int numero) { this.Numero = numero; }

    public String getNome() { return Nome; }

    public void setNome(String nome) { this.Nome = nome; }

    public String getDescricao() { return Descricao; }

    public void setDescricao(String descricao) { this.Descricao = descricao; }

    public Historia getHistoria() { return Historia; }

    public void setHistoria(Historia historia) { this.Historia = historia; }
}
