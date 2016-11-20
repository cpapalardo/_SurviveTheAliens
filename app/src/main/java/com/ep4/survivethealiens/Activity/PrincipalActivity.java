package com.ep4.survivethealiens.Activity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ep4.survivethealiens.Helper.SaveSharedPreference;
import com.ep4.survivethealiens.Model.Jogador;
import com.ep4.survivethealiens.OpcoesActivity;
import com.ep4.survivethealiens.R;

import org.greenrobot.eventbus.EventBus;

public class PrincipalActivity extends AppCompatActivity {

    Jogador jogador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //se jogador for encontrado
        if(SaveSharedPreference.getId(this).length() == 0)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        jogador = EventBus.getDefault().getStickyEvent(Jogador.class);
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
        Intent intent = new Intent(this, MissaoActivity.class);
        startActivity(intent);
    }
}
