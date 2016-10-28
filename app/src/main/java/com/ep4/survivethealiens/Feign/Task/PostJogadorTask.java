package com.ep4.survivethealiens.Feign.Task;

import android.os.AsyncTask;

import com.ep4.survivethealiens.Feign.Request.JogadorRequests;
import com.ep4.survivethealiens.Model.Jogador;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

/**
 * Created by Carla on 23/10/2016.
 */

public class PostJogadorTask extends AsyncTask<Jogador, Void, Jogador> {

    @Override
    public Jogador doInBackground(Jogador... params) {
        try{
            JogadorRequests request = Feign.builder()
                    .encoder(new GsonEncoder())
                    .decoder(new GsonDecoder())
                    .logLevel(Logger.Level.FULL)
                    .target(JogadorRequests.class, "http://survivethealiens.azurewebsites.net/api/");// lá em PostagemRequest, as URIS
            //serão pegas a partir desta URL
            Jogador jogador = request.criarJogador(params[0]);
            return jogador;
        }catch (Exception e){
            System.err.println("Erro de comunicação,");
            e.printStackTrace();
            return null;
        }
    }
}
