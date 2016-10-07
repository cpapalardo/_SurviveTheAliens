package com.ep4.survivethealiens;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
/**
 * Created by aluno on 07/10/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragmentCapitulo1 tab1 = new TabFragmentCapitulo1();
                return tab1;
            case 1:
                TabFragmentCapitulo2 tab2 = new TabFragmentCapitulo2();
                return tab2;
            case 2:
                TabFragmentCapitulo3 tab3 = new TabFragmentCapitulo3();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
