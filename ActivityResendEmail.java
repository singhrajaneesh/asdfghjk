package com.ekant.justbiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityResendEmail extends AppCompatActivity implements  ModAsyncResponce
{

    Button button,button1;
    String resendemail;
    ProgressBar progressBar;
    Boolean isInternetPresent = false;
    ModConnectionDetector cd;
    TextView textView,msg;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resend__email);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Typeface face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");
        textView=(TextView) findViewById(R.id.alert2);
        msg=(TextView) findViewById(R.id.msg);

        button=(Button) findViewById(R.id.resend);
        button1=(Button) findViewById(R.id.backtologin);


        textView.setTypeface(face);
        msg.setTypeface(face);
        button.setTypeface(face);
        button1.setTypeface(face);


        cd = new ModConnectionDetector(getApplicationContext());
        resendemail=getIntent().getStringExtra("UID");
        progressBar=(ProgressBar)findViewById(R.id.progressBarLogin);
        // This is progress bar spinner color.animation
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffffff"), android.graphics.PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (android.R.id.home==item.getItemId())
        {
            finish();
            Intent intent=new Intent(ActivityResendEmail.this,ActivityLogin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed()
    {
        finish();
        Intent intent=new Intent(ActivityResendEmail.this,ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }

    // Button click back to login
    public void backtologin(View view)
    {
        finish();
        Intent intent=new Intent(ActivityResendEmail.this,ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
    public void resend(View view)
    {
        sendDatatoweb(resendemail);
    }

    // Button click send data to json
    private void sendDatatoweb(String resendemail)
    {

        progressBar.setVisibility(View.VISIBLE);
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent)
        {
            JSONObject obj = new JSONObject();
            try
            {
                obj.put("UID", resendemail);

            } catch (Exception e)
            {
                e.printStackTrace();
            }

            ModAsyncTask task = new ModAsyncTask();
            task.delegate = this;
            task.execute(obj.toString(), "resendlink.php");


        }
        else
        {
            cd.showAlertDialog(this, "No Internet Connection", "You don't have internet connection.", false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }, 0);
        }

    }
    @Override
    public void processFinish(String output) {
        progressBar.setVisibility(View.INVISIBLE);


        if (output != null)

        {
            String loginValue = "";
            String loginResult = output;
            try {
                JSONObject jsonObj = new JSONObject(loginResult);
                loginValue = jsonObj.getString("ResendLinkCredential");

                if (loginValue.equals("1")) {

                    AlertDialog alertDialog = new AlertDialog.Builder(ActivityResendEmail.this).create();
                    // Setting Dialog Title
                    alertDialog.setTitle("Email Sent");
                    // Setting Dialog Message
                    alertDialog.setMessage("Your Email verification link has been sent to your registered Email ID.");
                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.password_sent);
                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog closed
                            Intent intent = new Intent(ActivityResendEmail.this, ActivityLogin.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            overridePendingTransition(R.anim.left_in, R.anim.right_out);
                        }
                    });
                    // Showing Alert Message
                    alertDialog.show();
                }
            }
            catch (JSONException e)

            {
                e.printStackTrace();
            }
        }
    }
}
