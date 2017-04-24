package com.ekant.justbiz;

/**
 * Created by ekant on 12/12/15.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class AdapterTabs extends FragmentStatePagerAdapter{

    private int TOTAL_TABS = 3;

    public AdapterTabs(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int index) {
        // TODO Auto-generated method stub
        switch (index) {
            case 0:
                return new FragLogin();

            case 1:
                return new FragSignup();
            case 2:
                return new FragForgotPassword();

        }

        return null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return TOTAL_TABS;
    }

}


