package com.exemple.enjoyfood.myadapter;

import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import dz.baichoudjedi.lovefood.ui.Tab1;
import dz.baichoudjedi.lovefood.ui.Tab2;
import dz.baichoudjedi.lovefood.ui.Tab3;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNotOfTabs;

    public PagerAdapter(FragmentManager fm, int mNotOfTabs){
        super(fm);
        this.mNotOfTabs = mNotOfTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                Tab1 tab1 = new Tab1();
                return tab1;
            case 1:
                Tab2 tab2 = new Tab2();
                return tab2;
            case 2:
                Tab3 tab3 = new Tab3();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNotOfTabs;
    }
}
