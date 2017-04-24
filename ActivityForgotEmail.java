package com.ekant.justbiz;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityForgotEmail extends AppCompatActivity  implements View.OnClickListener{
    Button button;
    TextView textView,errormsg;

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_email);

        Typeface face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");
        errormsg=(TextView) findViewById(R.id.alert);
        textView=(TextView) findViewById(R.id.text2);
        button=(Button) findViewById(R.id.button);
        button.setOnClickListener((View.OnClickListener) this);



        errormsg.setTypeface(face);
        textView.setTypeface(face);
        button.setTypeface(face);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (android.R.id.home==item.getItemId())
        {
            finish();
            Intent intent=new Intent(ActivityForgotEmail.this,ActivityLogin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            ActivityForgotEmail.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
    // Button click move to Login
    @Override
    public void onClick(View v)
    {
        finish();
        Intent intent=new Intent(ActivityForgotEmail.this,ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        ActivityForgotEmail.this. overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
    // mobile backpressed move to login
    @Override
    public void onBackPressed()
    {
        finish();
        Intent intent=new Intent(ActivityForgotEmail.this,ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        ActivityForgotEmail.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
