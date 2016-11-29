package com.ep4.survivethealiens.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by aluno on 07/10/2016.
 */
public class Jogador {

    @SerializedName("Id")
    private Integer Id;

    @SerializedName("Nome")
    private String Nome;

    @SerializedName("Apelido")
    private String Apelido;

    @SerializedName("Genero")
    private String Genero;

    @SerializedName("Email")
    private String Email;

    @SerializedName("Senha")
    private String Senha;

    @SerializedName("HorasJogadas")
    private Float HorasJogadas;

    @SerializedName("KmCaminhados")
    private Float KmCaminhados;

    public Jogador(){

    }

    public Jogador(String nome, String apelido, String _email, String _senha, String genero){
        this.Email = _email;
        this.Senha = _senha;
        this.Nome = nome;
        this.Apelido = apelido;
        this.Genero = genero;
        HorasJogadas = 0f;
        KmCaminhados = 0f;
        Id = 0;
    }

    public Jogador(String _email, String _senha){
        Email = _email;
        Senha = _senha;
    }

    public int getId() { return Id; }

    public void setId(int id) {
        this.Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        this.Nome = nome;
    }

    public String getApelido() {
        return Apelido;
    }

    public void setApelido(String apelido) {
        this.Apelido = apelido;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        this.Genero = genero;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        this.Senha = senha;
    }

    public float getHorasJogadas() {
        return HorasJogadas;
    }

    public void setHorasJogadas(float horasJogadas) {
        this.HorasJogadas = horasJogadas;
    }

    public float getKmCaminhados() {
        return KmCaminhados;
    }

    public void setKmCaminhados(float kmCaminhados) { this.KmCaminhados = kmCaminhados; }
}
