package com.example.ginanjarpr.siapnonmvp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ginanjarpr.siapnonmvp.function.buataduan.StepDuaFragment;
import com.example.ginanjarpr.siapnonmvp.function.buataduan.StepSatuFragment;

public class BuatAduanFragmentAdapter extends FragmentPagerAdapter {

    public BuatAduanFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                StepSatuFragment uses = new StepSatuFragment();
                return uses;
            case 1:
                StepDuaFragment used = new StepDuaFragment();
                return used;
            default:
                return null;
        }

    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 2;
    }


}