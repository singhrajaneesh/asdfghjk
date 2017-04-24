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
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivitySaveTags extends AppCompatActivity implements ModAsyncResponce,ModProfileResponce
{
    final String imageurl = "http://www.justbusinesses.net/bridge/uploads/category-images/";
    String name,categoryname,categoryid,subcatname,subcatid,verticals,verticalid,shraredUID,MyPREFERENCES = "MyPREFERENCES";
    EditText et;
    JSONObject senddata;
    Moddb db =  new Moddb(this);
    Boolean isInternetPresent = false;
    ProgressBar progressBar;
    ModConnectionDetector connectionDetector;
    ModConnectionDetector cd;
    TextView tv1,tv3 ;
    ImageView imageView;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String a;
    LinearLayout linearLayoutprogress;
    RelativeLayout relativeLayoutlayoutprogress;
    Typeface face;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_save_tags);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
            getSupportActionBar().setIcon(R.drawable.logo);
            senddata = new JSONObject();

            a=getIntent().getStringExtra("Search");
            sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            shraredUID = sharedpreferences.getString("UID", null);

            linearLayoutprogress=(LinearLayout) findViewById(R.id.llprogress);
            relativeLayoutlayoutprogress=(RelativeLayout) findViewById(R.id.rlprogressbar);
            progressBar=(ProgressBar)findViewById(R.id.progressBarLogin);
            progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), android.graphics.PorterDuff.Mode.SRC_ATOP);
            tv1 = (TextView) findViewById(R.id.selectedcategory);
            tv3 = (TextView) findViewById(R.id.savetagstagtext);
            imageView = (ImageView) findViewById(R.id.categoryimage);
            connectionDetector = new ModConnectionDetector(getApplicationContext());
            et = (EditText) findViewById(R.id.verticalname);
            categoryid = getIntent().getStringExtra("CategoryID");

                save=(Button) findViewById(R.id.save);
                face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");
                tv1.setTypeface(face);
                tv3.setTypeface(face);
                 et.setTypeface(face);
                tv3.setTypeface(face);
                save.setTypeface(face);

        // Value is getting form GetBusiness via searchCategory
        if (a.equals("searchflow"))
            {
                tv1.setText(Html.fromHtml(getIntent().getStringExtra("CategoryName")));
                tv3.setText(Html.fromHtml("<b> Businesses-tags </b>" + " : " + getIntent().getStringExtra("selectedSubcatname")));
                subcatid = getIntent().getStringExtra("selected");
                ModImageLoader imageLoader = new ModImageLoader(this);
               // imageLoader.DisplayImage(imageurl + getIntent().getStringExtra("Image"), imageView);
                tv1.setTypeface(face);
                tv3.setTypeface(face);




            }

        // Value is getting form GetBusiness via Normal flow menas Choose category,choose sub category etc...
        else if(a.equals("normalflow"))
            {

                tv1.setText(Html.fromHtml(getIntent().getStringExtra("CategoryName")));
                tv3.setText(Html.fromHtml("<b> Sub Category </b>" + " : " + getIntent().getStringArrayListExtra("selectedSubcatname").toString().substring(1, getIntent().getStringArrayListExtra("selectedSubcatname").toString().length() - 1)));
                ModImageLoader imageLoader = new ModImageLoader(this);
              //  imageLoader.DisplayImage(imageurl + getIntent().getStringExtra("Image"), imageView);




                categoryname = getIntent().getStringExtra("CategoryName");
                verticals = getIntent().getStringExtra("Vertical");
                subcatname = getIntent().getStringArrayListExtra("selectedSubcatname").toString().substring(1, getIntent().getStringArrayListExtra("selectedSubcatname").toString().length() - 1);
                subcatid = getIntent().getStringArrayListExtra("selected").toString().substring(1, getIntent().getStringArrayListExtra("selected").toString().length() - 1);

                tv1.setTypeface(face);
                tv3.setTypeface(face);
            }

        try
            {
                senddata.put("UID",shraredUID);
                senddata.put("CategoryID", getIntent().getStringExtra("CategoryID"));
                senddata.put("SubcategoryID", subcatid);
                senddata.put("VerticalsID", verticalid);

            }
        catch (JSONException e)
            {
                e.printStackTrace();
            }

// onCreate finished here

    }

 // Resonsible for ActionBar Back press icon
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (android.R.id.home==item.getItemId())
            {
                finish();
                ActivitySaveTags.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
                return true;
            }
            return super.onOptionsItemSelected(item);
    }

// Responsible for backpress arroy icon on Phone
    @Override
    public void onBackPressed()
            {
                    finish();
                    ActivitySaveTags.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);

            }

    public void savevertical(View view)
            {
                name = et.getText().toString();
                if (name.equals(""))
                {

                    Toast.makeText(ActivitySaveTags.this, "Please give a name to your choice.", Toast.LENGTH_LONG).show();

                }
                else if (getIntent().getStringExtra("industry").equals("Get"))
                {
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

                        relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
                        linearLayoutprogress.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        JSONObject obj = new JSONObject();
                        try
                        {
                            obj.put("UID",shraredUID);
                            obj.put("Title",et.getText().toString());
                            obj.put("CategoryID",categoryid);
                            obj.put("SubCategoryIDs",subcatid);
                            obj.put("VerticalIDs",verticalid);
                            obj.put("BType",getIntent().getStringExtra("industry"));
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        ModAsyncTask task = new ModAsyncTask();
                        task.delegate = this;
                        task.execute(obj.toString(), "member-verticals1.php");

                    }
                    else
                    {
                        connectionDetector.showAlertDialog(this, "No Internet Connection", "You don't have internet connection.", false);
                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
                                linearLayoutprogress.setVisibility(View.VISIBLE);
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

                    }

                    // Putting all data into sharedPreferences for instant update and skip API
                    getUserdetail(db.getValue("UID"));

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
            cd.showAlertDialog(ActivitySaveTags.this, "No Internet Connection", "You don't have internet connection.", false);
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
        int value = Integer.parseInt(db.getValue("profilecomplete"));

       // progressBar.setVisibility(View.INVISIBLE);

        if(Integer.parseInt(db.getValue("profilecomplete"))==4)
            {
                Intent i = new Intent(getApplicationContext(), ActivityDashBoard.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        else
            {
                db.updateValue("profilecomplete","3");
                Intent i = new Intent(getApplicationContext(), ActivityMyProfile.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
    }

    // This Override method is responsible for click any place of screen keyBoard automatically hide

    @Override
    public boolean onTouchEvent(MotionEvent event)
            {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
}
