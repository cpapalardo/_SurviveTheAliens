package com.ep4.survivethealiens.Feign.Task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ep4.survivethealiens.Activity.HistoriaActivity;
import com.ep4.survivethealiens.Activity.LoginActivity;
import com.ep4.survivethealiens.Activity.MissaoActivity;
import com.ep4.survivethealiens.Activity.PerfilActivity;
import com.ep4.survivethealiens.Activity.PrincipalActivity;
import com.ep4.survivethealiens.Feign.Request.JogadorRequests;
import com.ep4.survivethealiens.Helper.SaveSharedPreference;
import com.ep4.survivethealiens.Model.Jogador;
import com.ep4.survivethealiens.Model.MissaoJogador;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

/**
 * Created by Carla on 20/11/2016.
 */

public class AtualizarDadosTask extends AsyncTask<Jogador, Void, Jogador> {

    ProgressDialog pDialog;
    MissaoActivity myActivity;
    boolean userVerified;
    private Context myContext;
    Jogador jogador;

    public AtualizarDadosTask(MissaoActivity activity, Context context){
        myActivity = activity;
        myContext = context;
    }

    @Override
    protected void onPreExecute() {
        try {
            pDialog = new ProgressDialog(myContext);
            pDialog.setMessage("Atualizando missões...");
            pDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected Jogador doInBackground(Jogador... params) {
        try{
            JogadorRequests request = Feign.builder()
                    .encoder(new GsonEncoder())
                    .decoder(new GsonDecoder())
                    .logLevel(Logger.Level.FULL)
                    .target(JogadorRequests.class, "http://survivethealiens.azurewebsites.net/api/");// lá em PostagemRequest, as URIS
            //serão pegas a partir desta URL
            jogador = request.atualizarJogador(params[0].getId(), params[0]);
            if(jogador != null) {
                userVerified = true;
                MissaoJogador missaoJogador = null;
                for (int i = 0; i < LoginActivity.missaoJogadorList.size(); i++) {
                    if(LoginActivity.missaoJogadorList.get(i).getIdMissao() == myActivity.missao.getId()){
                        if(i < LoginActivity.missaoJogadorList.size()-1){
                            missaoJogador = LoginActivity.missaoJogadorList.get(i+1);
                            missaoJogador.setLiberada(true);
                            LoginActivity.missaoList.get(i+1).setLiberada(true);
                            break;
                        }
                    }
                }
                if(missaoJogador != null)
                    request.putMissaoJogador(missaoJogador.getId(), missaoJogador);
            }
        }catch (Exception e){
            userVerified = false;
            System.err.println("Erro de comunicação,");
            e.printStackTrace();
        }
        return jogador;
    }

    @Override
    protected void onPostExecute(Jogador jogador) {
        pDialog.dismiss();
        try {
            if(userVerified){
                LoginActivity.jogador = this.jogador;
                Toast.makeText(myContext, "Dados atualizados.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(myContext, HistoriaActivity.class);
                SaveSharedPreference.setJogador(myContext, jogador);
                myActivity.startActivity(intent);
            }else{
                Toast.makeText(myContext, "Houve um problema ao atualizar o cadastro. Tente novamente mais tarde.", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
