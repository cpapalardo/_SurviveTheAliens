package com.ep4.survivethealiens.Feign.Task;

import android.os.AsyncTask;

import com.ep4.survivethealiens.Feign.Request.JogadorRequests;
import com.ep4.survivethealiens.Model.Jogador;

import feign.Feign;
import feign.gson.GsonDecoder;

/**
 * Created by Carla on 23/10/2016.
 */

public class GetJogadorTask extends AsyncTask<Integer, Void, Jogador> {
    @Override
    public Jogador doInBackground(Integer... params) {
        try{
            JogadorRequests request = Feign.builder()
                    .decoder(new GsonDecoder())
                    .target(JogadorRequests.class, "http://survivethealiens.azurewebsites.net/api/");// lá em PostagemRequest, as URIS
            //serão pegas a partir desta URL
            Jogador jogador = request.getPostagem(params[0]);
            return jogador;
        }catch (Exception e){
            System.err.println("Erro de comunicação,");
            e.printStackTrace();
            return null;
        }
    }
}
