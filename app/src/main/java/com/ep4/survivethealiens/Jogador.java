package com.ep4.survivethealiens;

import java.util.Date;

/**
 * Created by aluno on 07/10/2016.
 */
public class Jogador {
    private Integer id;
    private String nome;
    private String apelido;
    private Character genero;
    private String email;
    private String senha;
    private Date dataCadastro;
    private Integer horasJogadas;
    private Integer kmCaminhados;

    public Jogador(){

    }

    public Jogador(String nome, String apelido, String _email, String _senha, String genero){
        this.email = _email;
        this.senha = _senha;
        this.nome = nome;
        this.apelido = apelido;
        this.genero = genero.charAt(0);
        dataCadastro = new Date();
        horasJogadas = 0;
        kmCaminhados = 0;
    }

    public Jogador(String _email, String _senha){
        email = _email;
        senha = _senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public Character getGenero() {
        return genero;
    }

    public void setGenero(Character genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public int getHorasJogadas() {
        return horasJogadas;
    }

    public void setHorasJogadas(int horasJogadas) {
        this.horasJogadas = horasJogadas;
    }

    public int getKmCaminhados() {
        return kmCaminhados;
    }

    public void setKmCaminhados(int kmCaminhados) {
        this.kmCaminhados = kmCaminhados;
    }
}
