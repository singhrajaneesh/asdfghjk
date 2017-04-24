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

public class ActivitySignUpClick extends AppCompatActivity implements View.OnClickListener

{
    TextView textView,headermsg;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_click);



        Typeface face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");

        headermsg = (TextView) findViewById(R.id.heading);
        headermsg.setTypeface(face);



        textView=(TextView) findViewById(R.id.tvPerson);
        textView.setTypeface(face);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        button=(Button) findViewById(R.id.btnlogin);
        button.setOnClickListener((View.OnClickListener) this);

        button.setTypeface(face);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (android.R.id.home==item.getItemId())
        {
            finish();
            Intent intent=new Intent(ActivitySignUpClick.this,ActivityLogin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            ActivitySignUpClick.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        finish();
        Intent intent=new Intent(ActivitySignUpClick.this,ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        ActivitySignUpClick.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }
    @Override
    public void onClick(View v)
    {
        Intent intent=new Intent(ActivitySignUpClick.this,ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        ActivitySignUpClick.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}

