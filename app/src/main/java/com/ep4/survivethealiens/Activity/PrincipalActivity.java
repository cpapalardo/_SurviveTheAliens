package com.ep4.survivethealiens.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ep4.survivethealiens.Helper.SaveSharedPreference;
import com.ep4.survivethealiens.Model.Jogador;
import com.ep4.survivethealiens.Model.Missao;
import com.ep4.survivethealiens.R;

import org.greenrobot.eventbus.EventBus;

public class PrincipalActivity extends AppCompatActivity {

    Jogador jogador;
    TextView textViewNomeMissao;
    Missao missao = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //se jogador for encontrado
        if(SaveSharedPreference.getId(this) == 0)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        textViewNomeMissao = (TextView) findViewById(R.id.textViewNomeMissao);

        for (int i = 0; i < LoginActivity.missaoList.size(); i++) {
            boolean liberada = LoginActivity.missaoList.get(i).isLiberada();
            if(!liberada){
                missao = LoginActivity.missaoList.get(i-1);
                break;
            }
        }
        if(missao == null){
            missao = LoginActivity.missaoList.get(LoginActivity.missaoList.size()-1);
        }
        textViewNomeMissao.setText(missao.getNome());
        EventBus.getDefault().postSticky(missao);

        //jogador = EventBus.getDefault().getStickyEvent(Jogador.class);
    }

    public void abrirHistoria(View view) {
        Intent intent = new Intent(this, HistoriaActivity.class);
        startActivity(intent);
    }

    public void abrirPerfil(View view) {
        Intent intent = new Intent(this, PerfilActivity.class);
        startActivity(intent);
    }

    public void abrirOpcoes(View view){
        Intent intent = new Intent(this, OpcoesActivity.class);
        startActivity(intent);
    }
	
	public void retomarMissao(View v){
        EventBus.getDefault().postSticky(missao);
        Intent intent = new Intent(this, MissaoActivity.class);
        startActivity(intent);
    }
}
