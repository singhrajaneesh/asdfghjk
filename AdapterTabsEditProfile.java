package com.ekant.justbiz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ekant on 02/03/16.
 */
public class AdapterTabsEditProfile extends FragmentStatePagerAdapter
{
    private int TOTAL_TABS = 2;

    public AdapterTabsEditProfile(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int index) {
        // TODO Auto-generated method stub
        switch (index) {
            case 0:
                return new FragCompanyInfo();


            case 1:
                return new FragPersonalInfo();


        }

        return null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return TOTAL_TABS;
    }

}



