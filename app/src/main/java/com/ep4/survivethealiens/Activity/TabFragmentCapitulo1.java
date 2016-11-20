package com.ep4.survivethealiens.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ep4.survivethealiens.Helper.MissaoAdapter;
import com.ep4.survivethealiens.Model.Missao;
import com.ep4.survivethealiens.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by aluno on 07/10/2016.
 */
public class TabFragmentCapitulo1 extends Fragment{

    ListView listView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.tab_fragment_1, container, false);

            MissaoAdapter adapter = new MissaoAdapter(getActivity(), LoginActivity.missaoList);
            listView = (ListView)rootView.findViewById(R.id.listViewMissoesCap1);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Missao missao = LoginActivity.missaoList.get(position);

                    if(missao.isLiberada()){
                        EventBus.getDefault().postSticky(missao);
                         Intent detailIntent = new Intent(getContext(), MissaoActivity.class);
                        startActivity(detailIntent);
                    }else
                        Toast.makeText(getContext(), "Missão: " + missao.getNome(), Toast.LENGTH_SHORT).show();
                }
            });

            super.onActivityCreated(savedInstanceState);
            return rootView;
        }
}
