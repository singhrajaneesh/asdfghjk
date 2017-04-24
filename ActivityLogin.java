package com.ekant.justbiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ActivityLogin extends AppCompatActivity implements android.support.v7.app.ActionBar.TabListener{

    private ViewPager tabsviewPager;
    private AdapterTabs mTabsAdapter;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textView=(TextView)findViewById(R.id.textview);


        // This will show logo and Text in Action Bar (JB logo and Just Business Text)

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setTitle("  Just Businesses");

        tabsviewPager = (ViewPager) findViewById(R.id.tabspager);
        mTabsAdapter = new AdapterTabs(getSupportFragmentManager());
        tabsviewPager.setAdapter(mTabsAdapter);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Tab login = getSupportActionBar().newTab().setText("Login").setTabListener(this);
        Tab signup = getSupportActionBar().newTab().setText("Sign Up").setTabListener(this);
        Tab forgotpassword = getSupportActionBar().newTab().setText("Forgot Password").setTabListener(this);

        getSupportActionBar().addTab(login);
        getSupportActionBar().addTab(signup);
        getSupportActionBar().addTab(forgotpassword);


        //This helps in providing swiping effect for v7 compat library
        tabsviewPager.setOnPageChangeListener(new OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int position)
            {
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
    public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabSelected(Tab selectedtab, FragmentTransaction arg1)
    {
        // TODO Auto-generated method stub
        tabsviewPager.setCurrentItem(selectedtab.getPosition()); //update tab position on tap
    }

    @Override
    public void onTabUnselected(Tab arg0, FragmentTransaction arg1)
    {
        // TODO Auto-generated method stub

    }
    @Override public void onBackPressed()
    {

        finish();

    }


    // This onClick method basically used in FragSignup terms and conditons click.

    public void onClick(View view)
    {

        Intent intent=new Intent(ActivityLogin.this,ActivityTermCondition.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

    }
}

