package com.ekant.justbiz;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class ActivityEditProfile extends AppCompatActivity implements android.support.v7.app.ActionBar.TabListener  {


    private ViewPager tabsviewPager;
    private AdapterTabsEditProfile mTabsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        // This will show logo and Text in Action Bar (JB logo and Just Business Text)

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setTitle("  Just Businesses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabsviewPager = (ViewPager) findViewById(R.id.tabspager);
        mTabsAdapter = new AdapterTabsEditProfile(getSupportFragmentManager());
        tabsviewPager.setAdapter(mTabsAdapter);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab basic = getSupportActionBar().newTab().setText("Company Info").setTabListener(this);
        ActionBar.Tab Personal = getSupportActionBar().newTab().setText("Personal Info").setTabListener(this);



        getSupportActionBar().addTab(basic);
        getSupportActionBar().addTab(Personal);

        //This helps in providing swiping effect for v7 compat library
        tabsviewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                getSupportActionBar().setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });


    }

    @Override
    public void onTabReselected(ActionBar.Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabSelected(ActionBar.Tab selectedtab, FragmentTransaction arg1)
    {
        // TODO Auto-generated method stub
        tabsviewPager.setCurrentItem(selectedtab.getPosition()); //update tab position on tap
    }

    @Override
    public void onTabUnselected(ActionBar.Tab arg0, FragmentTransaction arg1)
    {
        // TODO Auto-generated method stub

    }





    @Override
    public void onBackPressed()
    {
        finish();
        Intent intent=new Intent(ActivityEditProfile.this,ActivityMyProfile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (android.R.id.home==item.getItemId())
        {
            finish();
            Intent intent=new Intent(ActivityEditProfile.this,ActivityMyProfile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
