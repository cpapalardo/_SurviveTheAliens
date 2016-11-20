package com.ep4.survivethealiens.Helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ep4.survivethealiens.Activity.HistoriaActivity;
import com.ep4.survivethealiens.Activity.LoginActivity;
import com.ep4.survivethealiens.Model.Missao;
import com.ep4.survivethealiens.Model.MissaoJogador;
import com.ep4.survivethealiens.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Carla on 15/11/2016.
 */

public class MissaoAdapter extends BaseAdapter{

    private FragmentActivity myContext;
    private LayoutInflater myInflater;
    private ArrayList<Missao> myDataSource;

    public MissaoAdapter(FragmentActivity context, ArrayList<Missao> missaoArrayList)
    {
        myContext = context;
        myDataSource = missaoArrayList;
        myInflater = LayoutInflater.from(context); //(LayoutInflater) myContext.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
    }
    @Override
    public int getCount() {
        return myDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return myDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = myInflater.inflate(R.layout.listview_item, parent, false);
        }
        Missao missao = myDataSource.get(position);

        //pegando referências
        TextView itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
        TextView itemSubtitle = (TextView) convertView.findViewById(R.id.itemSubtitle);
        TextView itemDetail = (TextView) convertView.findViewById(R.id.itemDetail);
        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.itemThumbnail);

        itemTitle.setText(missao.getNome());
        thumbnail.setImageResource(R.drawable.iconplay);

        for (MissaoJogador mJogador: LoginActivity.missaoJogadorList
             ) {
            if(mJogador.getIdMissao() == missao.getId()) {
                if(mJogador.isLiberada()) {
                    missao.setLiberada(true);
                    itemDetail.setText("Liberada");
                    thumbnail.setImageResource((R.drawable.padlockopen64black));
                }
                else{
                    missao.setLiberada(false);
                    itemDetail.setText("Bloqueada");
                    thumbnail.setImageResource(R.drawable.padlocklocked64black);
                }
            }
        }


        return convertView;
    }
}
