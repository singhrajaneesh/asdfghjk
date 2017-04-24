package com.ekant.justbiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class ActivityMyProfile extends AppCompatActivity
{
    final String imageurl = "http://www.justbusinesses.net/bridge/uploads/company-logos/";
    final String banner = "http://www.justbusinesses.net/bridge/uploads/company-banners/";
    public TextView companyname, companycategory,companysubcategory, aboutdesc;
    public ImageView companylogo, companybanner,editimage1,editimage2,editimage3,editimage4;
    public ModImageLoader imageLoader;
    LayoutInflater layoutInflater;
    Context context;
    String message, name, uid;
    int profilecomplete;
    ArrayList<HashMap<String, String>> getbussinesses,givebussinesses;
    private ModObservableScrollView scrollView;
    JSONArray Category = null;
    ModTagLayout tagLayout;
    Bundle bundle;
    MenuItem item;
    String[] values;
    Button getButton,giveButton;
    ListView getList,givelist;
    AdapterSaveTagsMyprofile adapter;
    AdapterFriendGetTags adapter1;
    JSONArray getbusiness,givebusiness;
    RelativeLayout infolayout,industrylayout,getlayout,givelayout,imagelayout;
    Boolean isInternetPresent = false;
    ModConnectionDetector connectionDetector;
    ProgressBar progressBar;
    String MyPREFERENCES="MyPREFERENCES";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    JSONObject jsonObject;
    Intent intent;
    String obj,completelevel;
    TextView get,give,about;
    Integer getbusinesssize,givebusinesssize;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        // set Action Bar Icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        values = new String[]{"ear", "eye",  "head", "ear", "eye", "head","ear", "eye",  "head","ear", "eye",  "head", "ear", "eye", "head","ear", "eye",  "head"};
        Typeface face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");
        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String strJson = sharedpreferences.getString("UserInfo", null);

        connectionDetector = new ModConnectionDetector(getApplicationContext());
        givebussinesses = new ArrayList<HashMap<String, String>>();
        getbussinesses = new ArrayList<HashMap<String, String>>();
        editimage1 = (ImageView) findViewById(R.id.edit3);
        editimage2 = (ImageView) findViewById(R.id.edit1);
        editimage3 = (ImageView) findViewById(R.id.edit);
        editimage4 = (ImageView) findViewById(R.id.addvertical);
        getButton = (Button) findViewById(R.id.getadd);
        giveButton = (Button) findViewById(R.id.giveadd);

        getButton.setTypeface(face);
        giveButton.setTypeface(face);
        getList = (ListView) findViewById(R.id.dynamicget12);
        givelist = (ListView) findViewById(R.id.myprofiledynamicgive);
       // setListViewHeightBasedOnItems(getList);
        //new  ModHelperMyProfile().getListViewSize(getList, 1);

        infolayout = (RelativeLayout) findViewById(R.id.infolayout);
        industrylayout = (RelativeLayout) findViewById(R.id.industrylayout);
        getlayout = (RelativeLayout) findViewById(R.id.getlayout);
        givelayout = (RelativeLayout) findViewById(R.id.givelayout);
        imagelayout = (RelativeLayout) findViewById(R.id.imagelayout);
        progressBar=(ProgressBar)findViewById(R.id.progressBarLogin);
        // This is progress bar spinner color.animation
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#4db7ab"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        layoutInflater = getLayoutInflater();
        tagLayout = (ModTagLayout) findViewById(R.id.tagLayout);

        get=(TextView) findViewById(R.id.givebussiness);
        give=(TextView) findViewById(R.id.wantbussiness);
        about=(TextView) findViewById(R.id.about);


        get.setTypeface(face);
        give.setTypeface(face);
        about.setTypeface(face);


        scrollView = (ModObservableScrollView) findViewById(R.id.scrollView);
        scrollView.setScrollViewListener(new ModObservableScrollView.ScrollViewListener()
                                         {
                                             @Override
                                             public void onScrollChanged(ModObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                                                 View view = scrollView.findViewById(R.id.companylogo);
                                                 if (view != null) {
                                                     view.setTranslationY(scrollView.getScrollY() / 2);
                                                 }
                                             }
                                         }
        );

        companyname = (TextView) findViewById(R.id.companynameprofile);
        aboutdesc = (TextView) findViewById(R.id.aboutdesc);
        aboutdesc.setGravity(Gravity.FILL);
        companycategory = (TextView) findViewById(R.id.companycategory);
        companysubcategory = (TextView) findViewById(R.id.companySubcategory);

        companyname.setTypeface(face);
        aboutdesc.setTypeface(face);
        companycategory.setTypeface(face);
        companysubcategory.setTypeface(face);


        companybanner = (ImageView) findViewById(R.id.companylogo);
        companylogo = (ImageView) findViewById(R.id.profilelogo);
        imageLoader = new ModImageLoader(this);

        try
        {
            jsonObject = new JSONObject(strJson);
            completelevel = jsonObject.getString("CompleteLevel");
            companyname.setText(jsonObject.getString("CompanyName"));
            companycategory.setText(jsonObject.getString("MemberIndustry"));
            companysubcategory.setText(jsonObject.getString("SubCategories"));
            String imageurl1 = imageurl + jsonObject.getString("CompanyLogo");

            new ModIconLoader(this).DisplayImage(imageurl1, companylogo);
            String bannerurl = banner + jsonObject.getString("CompanyBanner");

            imageLoader.profile(bannerurl, companybanner);
            companybanner.setTag(jsonObject.getString("CompanyBanner"));
            aboutdesc.setText(jsonObject.getString("CompanyDescription"));

            List<String> Verticals = Arrays.asList(jsonObject.getString("MemberVerticals").split("\\s*,\\s*"));
            for (int i = 0; i <= Verticals.size() - 1; i++)
            {
                View tagView = layoutInflater.inflate(R.layout.tag_dull_layout, null, false);
                final TextView tagTextView = (TextView) tagView.findViewById(R.id.tagTextView123);
                tagTextView.setText(Verticals.get(i));
                tagLayout.addView(tagView);
            }
            getbusiness = jsonObject.getJSONArray("GetBusinesses");
            for (int i = 0; i < getbusiness.length(); i++)
            {
                HashMap<String, String> n = new HashMap<String, String>();
                JSONObject c = getbusiness.getJSONObject(i);
                n.put("GetTitle", c.getString("GetTitle"));
                n.put("Category", c.getString("Category"));
                n.put("SubCategories", c.getString("SubCategories"));
                n.put("Verticals", c.getString("Verticals"));
                getbussinesses.add(n);
            }


            getbusinesssize = (Integer) getbussinesses.size();

            adapter = new AdapterSaveTagsMyprofile(this, getbussinesses, getApplicationContext());
            getList.setAdapter(adapter);
            ModHelperMyProfile.getListViewSize(getList, 1);

            givebusiness = jsonObject.getJSONArray("GiveBusinesses");
            System.out.println("givebusiness.length()" + givebusiness.length());
            for (int i = 0; i < givebusiness.length(); i++)
            {
                HashMap<String, String> n = new HashMap<String, String>();
                JSONObject c = givebusiness.getJSONObject(i);
                n.put("Category", c.getString("Category"));
                n.put("SubCategories", c.getString("SubCategories"));
                n.put("Verticals", c.getString("Verticals"));
                givebussinesses.add(n);
            }

            givebusinesssize = (Integer) givebussinesses.size();


            adapter1 = new AdapterFriendGetTags(this, givebussinesses, getApplicationContext());
            givelist.setAdapter(adapter1);
            ViewGroup.LayoutParams params = givelist.getLayoutParams();
            int totalHeight = 0;//it is the ListView Height
            for (int i = 0, len = adapter1.getCount(); i < len; i++) {
//                View listItem = listAdapter.getView(i, null, listView);
//                listItem.measure(0, 0);
//                int list_child_item_height = listItem.getMeasuredHeight()+listView.getDividerHeight();//item height
                totalHeight += 225; //
            }
            params.height = totalHeight;
            givelist.setLayoutParams(params);

        }


        catch (Exception e)
        {


            e.printStackTrace();

        }

        profilecomplete = Integer.parseInt(completelevel);
        switch (profilecomplete)
        {
            case 1:
                infolayout.setVisibility(View.VISIBLE);
                industrylayout.setVisibility(View.INVISIBLE);
                getlayout.setVisibility(View.INVISIBLE);
                givelayout.setVisibility(View.INVISIBLE);
                imagelayout.setVisibility(View.INVISIBLE);
                break;
            case 2:
                infolayout.setVisibility(View.INVISIBLE);
                industrylayout.setVisibility(View.VISIBLE);
                getlayout.setVisibility(View.INVISIBLE);
                givelayout.setVisibility(View.INVISIBLE);
                imagelayout.setVisibility(View.INVISIBLE);
                break;
            case 3:
                infolayout.setVisibility(View.INVISIBLE);
                industrylayout.setVisibility(View.INVISIBLE);
                getlayout.setVisibility(View.VISIBLE);
                givelayout.setVisibility(View.INVISIBLE);
                imagelayout.setVisibility(View.INVISIBLE);
                break;
            case 4:
                infolayout.setVisibility(View.INVISIBLE);
                industrylayout.setVisibility(View.INVISIBLE);
                getlayout.setVisibility(View.INVISIBLE);
                givelayout.setVisibility(View.VISIBLE);
                imagelayout.setVisibility(View.INVISIBLE);
                break;
            case 5:
                infolayout.setVisibility(View.INVISIBLE);
                industrylayout.setVisibility(View.INVISIBLE);
                getlayout.setVisibility(View.INVISIBLE);
                givelayout.setVisibility(View.INVISIBLE);
                imagelayout.setVisibility(View.INVISIBLE);
                break;
            default:
                infolayout.setVisibility(View.INVISIBLE);
                industrylayout.setVisibility(View.INVISIBLE);
                getlayout.setVisibility(View.INVISIBLE);
                givelayout.setVisibility(View.INVISIBLE);
                imagelayout.setVisibility(View.INVISIBLE);
                break;
        }
   }




    public void onBackPressed()
    {
        finish();
        if(Integer.parseInt(completelevel)==5)
        {

            Intent intent = new Intent(ActivityMyProfile.this, ActivityDashBoard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            ActivityMyProfile.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (android.R.id.home == item.getItemId())
        {
            if(Integer.parseInt(completelevel)==5)
            {
                finish();
                Intent intent = new Intent(ActivityMyProfile.this, ActivityDashBoard.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                ActivityMyProfile.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
                return true;
            }
            else
            {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);

            }
        }
        return super.onOptionsItemSelected(item);
    }

    // Function to call edit profile details
    public void CallEditdetails(View view)
    {
        finish();
        Intent editprofile = new Intent(ActivityMyProfile.this, ActivityEditProfile.class);
        startActivity(editprofile);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

    }

    public void infocompleted(View view)
    {
        Intent editprofile = new Intent(ActivityMyProfile.this, ActivityEditProfile.class);

        startActivity(editprofile);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    // Function to call edit company $ profile logo
    public void CallEditCompany(View view)
    {
        finish();
        Intent editprofile = new Intent(ActivityMyProfile.this, ActivityUpLoadImage.class);
        startActivity(editprofile);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    // Function to call edit company $  profile logo
    public void CallEditProfile(View view)
    {
        finish();
        Intent editprofile = new Intent(ActivityMyProfile.this, ActivityUpLoadImage.class);
        startActivity(editprofile);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }



    // Function to complete your own industry
    public void industrycomplete(View view)
    {


        Intent industrycomplete = new Intent(ActivityMyProfile.this, ActivityChooseCategory.class);
        industrycomplete.putExtra("industry", "Own");
        startActivity(industrycomplete);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

    }
    // Function to complete your own industry
    public void getcompleted(View view)
    {

        if (getbusinesssize>=3)
        {

            Toast.makeText(ActivityMyProfile.this, "Can't select more than 3 get Businesses", Toast.LENGTH_SHORT).show();

        }

        else
        {
            Intent industrycomplete = new Intent(ActivityMyProfile.this, ActivityChooseCategory.class);
            industrycomplete.putExtra("industry", "Get");
            startActivity(industrycomplete);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }

    }

    public void givecomplete(View view)
    {


        if (givebusinesssize>=2)
        {

            Toast.makeText(ActivityMyProfile.this, "Can't select more than 2 giveBusinesses", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Intent industrycomplete = new Intent(ActivityMyProfile.this, ActivityChooseCategory.class);
            industrycomplete.putExtra("industry", "Give");
            startActivity(industrycomplete);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView)
    {

        AdapterSaveTagsMyprofile listAdapter = (AdapterSaveTagsMyprofile) listView.getAdapter();
        if (listAdapter != null)
        {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems;itemPos++)
            {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight()+40;
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;

        }

    }
}











