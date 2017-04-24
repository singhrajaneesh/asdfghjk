package com.ekant.justbiz;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ActivityFriendProfile extends AppCompatActivity implements ModAsyncResponce {

    final String imageurl = "http://www.justbusinesses.net/bridge/uploads/company-logos/";
    final String banner = "http://www.justbusinesses.net/bridge/uploads/company-banners/";
    public TextView companyname, companycategory, companysubcategory, aboutdesc;
    public ImageView companylogo, companybanner;
    public ModImageLoader imageLoader;
    LayoutInflater layoutInflater;
    Context context;
    String UID, profilecomplete, myindustrey, message, name, uid;
    ArrayList<HashMap<String, String>> getbussinesses, givebussinesses;
    private ModObservableScrollView scrollView;
    JSONArray Category = null;
    Moddb db;
    ModTagLayout tagLayout;
    Bundle bundle;
    MenuItem item;
    private int noOfBtns;
    private ImageView[] Images;
    String[] values;
    Button getButton, giveButton;
    ListView friendgetList, friendgiveList;
    ProgressBar progressBar;
    AdapterFriendGetTags adapter;
    AdapterFriendGiveTags adapter1;
    RelativeLayout  guidelayout, guidelayout1;
    JSONArray getbusiness, givebusiness;
    Boolean isInternetPresent = false;
    ModConnectionDetector connectionDetector;
    TextView get,give,about;
    LinearLayout linearLayoutprogress;
    RelativeLayout relativeLayoutlayoutprogress;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        values = new String[]{"ear", "eye", "head", "ear", "eye", "head", "ear", "eye", "head"};
        db = new Moddb(this);
        UID = getIntent().getStringExtra("MemberID");


        Typeface face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");
        connectionDetector = new ModConnectionDetector(getApplicationContext());
        getbussinesses = new ArrayList<HashMap<String, String>>();
        givebussinesses = new ArrayList<HashMap<String, String>>();


        getButton = (Button) findViewById(R.id.getadd);
        giveButton = (Button) findViewById(R.id.giveadd);

        friendgetList = (ListView) findViewById(R.id.friendget);
        friendgiveList = (ListView) findViewById(R.id.friendgive);

        guidelayout = (RelativeLayout) findViewById(R.id.relativelayout);
        guidelayout1 = (RelativeLayout) findViewById(R.id.relativelayout1);
        linearLayoutprogress=(LinearLayout) findViewById(R.id.llprogress);
        relativeLayoutlayoutprogress=(RelativeLayout) findViewById(R.id.rlprogressbar);
        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        // This is progress bar spinner color.animation
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), android.graphics.PorterDuff.Mode.SRC_ATOP);

        layoutInflater = getLayoutInflater();
        tagLayout = (ModTagLayout) findViewById(R.id.tagLayout);


        //givebussinesses = db.getVertical();

        scrollView = (ModObservableScrollView) findViewById(R.id.scrollView);
        scrollView.setScrollViewListener(new ModObservableScrollView.ScrollViewListener() {
                                             @Override
                                             public void onScrollChanged(ModObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                                                 View view = scrollView.findViewById(R.id.companylogo);
                                                 if (view != null) {
                                                     view.setTranslationY(scrollView.getScrollY() / 2);
                                                 }
                                             }
                                         }
        );

        companyname = (TextView) findViewById(R.id.companyname);
        aboutdesc = (TextView) findViewById(R.id.aboutdesc);
        aboutdesc.setGravity(Gravity.FILL);
        companycategory = (TextView) findViewById(R.id.companycategory);
        companysubcategory = (TextView) findViewById(R.id.companySubcategory);
        companybanner = (ImageView) findViewById(R.id.companylogo);
        companylogo = (ImageView) findViewById(R.id.profilelogo);
        imageLoader = new ModImageLoader(this);
        companyname.setTypeface(face);
        aboutdesc.setTypeface(face);
        companycategory.setTypeface(face);
        companysubcategory.setTypeface(face);
        get=(TextView) findViewById(R.id.givebussiness);
        give=(TextView) findViewById(R.id.wantbussiness);
        about=(TextView) findViewById(R.id.about);
        get.setTypeface(face);
        give.setTypeface(face);
        about.setTypeface(face);

        //ImageFooter();
        sendDataToWeb();
        profilecomplete = db.getValue("profilecomplete");
        myindustrey = db.getValue("myindustrey");
    }


    public void sendDataToWeb() {

        relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
        linearLayoutprogress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);


        isInternetPresent = connectionDetector.isConnectingToInternet();
        if (isInternetPresent) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("UID", UID);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ModAsyncTask task = new ModAsyncTask();
            task.delegate = this;
            task.execute(obj.toString(), "member-profile.php");


        }
        else
        {
            connectionDetector.showAlertDialog(this, "No Internet Connection", "You don't have internet connection.", false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
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


        try {
            JSONObject jsonObj = new JSONObject(output);


            companyname.setText(jsonObj.getString("CompanyName"));

            companycategory.setText(jsonObj.getString("MemberIndustry"));
            companysubcategory.setText(jsonObj.getString("SubCategories"));
            String imageurl1 = imageurl + jsonObj.getString("CompanyLogo");
            new ModIconLoader(this).DisplayImage(imageurl1, companylogo);
            String bannerurl = banner + jsonObj.getString("CompanyBanner");
            imageLoader.profile(bannerurl, companybanner);
            companybanner.setTag(jsonObj.getString("CompanyBanner"));
            aboutdesc.setText(jsonObj.getString("CompanyDescription"));
            List<String> Verticals = Arrays.asList(jsonObj.getString("MemberVerticals").split("\\s*,\\s*"));
            for (int i = 0; i <= Verticals.size() - 1; i++) {
                View tagView = layoutInflater.inflate(R.layout.tag_dull_layout, null, false);
                final TextView tagTextView = (TextView) tagView.findViewById(R.id.tagTextView123);
                tagTextView.setText(Verticals.get(i));
                tagLayout.addView(tagView);
            }
            getbusiness = jsonObj.getJSONArray("GetBusinesses");

            for (int i = 0; i < getbusiness.length(); i++) {
                HashMap<String, String> n = new HashMap<String, String>();
                JSONObject c = getbusiness.getJSONObject(i);
                n.put("Category", c.getString("Category"));
                n.put("SubCategories", c.getString("SubCategories"));
                n.put("Verticals", c.getString("Verticals"));
                getbussinesses.add(n);
            }

            givebusiness = jsonObj.getJSONArray("GiveBusinesses");
            for (int i = 0; i < givebusiness.length(); i++) {
                HashMap<String, String> n = new HashMap<String, String>();
                JSONObject c = givebusiness.getJSONObject(i);
                n.put("Category", c.getString("Category"));
                n.put("SubCategories", c.getString("SubCategories"));
                n.put("Verticals", c.getString("Verticals"));
                givebussinesses.add(n);
            }

            adapter = new AdapterFriendGetTags(this, getbussinesses, getApplicationContext());
            friendgetList.setAdapter(adapter);
            new ModHelperMyProfile().getListViewSize(friendgetList, 3);
            adapter1 = new AdapterFriendGiveTags(this, givebussinesses, getApplicationContext());
            friendgiveList.setAdapter(adapter1);
            //new ModHelperMyProfile().getListViewSize(friendgiveList, 2);


        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    public void onBackPressed()
    {
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void ImageFooter() {
//        noOfBtns = 8;
//        LinearLayout ll = (LinearLayout) findViewById(R.id.btnLay);
//        Images = new ImageView[noOfBtns];
//        for (int i = 0; i < noOfBtns; i++) {
//            Images[i] = new ImageView(this);
//            Images[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
//            String uri = "@drawable/" + values[i];
//
//            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
//            Drawable res = getResources().getDrawable(imageResource);
//            Images[i].setImageDrawable(res);
//            //Images[i].setText(""+(i+1));
//
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150, 150);
//            lp.setMargins(30, 30, 0, 30);
//            //params.setMargins(left, top, right, bottom);
//            ll.addView(Images[i], lp);
//
//            final int j = i;
//            Images[j].setOnClickListener(new View.OnClickListener() {
//
//                public void onClick(View v) {
//
//                }
//            });
//        }
//
//
//    }


}