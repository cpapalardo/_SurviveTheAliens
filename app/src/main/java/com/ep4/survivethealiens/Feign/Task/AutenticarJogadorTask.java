package com.ep4.survivethealiens.Feign.Task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ep4.survivethealiens.Activity.LoginActivity;
import com.ep4.survivethealiens.Activity.PrincipalActivity;
import com.ep4.survivethealiens.Feign.Request.JogadorRequests;
import com.ep4.survivethealiens.Helper.SaveSharedPreference;
import com.ep4.survivethealiens.Model.Credenciais;
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
 * Created by Carla on 04/11/2016.
 */

public class AutenticarJogadorTask extends AsyncTask<Credenciais, Void, Jogador> {

    Jogador jogador;
    ArrayList<MissaoJogador> missaoJogadorArrayList;
    ArrayList<Missao> missaoList;
    LoginActivity myActivity;
    boolean userVerified;
    private Context myContext;
    ProgressDialog pDialog;

    public AutenticarJogadorTask(LoginActivity activity, Context context){
        myActivity = activity;
        myContext = context;
    }

    @Override
    protected Jogador doInBackground(Credenciais... params) {
        try{
            JogadorRequests request = Feign.builder()
                    .encoder(new GsonEncoder())
                    .decoder(new GsonDecoder())
                    .logLevel(Logger.Level.FULL)
                    .target(JogadorRequests.class, "http://survivethealiens.azurewebsites.net/api/");// lá em PostagemRequest, as URIS
            //serão pegas a partir desta URL
            jogador = request.autenticarJogador(params[0]);

            if(jogador != null) {
                userVerified = true;
                missaoJogadorArrayList = request.getMissoesById(jogador.getId());
                missaoList = request.getMissoes();
            }
        }catch (Exception e){
            userVerified = false;
            System.err.println("Erro de comunicação.");
            e.printStackTrace();
        }
        return jogador;
    }

    @Override
    protected void onPreExecute() {
        try {
            pDialog = new ProgressDialog(myContext);
            pDialog.setMessage("Autenticando usuário...");
            pDialog.setCancelable(false);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Jogador jogador) {
        try {
            pDialog.dismiss();
            if(userVerified){
//                GetMissaoByJogadorTask getMissaoByJogadorTask = new GetMissaoByJogadorTask(myActivity, myContext);
//                getMissaoByJogadorTask.execute(jogador.getId());

                myActivity.jogador = this.jogador;
                LoginActivity.missaoList = this.missaoList;
                LoginActivity.missaoJogadorList = missaoJogadorArrayList;

                for(int i = 0; i < missaoJogadorArrayList.size(); i++){
                    if(missaoJogadorArrayList.get(i).isLiberada())
                        missaoList.get(i).setLiberada(true);
                }

                Intent intent = new Intent(myActivity, PrincipalActivity.class);
                EventBus.getDefault().postSticky(jogador);
                SaveSharedPreference.setJogador(myActivity, jogador);
                myActivity.startActivity(intent);
                Toast.makeText(myContext, "Olá de novo, " + jogador.getApelido() + "!", Toast.LENGTH_SHORT).show();
            }else{
                //ou e-mail em uso
                Toast.makeText(myContext, "Houve um problema ao efetuar a autenticação. Tente novamente mais tarde.", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //pDialogMissoes.dismiss();
    }
}
