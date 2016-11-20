package com.ep4.survivethealiens.Feign.Task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ep4.survivethealiens.Activity.CadastroActivity;
import com.ep4.survivethealiens.Activity.PrincipalActivity;
import com.ep4.survivethealiens.Feign.Request.JogadorRequests;
import com.ep4.survivethealiens.Helper.SaveSharedPreference;
import com.ep4.survivethealiens.Model.Jogador;
import com.ep4.survivethealiens.Model.Missao;
import com.ep4.survivethealiens.Model.MissaoJogador;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

/**
 * Created by Carla on 23/10/2016.
 */

public class PostJogadorTask extends AsyncTask<Jogador, Void, Jogador> {
    ProgressDialog pDialog;
    CadastroActivity myActivity;
    boolean userVerified;
    private Context myContext;
    Jogador jogador;
    ArrayList<MissaoJogador> missaoJogadorArrayList;
    ArrayList<Missao> missaoList;

    public PostJogadorTask(CadastroActivity activity, Context context){
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
            jogador = request.criarJogador(params[0]);
            if(jogador != null) {
                userVerified = true;
                missaoJogadorArrayList = request.getMissoesById(jogador.getId());
                missaoList = request.getMissoes();
            }
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
            pDialog.setMessage("Cadastrando usuário...");
            pDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Jogador jogador) {
        try {
            if(userVerified){
                myActivity.jogador = this.jogador;
                Intent intent = new Intent(myActivity, PrincipalActivity.class);
                EventBus.getDefault().postSticky(jogador);
                SaveSharedPreference.setJogador(myContext, jogador);
                myActivity.startActivity(intent);
            }else{
                //ou e-mail em uso
                myActivity.textErroCadastro.setText("E-mail já cadastrado.");
                //Toast.makeText(myContext, "Houve um problema ao efetuar o cadastro. Tente novamente mais tarde.", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        pDialog.dismiss();
    }
}
