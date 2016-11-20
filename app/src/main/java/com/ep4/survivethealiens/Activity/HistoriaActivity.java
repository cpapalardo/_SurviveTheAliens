package com.ep4.survivethealiens.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.ep4.survivethealiens.Feign.Task.GetMissaoByJogadorTask;
import com.ep4.survivethealiens.Helper.SaveSharedPreference;
import com.ep4.survivethealiens.Model.Jogador;
import com.ep4.survivethealiens.Model.MissaoJogador;
import com.ep4.survivethealiens.Helper.PagerAdapter;
import com.ep4.survivethealiens.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by aluno on 07/10/2016.
 */
public class HistoriaActivity extends AppCompatActivity{

    GetMissaoByJogadorTask getMissaoByJogadorTask;
    public static ArrayList<MissaoJogador> missaoJogadorList;
    ListView listView;
    Jogador jogador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);

        //se jogador for encontrado
        if(SaveSharedPreference.getId(this).length() == 0)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        jogador = EventBus.getDefault().getStickyEvent(Jogador.class);

        final LayoutInflater factory = getLayoutInflater();
        final View textEntryView = factory.inflate(R.layout.tab_fragment_1, null);
        listView = (ListView)textEntryView.findViewById(R.id.listViewMissoesCap1);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Capítulo 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Capítulo 2"));
        //tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
