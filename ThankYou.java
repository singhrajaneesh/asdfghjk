package com.ekant.justbiz;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThankYou extends ActionBarActivity {

    Button button;
    TextView heading,msgtext1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you2);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setIcon(R.drawable.logo);

        heading=(TextView) findViewById(R.id.heading);
        msgtext1=(TextView) findViewById(R.id.msgtext1);
        button=(Button) findViewById(R.id.button);
        Typeface face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");
        heading.setTypeface(face);
        msgtext1.setTypeface(face);
        button.setTypeface(face);


    }


    public void calldashboard(View view) {
        finish();
        Intent i = new Intent(ThankYou.this,ActivityDashBoard.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
        ThankYou.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);


    }
    @Override
    public void onBackPressed()
    {
        finish();
        Intent intent=new Intent(ThankYou.this,ActivityDashBoard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        ThankYou.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (android.R.id.home==item.getItemId())
    {
        finish();
        Intent intent=new Intent(ThankYou.this,ActivityDashBoard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        ThankYou.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
        return true;
    }
        return super.onOptionsItemSelected(item);
    }



}
