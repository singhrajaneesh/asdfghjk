package com.ekant.justbiz;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityChangePassword extends AppCompatActivity implements ModAsyncResponce
{
    EditText password, confirmpassword;
    TextView errormsg,header;
    ImageView imageView;
    String UID, UserPassword;

    Moddb db;
    ProgressBar progressBar;
    LinearLayout linearLayoutprogress;
    RelativeLayout relativeLayoutlayoutprogress;
    Boolean isInternetPresent = false;
    ModConnectionDetector connectionDetector;
    Button changepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        //create top action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setIcon(R.drawable.logo);
        header=(TextView) findViewById(R.id.changetext);
        Typeface face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");
        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        linearLayoutprogress=(LinearLayout) findViewById(R.id.llprogress);
        relativeLayoutlayoutprogress=(RelativeLayout) findViewById(R.id.rlprogressbar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        connectionDetector = new ModConnectionDetector(getApplicationContext());
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        errormsg = (TextView) findViewById(R.id.errormsg);
        changepassword = (Button) findViewById(R.id.changepassword);
        imageView = (ImageView) findViewById(R.id.alertimage);
        db = new Moddb(this);
        UID = db.getValue("UID");
        header.setTypeface(face);
       // password.setTypeface(face);
       // confirmpassword.setTypeface(face);
        errormsg.setTypeface(face);
        header.setTypeface(face);
        changepassword.setTypeface(face);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (android.R.id.home == item.getItemId())
        {
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changepass(View view)
    {
        UserPassword = password.getText().toString();
        if (password.getText().toString().equals("") && confirmpassword.getText().toString().equals(""))
        {
            errormsg.setText(" Please fill in all required fields.");
            errormsg.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }
        else if (password.getText().toString().equals(""))
        {

            errormsg.setText(" Please fill in new Password.");
            errormsg.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }
        else if (confirmpassword.getText().toString().equals(""))
        {

            errormsg.setText(" Please fill in confirm Password.");
            errormsg.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }
        else if (password.getText().toString().equals(confirmpassword.getText().toString()))
        {

            sendDataToWeb(UID, UserPassword);

        }
        else if (password.getText().toString() != confirmpassword.getText().toString())
        {
            errormsg.setText(" Oops! Please make sure new password and confirm password are same.");
            imageView.setVisibility(View.VISIBLE);
            errormsg.setVisibility(View.VISIBLE);
        }

    }

    private void sendDataToWeb(String UID, String password_right)
    {
        relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
        linearLayoutprogress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        isInternetPresent = connectionDetector.isConnectingToInternet();
        if (isInternetPresent)
        {
            JSONObject obj = new JSONObject();
            try
            {
                obj.put("UID", UID);
                obj.put("Password", password_right);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ModAsyncTask task = new ModAsyncTask();
            task.delegate = this;
            task.execute(obj.toString(), "changeuserpassword.php");


        }
        else
        {
            connectionDetector.showAlertDialog(this, "No Internet Connection", "You don't have internet connection.", false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run()
                {

                    relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
                    linearLayoutprogress.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }, 0);
        }

    }

    public void processFinish(String output)
    {
        relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
        linearLayoutprogress.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        finish();
        Intent intent = new Intent(ActivityChangePassword.this, ActivityResetPassword.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

@Override
    public void onBackPressed()
        {
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }

}

