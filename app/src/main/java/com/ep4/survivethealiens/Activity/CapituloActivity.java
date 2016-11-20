package com.ep4.survivethealiens.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ep4.survivethealiens.Feign.Task.GetMissaoByJogadorTask;
import com.ep4.survivethealiens.Helper.SaveSharedPreference;
import com.ep4.survivethealiens.Model.Jogador;
import com.ep4.survivethealiens.Model.MissaoJogador;
import com.ep4.survivethealiens.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by aluno on 07/10/2016.
 */
public class CapituloActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    GetMissaoByJogadorTask getMissaoByJogadorTask;
    public ArrayList<MissaoJogador> missaoJogadorList;

    ListView listView;

    Jogador jogador = EventBus.getDefault().getStickyEvent(Jogador.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capitulo);

        //se jogador for encontrado
        if(SaveSharedPreference.getId(this) == 0)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        initInstancesDrawer();

       // getMissaoByJogadorTask = new GetMissaoByJogadorTask(this, this);
        jogador = SaveSharedPreference.getJogador(this);
        getMissaoByJogadorTask.execute(jogador.getId());
        listView = (ListView) findViewById(R.id.listViewMissoesCap1);
    }

    private void initInstancesDrawer() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Capítulo 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Capítulo 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Capítulo 3"));
    }

    public void criarMissoes(){
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.tab_fragment_1, missaoJogadorList);
        listView.setAdapter(adapter);
        if(missaoJogadorList == null)
            Toast.makeText(this, "Nenhuma missão disponível.", Toast.LENGTH_SHORT).show();
    }
}
