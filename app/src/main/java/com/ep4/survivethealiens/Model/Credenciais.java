package com.ep4.survivethealiens.Model;

/**
 * Created by Carla on 12/11/2016.
 */

public class Credenciais {
    private String Email;
    private String Senha;

    public Credenciais(String email, String senha){
        Email = email;
        Senha = senha;
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
}
