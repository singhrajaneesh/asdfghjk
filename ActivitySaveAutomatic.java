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
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivitySaveAutomatic extends AppCompatActivity implements ModAsyncResponce,ModProfileResponce
{
    final String imageurl = "http://www.justbusinesses.net/bridge/uploads/category-images/";
    ProgressBar progressBar;
    Moddb db =  new Moddb(this);
    Boolean isInternetPresent = false;
    ModConnectionDetector connectionDetector;
    ModConnectionDetector cd;
    TextView tv1,tv2;
    int profilecomplete;
    String Category,Vertical,Image,GetBusiness,industry1,industries,name,categoryid,subcatname,subcatid,verticals,verticalid,UID,shraredUID;
    SharedPreferences sharedpreferences;
    String MyPREFERENCES="MyPREFERENCES";
    SharedPreferences.Editor editor;
    JSONObject jsonObject;
    String completelevel;
    LinearLayout linearLayoutprogress;
    RelativeLayout relativeLayoutlayoutprogress;
    Typeface face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_automatic);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setIcon(R.drawable.logo);

        face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");
        linearLayoutprogress=(LinearLayout) findViewById(R.id.llprogress);
        relativeLayoutlayoutprogress=(RelativeLayout) findViewById(R.id.rlprogressbar);
        progressBar=(ProgressBar)findViewById(R.id.progressBarLogin);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        connectionDetector = new ModConnectionDetector(getApplicationContext());
        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        shraredUID = sharedpreferences.getString("UID", null);
        industry1 = getIntent().getStringExtra("industry");

      if (industry1.equals("Own"))
        {


            Category = getIntent().getStringExtra("CategoryName");
            categoryid = getIntent().getStringExtra("CategoryID");
            subcatname = getIntent().getStringArrayListExtra("selectedSubcatname").toString().substring(1, getIntent().getStringArrayListExtra("selectedSubcatname").toString().length() - 1);
            subcatid = getIntent().getStringArrayListExtra("selected").toString().substring(1, getIntent().getStringArrayListExtra("selected").toString().length() - 1);
            verticals = getIntent().getStringArrayListExtra("selectedverticals").toString().substring(1, getIntent().getStringArrayListExtra("selectedverticals").toString().length() - 1);
            verticalid = getIntent().getStringArrayListExtra("selectedverticalsID").toString().substring(1, getIntent().getStringArrayListExtra("selectedverticalsID").toString().length() - 1);
            Vertical = getIntent().getStringExtra("Vertical");
            Image = getIntent().getStringExtra("Image");
            GetBusiness = getIntent().getStringExtra("GetBusiness");
            tv1 = (TextView) findViewById(R.id.saveautomaticselectedcategory);
            tv1.setText(Html.fromHtml(getIntent().getStringExtra("CategoryName")));
            industries="Own";
            tv1.setTypeface(face);



            tv2 = (TextView) findViewById(R.id.saveautomatictitle1);
            tv2.setText(subcatname);

            tv2.setTypeface(face);


        }
      else if (industry1.equals("searchflow"))
      {

          Category = getIntent().getStringExtra("CategoryName");
          tv1 = (TextView) findViewById(R.id.saveautomaticselectedcategory);
          tv1.setText(Html.fromHtml(getIntent().getStringExtra("CategoryName")));
          categoryid = getIntent().getStringExtra("CategoryID");
          subcatname =getIntent().getStringExtra("selectedSubcatname");
          subcatid = getIntent().getStringExtra("selected");
          verticals =  getIntent().getStringExtra("selectedverticals");
          verticalid = getIntent().getStringExtra("selectedverticalsID");
          industries="Own";
          tv1.setTypeface(face);


      }
      else if (industry1.equals("Give"))
      {

          Category = getIntent().getStringExtra("CategoryName");
          categoryid = getIntent().getStringExtra("CategoryID");
          Image = getIntent().getStringExtra("Image");
          tv1 = (TextView) findViewById(R.id.saveautomaticselectedcategory);
          tv1.setText(Html.fromHtml(getIntent().getStringExtra("CategoryName")));
          subcatname = getIntent().getStringArrayListExtra("selectedSubcatname").toString().substring(1, getIntent().getStringArrayListExtra("selectedSubcatname").toString().length() - 1);
          subcatid = getIntent().getStringArrayListExtra("selected").toString().substring(1, getIntent().getStringArrayListExtra("selected").toString().length() - 1);
          industries="Give";
          tv1.setTypeface(face);

          tv2 = (TextView) findViewById(R.id.saveautomatictitle1);
          tv2.setText(subcatname);

          tv2.setTypeface(face);

      }
      else if (industry1.equals("givesearchflow"))
      {

          Category = getIntent().getStringExtra("CategoryName");
          categoryid = getIntent().getStringExtra("CategoryID");
          tv1 = (TextView) findViewById(R.id.saveautomaticselectedcategory);
          tv1.setText(Html.fromHtml(getIntent().getStringExtra("CategoryName")));
          industries="Give";
          tv1.setTypeface(face);

      }

        sendDataToWeb();

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendDataToWeb()
    {
        isInternetPresent = connectionDetector.isConnectingToInternet();
        if (isInternetPresent)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
                    linearLayoutprogress.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }, 1000);


            JSONObject obj = new JSONObject();
            try
            {
                obj.put("UID",shraredUID);
                obj.put("CategoryID",categoryid);
                obj.put("SubCategoryIDs",subcatid);
                obj.put("VerticalIDs",verticalid);
                obj.put("BType",industries);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            ModAsyncTask task = new ModAsyncTask();
            task.delegate = this;
            task.execute(obj.toString(), "member-verticals.php");

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

 
        if(output!=null)
       {
           //Toast.makeText(ActivitySaveAutomatic.this, "output null", Toast.LENGTH_SHORT).show();
       }

        getUserdetail(shraredUID);



    }
    public void getUserdetail(String uid)

    {

        if (isInternetPresent)
        {
            JSONObject obj = new JSONObject();
            try
            {
                obj.put("UID",uid );

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            ModgetProfile task1 = new ModgetProfile();
            task1.delegate = this;
            task1.execute(obj.toString(), "member-profile.php");
        }
        else
        {
            cd.showAlertDialog(ActivitySaveAutomatic.this, "No Internet Connection", "You don't have internet connection.", false);
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
    public void getProfile(String output)
    {



        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putString("UserInfo", output);
        editor.commit();
        relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
        linearLayoutprogress.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String strJson = sharedpreferences.getString("UserInfo", null);

        try
        {
            jsonObject = new JSONObject(strJson);
            completelevel = jsonObject.getString("CompleteLevel");
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        profilecomplete = Integer.parseInt(completelevel);

        System.out.println("profilecomplete "+profilecomplete);

        if(profilecomplete!=4)//5
        {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(ActivitySaveAutomatic.this, ActivityMyProfile.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }, 2000);

        }
        else
        {


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(ActivitySaveAutomatic.this, ActivityDashBoard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }, 2000);

        }

    }

}
