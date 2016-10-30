package com.ep4.survivethealiens.Feign.Task;

import android.os.AsyncTask;

import com.ep4.survivethealiens.Feign.Request.JogadorRequests;
import com.ep4.survivethealiens.Feign.Request.MissaoRequests;
import com.ep4.survivethealiens.Model.Jogador;
import com.ep4.survivethealiens.Model.Missao;

import java.util.ArrayList;

import feign.Feign;
import feign.gson.GsonDecoder;

/**
 * Created by Carla on 29/10/2016.
 */

public class GetMissaoByJogadorTask extends AsyncTask<Jogador, Void, ArrayList<Missao>> {
    @Override
    protected ArrayList<Missao> doInBackground(Jogador... params) {
        try{
            MissaoRequests request = Feign.builder()
                    .decoder(new GsonDecoder())
                    .target(MissaoRequests.class, "http://survivethealiens.azurewebsites.net/api/");// lá em PostagemRequest, as URIS
            //serão pegas a partir desta URL
            ArrayList<Missao> missaoList = request.getMissaoListByJogador(params[0]);
            return missaoList;
        }catch (Exception e){
            System.err.println("Erro de comunicação,");
            e.printStackTrace();
            return null;
        }
    }
}
