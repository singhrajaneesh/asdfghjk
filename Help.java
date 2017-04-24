package com.ekant.justbiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Help extends AppCompatActivity implements ModAsyncResponce {

    String MyPREFERENCES = "MyPREFERENCES";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    TextView fname,lastname,email,FirstName,LastName,Email;
    EditText editText;
    Button save;
    ProgressBar progressBar;
    LinearLayout linearLayoutprogress;
    RelativeLayout relativeLayoutlayoutprogress;
    Boolean isInternetPresent = false;
    ModConnectionDetector connectionDetector;
    String f_name,l_name,E_mail,feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setIcon(R.drawable.logo);

        Typeface face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");

        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        linearLayoutprogress=(LinearLayout) findViewById(R.id.llprogress);
        relativeLayoutlayoutprogress=(RelativeLayout) findViewById(R.id.rlprogressbar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        connectionDetector = new ModConnectionDetector(getApplicationContext());
        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String strJson = sharedpreferences.getString("UserInfo", null);
        FirstName=(TextView) findViewById(R.id.helpfirstname);
        LastName=(TextView) findViewById(R.id.helplastname);
        Email=(TextView) findViewById(R.id.helpemail);
        fname=(TextView) findViewById(R.id.helpfn);
        lastname=(TextView) findViewById(R.id.helpln);
        email=(TextView) findViewById(R.id.helpemail1);


        editText=(EditText) findViewById(R.id.helpfeedback);

        save=(Button) findViewById(R.id.helpsave);


        if (strJson == null)
        {
            Toast.makeText(Help.this, "Oops! Error Occured can't fetch data", Toast.LENGTH_SHORT).show();

        }
        else
        {
            try {
                JSONObject jsonObject = new JSONObject(strJson);
                fname.setText(jsonObject.getString("FName"));
                lastname.setText(jsonObject.getString("LName"));
                email.setText(jsonObject.getString("LoginEmail"));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }



        FirstName.setTypeface(face);
        LastName.setTypeface(face);
        Email.setTypeface(face);
        fname.setTypeface(face);
        lastname.setTypeface(face);
        email.setTypeface(face);
        editText.setTypeface(face);



    }

public void save(View view)
{

    f_name=fname.getText().toString();
    l_name=lastname.getText().toString();
    E_mail=email.getText().toString();
    feedback=editText.getText().toString();

    if (editText.getText().toString().equals(""))
    {

    Toast.makeText(this, "Please fill Query", Toast.LENGTH_SHORT).show();
    }

    else
    {

        sendDataToWeb();
    }


}

    private void sendDataToWeb(){
        relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
        linearLayoutprogress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        isInternetPresent = connectionDetector.isConnectingToInternet();
        if (isInternetPresent)
        {

            JSONObject obj = new JSONObject();
            try
            {
                obj.put("FirstName", f_name);
                obj.put("LastName", l_name);
                obj.put("Email", E_mail);
                obj.put("Query", feedback);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ModAsyncTask task = new ModAsyncTask();
            task.delegate = this;
            task.execute(obj.toString(), "support.php");

        }
        else
        {
            connectionDetector.showAlertDialog(this, "No Internet Connection", "You don't have internet connection.", false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
                    linearLayoutprogress.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }, 0);
        }

    }



    @Override
    public void processFinish(String output) {

        relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
        linearLayoutprogress.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        Intent intent = new Intent(Help.this, ThankYou.class);
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


}
