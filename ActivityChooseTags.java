package com.ekant.justbiz;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ActivityChooseTags extends AppCompatActivity implements  ModAsyncResponce
{


    final String imageurl = "http://www.justbusinesses.net/bridge/uploads/category-images/";
    TextView click,tv1,tv2;
    JSONObject JSONids,senddata;
    JSONArray categories = null;
    ArrayList<HashMap<String, String>> dataList;
    LayoutInflater layoutInflater;
    ModTagLayout tagLayout;
    ArrayList<String> selectedverticals,selectedverticalsID;
    ProgressBar progressbar;
    Boolean isInternetPresent = false;
    ModConnectionDetector connectionDetector;
    String MyPREFERENCES ="MyPREFERENCES",shraredUID,tag;
    SharedPreferences sharedpreferences;
    ImageView imageView;
    ModImageLoader imageLoader;
    LinearLayout linearLayoutprogress;
    RelativeLayout relativeLayoutlayoutprogress;
    Typeface face;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_tags);
        tagLayout = (ModTagLayout) findViewById(R.id.tagLayout);
        getSupportActionBar().setIcon(R.drawable.logo);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);

        face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");
        connectionDetector = new ModConnectionDetector(getApplicationContext());
        click = (TextView) findViewById(R.id.click);
        layoutInflater = getLayoutInflater();
        dataList = new ArrayList<HashMap<String, String>>();
        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        shraredUID = sharedpreferences.getString("UID", null);

//
//        String CategoryName1=getIntent().getStringExtra("CategoryName");
//        String Image1=getIntent().getStringExtra("Image");
//        String CategoryID1=getIntent().getStringExtra("CategoryID");
//        String Vertical1=getIntent().getStringExtra("Vertical");
//        String GetBusiness1=getIntent().getStringExtra("GetBusiness");
//        String industry1=getIntent().getStringExtra("industry");
//
//
//        System.out.println("Choose tags: CategoryName1 "+CategoryName1+"\n");
//        System.out.println("Choose tags:  Image1  "+Image1+"\n");
//        System.out.println("Choose tags: CategoryID1 "+CategoryID1+"\n");
//        System.out.println("Choose tags:  Vertical1  "+Vertical1+"\n");
//        System.out.println("Choose tags:  Vertical1  "+GetBusiness1+"\n");
//        System.out.println("Choose tags:  industry1  "+industry1+"\n");

        tv1 = (TextView) findViewById(R.id.selectedcategory);
        tv1.setText(Html.fromHtml(getIntent().getStringExtra("CategoryName")));
        tv2 = (TextView) findViewById(R.id.title1);

        tv1.setTypeface(face);
        tv2.setTypeface(face);
        click.setTypeface(face);

        imageView = (ImageView) findViewById(R.id.categoryimage);
        imageLoader = new ModImageLoader(this);
      //  imageLoader.DisplayImage(imageurl + getIntent().getStringExtra("Image"), imageView);
        tv2.setText(Html.fromHtml("<b> Sub-categories </b>" + " : " + getIntent().getStringArrayListExtra("selectedSubcatname").toString().substring(1, getIntent().getStringArrayListExtra("selectedSubcatname").toString().length() - 1)));
        ArrayList<String> list = getIntent().getStringArrayListExtra("selected");

        System.out.println("Choose tags:  list  "+list+"\n");

        JSONids = new JSONObject();
        senddata = new JSONObject();

        linearLayoutprogress=(LinearLayout) findViewById(R.id.llprogress);
        relativeLayoutlayoutprogress=(RelativeLayout) findViewById(R.id.rlprogressbar);
        progressbar=(ProgressBar)findViewById(R.id.progressBarLogin);
        progressbar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        for (int i = 0; i < list.size(); i++)
        {
            try
            {
                JSONids.put("Count"+String.valueOf(i),list.get(i));

            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        sendDataToWeb();
        relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
        linearLayoutprogress.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.VISIBLE);
        click.setOnClickListener(new View.OnClickListener()
                                 {
                                     @Override
                                     public void onClick(View v)
                                     {
                                         selectedverticals = new ArrayList<String>();
                                         selectedverticalsID = new ArrayList<String>();

                                         for (int i = 0; i <= dataList.size() - 1; i++)
                                         {
                                             View text = tagLayout.getChildAt(i);
                                             TextView tv = (TextView) ((LinearLayout) text).getChildAt(0);

                                             if (tv.isSelected())
                                             {
                                                 HashMap<String, String> n = new HashMap<String, String>();
                                                 n = dataList.get(i);
                                                 selectedverticals.add(n.get("Vertical"));
                                                 selectedverticalsID.add(n.get("VerticalID"));
                                             }
                                         }
                                         if (getIntent().getStringExtra("Vertical") != null)
                                         {
                                             try
                                             {
                                                 senddata.put("UID", shraredUID);
                                                 senddata.put("CategoryID", getIntent().getStringExtra("CategoryID"));
                                                 senddata.put("SubcategoryID", getIntent().getStringArrayListExtra("selected"));
                                                 senddata.put("VerticalsID", getIntent().getStringArrayListExtra("selected"));
                                             }
                                             catch (JSONException e)
                                             {
                                                 e.printStackTrace();
                                             }


                                         }
                                         else if (selectedverticals.isEmpty())
                                         {
                                             Toast.makeText(ActivityChooseTags.this, "Please choose at least one Business Tag.", Toast.LENGTH_LONG).show();
                                         }
                                         else
                                         {

                                                 Intent i = new Intent(ActivityChooseTags.this, ActivitySaveAutomatic.class);
                                                 i.putExtra("CategoryName", getIntent().getStringExtra("CategoryName"));
                                                 i.putExtra("CategoryID", getIntent().getStringExtra("CategoryID"));
                                                 i.putExtra("Image", getIntent().getStringExtra("Image"));
                                                 i.putStringArrayListExtra("selectedSubcatname", getIntent().getStringArrayListExtra("selectedSubcatname"));
                                                 i.putStringArrayListExtra("selected", getIntent().getStringArrayListExtra("selected"));
                                                 i.putStringArrayListExtra("selectedverticals", selectedverticals);
                                                 i.putStringArrayListExtra("selectedverticalsID", selectedverticalsID);
                                                 i.putExtra("industry", getIntent().getStringExtra("industry"));
                                                 startActivity(i);
                                                 overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                         }

                                     }
        }
        );


        //onCreate Finished here...
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (android.R.id.home==item.getItemId())
        {
            finish();
            ActivityChooseTags.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        finish();
        ActivityChooseTags.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }
    public void sendDataToWeb()
    {
        isInternetPresent = connectionDetector.isConnectingToInternet();
        if (isInternetPresent)
        {
            JSONObject obj = new JSONObject();
            try
            {
                obj.put("Verticals",JSONids);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            ModAsyncTask task = new ModAsyncTask();
            task.delegate = this;
            task.execute(obj.toString(), "verticals1.php");
            System.out.println("obj.toString()" + obj.toString().toString());
        }
        else
        {
            connectionDetector.showAlertDialog(this, "No Internet Connection", "You don't have internet connection.", false);
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
                    linearLayoutprogress.setVisibility(View.INVISIBLE);
                    progressbar.setVisibility(View.INVISIBLE);
                }

                                      },
                    0);
        }


    }

    public void processFinish(String output)
    {


        relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
        linearLayoutprogress.setVisibility(View.INVISIBLE);
        progressbar.setVisibility(View.INVISIBLE);
        try
        {
            JSONObject jsonObj = new JSONObject(output);
            System.out.println("choose jsonObj   "+jsonObj.toString());
            categories = jsonObj.getJSONArray("Verticals");

            for (int i = 0; i < categories.length(); i++)
            {
                HashMap<String, String> n = new HashMap<String, String>();
                JSONObject c = categories.getJSONObject(i);
                n.put("VerticalID", c.getString("VerticalID"));
                n.put("Vertical", c.getString("Vertical"));
                dataList.add(n);
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }


        for (int i = 0; i <= dataList.size()-1 ; i++)
        {

            HashMap<String, String> subcategory = new  HashMap<String, String>();
            subcategory = dataList.get(i);
            tag = subcategory.get("Vertical");
            View tagView = layoutInflater.inflate(R.layout.tag_layout, null, false);
            final TextView tagTextView = (TextView) tagView.findViewById(R.id.tagTextView);
            tagTextView.setText(tag);
            tagTextView.setTypeface(face);
            tagTextView.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v)
                {
                    if (tagTextView.isSelected())
                    {
                        Drawable d = getResources().getDrawable(R.drawable.tag_background);
                        tagTextView.setBackground(d);
                        tagTextView.setSelected(false);
                        tagTextView.setTextColor(Color.BLACK);

                    }
                    else
                    {
                        Drawable d = getResources().getDrawable(R.drawable.tag_selected_background);
                        tagTextView.setBackground(d);
                        tagTextView.setSelected(true);
                        tagTextView.setTextColor(Color.WHITE);
                    }
                }
            });
            tagLayout.addView(tagView);
        }

    }




}



