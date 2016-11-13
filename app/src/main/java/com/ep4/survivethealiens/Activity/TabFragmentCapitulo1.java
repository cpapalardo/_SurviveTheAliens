package com.ep4.survivethealiens.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ep4.survivethealiens.Feign.Task.GetMissaoByJogadorTask;
import com.ep4.survivethealiens.Model.Jogador;
import com.ep4.survivethealiens.Model.MissaoJogador;
import com.ep4.survivethealiens.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by aluno on 07/10/2016.
 */
public class TabFragmentCapitulo1 extends Fragment{

    ListView listView;
    Jogador jogador;
    GetMissaoByJogadorTask getMissaoByJogadorTask;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.tab_fragment_1, container, false);

            ArrayList<String> lista = new ArrayList<>();
            lista.add("Nolito");
            lista.add("Fred");
            lista.add("Tobias");

            ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, LoginActivity.missaoJogadorList);
            listView = (ListView)rootView.findViewById(R.id.listViewMissoesCap1);
            listView.setAdapter(adapter);

            super.onActivityCreated(savedInstanceState);
            return rootView;
        }
}
