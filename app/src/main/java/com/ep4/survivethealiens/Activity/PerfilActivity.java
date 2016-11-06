package com.ep4.survivethealiens.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ep4.survivethealiens.Feign.Task.PutJogadorTask;
import com.ep4.survivethealiens.R;

public class PerfilActivity extends AppCompatActivity {

    PutJogadorTask putJogadorTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
    }

    public void atualizarJogador(View view) {

    }
}
