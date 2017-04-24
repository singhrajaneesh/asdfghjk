package com.ekant.justbiz;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ActivityThankYou extends AppCompatActivity
{

    Button button;
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> laptopCollection;
    android.widget.ExpandableListView expListView;
    TextView heading,missedopportunity,tvPerson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setIcon(R.drawable.logo);

        Typeface face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");
        heading=(TextView) findViewById(R.id.heading);
        missedopportunity=(TextView) findViewById(R.id.missedopportunity);
        tvPerson=(TextView) findViewById(R.id.tvPerson);

        button=(Button)findViewById(R.id.btnlogin);
        heading.setTypeface(face);
        button.setTypeface(face);
        missedopportunity.setTypeface(face);
        tvPerson.setTypeface(face);

        createGroupList();
        createCollection();

        expListView = (ExpandableListView) findViewById(R.id.laptop_list);
        final AdapterExpandableListAdapter expListAdapter = new AdapterExpandableListAdapter(this, groupList, laptopCollection);
        expListView.setAdapter(expListAdapter);


    }
    private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.add("Professional Services");
        groupList.add("Services Sector");
        groupList.add("eCommerce");



    }

    private void createCollection() {
        // preparing laptops collection(child)
        String[] hotels = { "DCA, Flex, LAN, ORCAD, VLSI, Editor"};
        String[] loan = { "Child Care, Engineering, Pest Control, Moving Company, Data Servies"};
        String[] accessories = { "General Internet, Consumer Services(B2C), Publicist"};


        laptopCollection = new LinkedHashMap<String, List<String>>();

        for (String laptop : groupList) {
            if (laptop.equals("Professional Services"))
            {
                loadChild(hotels);
            }

            else if (laptop.equals("Services Sector"))
                loadChild(loan);
            else if (laptop.equals("eCommerce"))
                loadChild(accessories);

//            else if (laptop.equals("Samsung"))
//                loadChild(samsungModels);
//            else
//                loadChild(lenovoModels);

            laptopCollection.put(laptop, childList);
        }
    }

    private void loadChild(String[] laptopModels)
    {
        childList = new ArrayList<String>();
        for (String model : laptopModels)
            childList.add(model);
    }

    private void setGroupIndicatorToRight() {
		/* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(35), width - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    public void onBackPressed()
    {

        Intent intent = new Intent(getApplicationContext(), ActivityDashBoard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (android.R.id.home==item.getItemId())
        {
            Intent intent = new Intent(getApplicationContext(), ActivityDashBoard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showactivity(View view)
    {

        Intent intent = new Intent(getApplicationContext(), ActivityDashBoard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
