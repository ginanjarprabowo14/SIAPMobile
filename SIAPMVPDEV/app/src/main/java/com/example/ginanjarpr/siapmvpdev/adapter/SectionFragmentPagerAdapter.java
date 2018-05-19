package com.example.ginanjarpr.siapmvpdev.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.ginanjarpr.siapmvpdev.function.tiket.diterima.DiterimaFragment;
import com.example.ginanjarpr.siapmvpdev.function.tiket.belumditerima.BelumDiterimaFragment;

public class SectionFragmentPagerAdapter extends FragmentPagerAdapter {

    public SectionFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                BelumDiterimaFragment uses = new BelumDiterimaFragment();
                return uses;
            case 1:
                DiterimaFragment used = new DiterimaFragment();
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

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return "BELUM DITERIMA";
            case 1:
                return "DITERIMA";
            default:
                return null;
        }
    }

}


