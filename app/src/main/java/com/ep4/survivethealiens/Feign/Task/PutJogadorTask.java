package com.ep4.survivethealiens.Feign.Task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ep4.survivethealiens.Activity.CadastroActivity;
import com.ep4.survivethealiens.Activity.PrincipalActivity;
import com.ep4.survivethealiens.Feign.Request.JogadorRequests;
import com.ep4.survivethealiens.Model.Jogador;

import org.greenrobot.eventbus.EventBus;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

/**
 * Created by Carla on 23/10/2016.
 */

public class PutJogadorTask extends AsyncTask<Jogador, Void, Jogador> {
    ProgressDialog pDialog;
    CadastroActivity myActivity;
    boolean userVerified;
    private Context myContext;
    Jogador jogador;

    public PutJogadorTask(CadastroActivity activity, Context context){
        myActivity = activity;
        myContext = context;
    }
    @Override
    public Jogador doInBackground(Jogador... params) {
        try{
            JogadorRequests request = Feign.builder()
                    .encoder(new GsonEncoder())
                    .decoder(new GsonDecoder())
                    .logLevel(Logger.Level.FULL)
                    .target(JogadorRequests.class, "http://survivethealiens.azurewebsites.net/api/");// lá em PostagemRequest, as URIS
            //serão pegas a partir desta URL
            jogador = request.atualizarJogador(params[0].getId(), params[0]);
            if(jogador != null)
                userVerified = true;
        }catch (Exception e){
            userVerified = false;
            System.err.println("Erro de comunicação,");
            e.printStackTrace();
        }
        return jogador;
    }

    @Override
    protected void onPreExecute() {
        try {
            pDialog = new ProgressDialog(myContext);
            pDialog.setMessage("Atualizando usuário...");
            pDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Jogador jogador) {
        try {
            if(!userVerified){
                myActivity.jogador = this.jogador;
                Intent intent = new Intent(myActivity, PrincipalActivity.class);
                EventBus.getDefault().postSticky(jogador);
                myActivity.startActivity(intent);
            }else{
                //ou e-mail em uso
                Toast.makeText(myContext, "Houve um problema ao atualizar o cadastro. Tente novamente mais tarde.", Toast.LENGTH_LONG);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        pDialog.dismiss();
    }
}
