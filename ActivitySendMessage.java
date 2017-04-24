package com.ekant.justbiz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivitySendMessage extends AppCompatActivity implements ModAsyncResponce {

    String UID,msg,VerticalsID;
    EditText etmsg;
    Boolean isInternetPresent = false;
    ModConnectionDetector connectionDetector;
    ProgressBar progressBar;
    LinearLayout linearLayoutprogress;
    RelativeLayout relativeLayoutlayoutprogress;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setIcon(R.drawable.logo);
        Typeface face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");

        TextView tv1 = (TextView) findViewById(R.id.titlename);
        TextView tv2 = (TextView) findViewById(R.id.category);
        TextView tv3 = (TextView) findViewById(R.id.subcategory);
        TextView tv4 = (TextView) findViewById(R.id.vertical);
        TextView title = (TextView) findViewById(R.id.title);
        btnlogin = (Button) findViewById(R.id.btnlogin);

        tv1.setText(getIntent().getStringExtra("CategoryName"));
        tv2.setText(getIntent().getStringExtra("Category"));
        tv3.setText(getIntent().getStringExtra("SubCategory"));
        tv4.setText(getIntent().getStringExtra("Verticals"));
        VerticalsID = getIntent().getStringExtra("VerticalID");
        etmsg = (EditText) findViewById(R.id.edttext);
        connectionDetector = new ModConnectionDetector(getApplicationContext());
        Moddb db = new Moddb(this);
        UID = db.getValue("UID");

        linearLayoutprogress=(LinearLayout) findViewById(R.id.llprogress);
        relativeLayoutlayoutprogress=(RelativeLayout) findViewById(R.id.rlprogressbar);
        progressBar=(ProgressBar)findViewById(R.id.progressBarLogin);
        // This is progress bar spinner color.animation
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), android.graphics.PorterDuff.Mode.SRC_ATOP);

        tv1.setTypeface(face);
        tv2.setTypeface(face);
        tv3.setTypeface(face);
        tv4.setTypeface(face);
        etmsg.setTypeface(face);
        title.setTypeface(face);
        btnlogin.setTypeface(face);


    }


    @Override
    public void onBackPressed()
    {
        finish();

        ActivitySendMessage.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (android.R.id.home==item.getItemId())
        {
            finish();
            ActivitySendMessage.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void Click(View view)
    {

        if (etmsg.getText().toString().equals(""))
        {
            Toast.makeText(ActivitySendMessage.this, "Please Fill Message to your Friends.", Toast.LENGTH_SHORT).show();

        }
                else
        {
            msg = etmsg.getText().toString();
            sendDataToWeb();
            relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
            linearLayoutprogress.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);

        }

    }

    public void sendDataToWeb()
    {

        isInternetPresent = connectionDetector.isConnectingToInternet();
        if (isInternetPresent)
        {
            JSONObject obj = new JSONObject();
            try
            {
                obj.put("FromUID", UID);
                obj.put("Message", etmsg.getText().toString());
                obj.put("VerticalsID", VerticalsID);

            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            ModAsyncTask task = new ModAsyncTask();
            task.delegate = this;
            task.execute(obj.toString(), "messagebroadcastings.php");

            System.out.println("Send Message"+obj.toString());

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
    public void processFinish(String output)
    {

        relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
        linearLayoutprogress.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        Intent thankyou = new Intent(this,ActivityThankYou.class);
        startActivity(thankyou);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }



}
