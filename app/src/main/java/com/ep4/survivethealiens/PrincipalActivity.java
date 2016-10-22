package com.ep4.survivethealiens;

import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ep4.survivethealiens.Jogador;
import com.ep4.survivethealiens.R;

import org.greenrobot.eventbus.EventBus;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }

    public void abrirHistoria(View view) {
        Intent intent = new Intent(this, HistoriaActivity.class);
        startActivity(intent);
    }
	
	public void retomarMissao(View v){
        Intent intent = new Intent(this, MissaoActivity.class);
        startActivity(intent);
    }
}
