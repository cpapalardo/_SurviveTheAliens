package com.ep4.survivethealiens.Feign.Task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ep4.survivethealiens.Activity.CadastroActivity;
import com.ep4.survivethealiens.Activity.CapituloActivity;
import com.ep4.survivethealiens.Activity.HistoriaActivity;
import com.ep4.survivethealiens.Activity.LoginActivity;
import com.ep4.survivethealiens.Activity.PrincipalActivity;
import com.ep4.survivethealiens.Feign.Request.JogadorRequests;
import com.ep4.survivethealiens.Feign.Request.MissaoRequests;
import com.ep4.survivethealiens.Model.Jogador;
import com.ep4.survivethealiens.Model.Missao;
import com.ep4.survivethealiens.Model.MissaoJogador;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

/**
 * Created by Carla on 29/10/2016.
 */

public class GetMissaoByJogadorTask extends AsyncTask<Integer, Void, ArrayList<MissaoJogador>> {

    LoginActivity myActivity;
    Context myContext;
    ArrayList<MissaoJogador> missaoList;
    ProgressDialog pDialog;

    public GetMissaoByJogadorTask(LoginActivity activity, Context context){
        myActivity = activity;
        myContext = context;
    }

    @Override
    protected ArrayList<MissaoJogador> doInBackground(Integer... params) {
        try{
            MissaoRequests request = Feign.builder()
                    .decoder(new GsonDecoder())
                    .encoder(new GsonEncoder())
                    .target(MissaoRequests.class, "http://survivethealiens.azurewebsites.net/api/");// lá em PostagemRequest, as URIS
            //serão pegas a partir desta URL
            missaoList = request.getMissoesById(params[0]);

        }catch (Exception e){
            System.err.println("Erro de comunicação,");
            e.printStackTrace();
        }

        return missaoList;
    }

    @Override
    protected void onPreExecute() {
        try {
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onPostExecute(ArrayList<MissaoJogador> missaoJogadors) {
        try {
            myActivity.missaoJogadorList = missaoJogadors;
            EventBus.getDefault().postSticky(missaoJogadors);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
