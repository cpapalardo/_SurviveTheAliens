package com.ep4.survivethealiens.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ep4.survivethealiens.R;

/**
 * Created by aluno on 07/10/2016.
 */
public class TabFragmentCapitulo1 extends Fragment{

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.tab_fragment_1, container, false);
        }
}
