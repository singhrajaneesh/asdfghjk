package com.ekant.justbiz;

import android.annotation.TargetApi;
import android.content.Intent;
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

public class ActivityChooseSubCategory extends AppCompatActivity implements  ModAsyncResponce
{
    final String imageurl = "http://www.justbusinesses.net/bridge/uploads/category-images/";
    TextView proceedClick,title,tv;
    String tag,CategoryID,CategoryName,Image;
    JSONArray categories = null;
    ArrayList<HashMap<String, String>> dataList;
    ArrayList<String>SubCategoryID,SubCategoryName;
    LayoutInflater layoutInflater;
    ModTagLayout tagLayout;
    ProgressBar progressBar;
    Boolean isInternetPresent = false;
    ModConnectionDetector connectionDetector;
    String getIndustry;
    LinearLayout linearLayoutprogress;
    RelativeLayout relativeLayoutlayoutprogress;
    Typeface face;
    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_subcategory);
        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        tagLayout = (ModTagLayout) findViewById(R.id.tagLayout);
        progressBar=(ProgressBar)findViewById(R.id.progressBarLogin);
        linearLayoutprogress=(LinearLayout) findViewById(R.id.llprogress);
        relativeLayoutlayoutprogress=(RelativeLayout) findViewById(R.id.rlprogressbar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), android.graphics.PorterDuff.Mode.SRC_ATOP);

        face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");

        connectionDetector = new ModConnectionDetector(getApplicationContext());
        CategoryID = getIntent().getStringExtra("CategoryID");
        CategoryName = getIntent().getStringExtra("Category");
        Image = getIntent().getStringExtra("Image");
        Intent intent = getIntent();
        getIndustry=intent.getStringExtra("industry");

        if (getIntent().equals("Give"))
            {
                Intent i = new Intent(ActivityChooseSubCategory.this, ActivitySaveAutomatic.class);
                startActivity(i);
            }

        String s = CategoryName;

        ImageView imageView = (ImageView) findViewById(R.id.categoryimage);

       // ModImageLoader imageLoader = new ModImageLoader(this);
       // imageLoader.DisplayImage(imageurl + getIntent().getStringExtra("Image"), imageView);


        tv = (TextView) findViewById(R.id.selectedcategory);
        tv.setText(Html.fromHtml(s));

        title = (TextView) findViewById(R.id.title);


        dataList = new ArrayList<HashMap<String, String>>();

        proceedClick = (TextView) findViewById(R.id.click);

        tv.setTypeface(face);
        title.setTypeface(face);
        proceedClick.setTypeface(face);

        layoutInflater = getLayoutInflater();
        sendDataToWeb();
        relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
        linearLayoutprogress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        proceedClick.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SubCategoryID = new ArrayList<String>();
                SubCategoryName = new ArrayList<String>();
                for (int i = 0; i < dataList.size(); i++)
                {
                    View text = tagLayout.getChildAt(i);
                    TextView tv = (TextView) ((LinearLayout) text).getChildAt(0);
                    if (tv.isSelected())
                    {
                        HashMap<String, String> n = new HashMap<String, String>();
                        n = dataList.get(i);
                        SubCategoryID.add(n.get("SubCategoryID"));
                        SubCategoryName.add(n.get("SubCategory"));
                    }
                }

                if (SubCategoryID.isEmpty())
                {
                    Toast.makeText(ActivityChooseSubCategory.this, "Please choose at least one Sub-Categories.", Toast.LENGTH_LONG).show();
                }
                else
                {

                    if (getIndustry.equals("Get"))
                    {
                        Intent i = new Intent(ActivityChooseSubCategory.this, ActivitySaveTags.class);
                        i.putExtra("CategoryName", CategoryName);
                        i.putExtra("Image", Image);
                        i.putExtra("CategoryID", CategoryID);
                        i.putExtra("Vertical", getIntent().getStringExtra("Vertical"));
                        i.putStringArrayListExtra("selected", SubCategoryID);
                        i.putStringArrayListExtra("selectedSubcatname", SubCategoryName);
                        i.putExtra("GetBusiness", getIntent().getStringExtra("GetBusiness"));
                        i.putExtra("industry", getIntent().getStringExtra("industry"));
                        i.putExtra("Search","normalflow");
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    }

                    else if (getIndustry.equals("Own"))
                    {

                        Intent i = new Intent(ActivityChooseSubCategory.this, ActivityChooseTags.class);
                        i.putExtra("CategoryName", CategoryName);
                        i.putExtra("CategoryID", CategoryID);
                        i.putExtra("Image", Image);
                        i.putExtra("Vertical", getIntent().getStringExtra("Vertical"));
                        i.putStringArrayListExtra("selected", SubCategoryID);
                        i.putStringArrayListExtra("selectedSubcatname", SubCategoryName);
                        i.putExtra("GetBusiness", getIntent().getStringExtra("GetBusiness"));
                        i.putExtra("industry", getIntent().getStringExtra("industry"));
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);

                    }




                    else   if (getIndustry.equals("Give"))
                    {



                        Intent i = new Intent(ActivityChooseSubCategory.this, ActivitySaveAutomatic.class);
                        i.putExtra("CategoryID", getIntent().getStringExtra("CategoryID"));
                        i.putExtra("CategoryName", getIntent().getStringExtra("Category"));
                        i.putExtra("Vertical", getIntent().getStringExtra("Vertical"));
                        i.putExtra("Image", getIntent().getStringExtra("Image"));
                        i.putExtra("GetBusiness", getIntent().getStringExtra("GetBusiness"));
                        i.putStringArrayListExtra("selected", SubCategoryID);
                        i.putStringArrayListExtra("selectedSubcatname", SubCategoryName);
                        i.putExtra("industry", getIntent().getStringExtra("industry"));
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);

                    }


                }}});

  //OnCreate finish here...
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (android.R.id.home==item.getItemId())
        {
            finish();
            ActivityChooseSubCategory.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed()
    {
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    public void sendDataToWeb()
    {
        isInternetPresent = connectionDetector.isConnectingToInternet();

        if (isInternetPresent)

        {
            JSONObject obj = new JSONObject();
            try
            {
                obj.put("CategoryID", CategoryID);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            ModAsyncTask task = new ModAsyncTask();
            task.delegate = this;
            task.execute(obj.toString(), "subcategories.php");

        }

        else
        {
            connectionDetector.showAlertDialog(this, "No Internet Connection", "You don't have internet connection.", false);
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run() {
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
        if(output!=null)
        {
            try
            {
                JSONObject jsonObj = new JSONObject(output);
                categories = jsonObj.getJSONArray("SubCategories");
                for (int i = 0; i < categories.length(); i++)
                {
                    HashMap<String, String> n = new HashMap<String, String>();
                    JSONObject c = categories.getJSONObject(i);
                    n.put("SubCategoryID", c.getString("SubCategoryID"));
                    n.put("SubCategory", c.getString("SubCategory"));
                    dataList.add(n);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i <= dataList.size() - 1; i++)
            {
                HashMap<String, String> subcategory = new HashMap<String, String>();
                subcategory = dataList.get(i);
                tag = subcategory.get("SubCategory");
                View tagView = layoutInflater.inflate(R.layout.tag_layout, null, true);
                final TextView tagTextView = (TextView) tagView.findViewById(R.id.tagTextView);
                tagTextView.setText(tag);
                tagTextView.setTypeface(face);
                tagTextView.setClickable(false);
                tagTextView.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        if (tagTextView.isSelected())
                        {
                            Drawable d = getResources().getDrawable(R.drawable.text_background);
                            tagTextView.setBackground(d);
                            tagTextView.setSelected(false);
                            tagTextView.setTextColor(Color.BLACK);
                        }
                        else
                        {
                            Drawable d = getResources().getDrawable(R.drawable.text_selected_background);
                            tagTextView.setBackground(d);
                            tagTextView.setSelected(true);
                            tagTextView.setTextColor(Color.WHITE);
                        }
                    }
                });
                tagLayout.addView(tagView);
            }
        }
        else
        {
            connectionDetector.showAlertDialog(this, "There is problem.", "You don't have internet connection.", false);
        }
    }
}


