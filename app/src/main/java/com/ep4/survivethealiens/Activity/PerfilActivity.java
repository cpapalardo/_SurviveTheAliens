package com.ep4.survivethealiens.Activity;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ep4.survivethealiens.Feign.Task.AutenticarJogadorTask;
import com.ep4.survivethealiens.Feign.Task.PutJogadorTask;
import com.ep4.survivethealiens.Model.Jogador;
import com.ep4.survivethealiens.R;

import org.greenrobot.eventbus.EventBus;

public class PerfilActivity extends AppCompatActivity {

    PutJogadorTask putJogadorTask;
    public Jogador jogador;
    TextView textNome, textApelido, textEmail, textSenha;
    Spinner generoSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        jogador = EventBus.getDefault().getStickyEvent(Jogador.class);

        textNome = (TextView) findViewById(R.id.textNome);
        textApelido = (TextView) findViewById(R.id.textApelido);
        textEmail = (TextView) findViewById(R.id.textEmail);
        textSenha = (TextView) findViewById(R.id.textSenha);

        textNome.setText(jogador.getNome());
        textApelido.setText(jogador.getApelido());
        textEmail.setText(jogador.getEmail());
        textSenha.setText(jogador.getSenha());
    }

    public void atualizarJogador(View view) {
        try {
            jogador.setNome(textNome.getText().toString());
            jogador.setApelido(textApelido.getText().toString());
            jogador.setEmail(textEmail.getText().toString());
            jogador.setSenha(textSenha.getText().toString());

            putJogadorTask = new PutJogadorTask(this, this);
            putJogadorTask.execute(jogador);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
