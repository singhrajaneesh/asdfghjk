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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityNewsandEvent extends AppCompatActivity  implements ModAsyncResponce{

    ListView listView;
    AdapterNewsandEvent adapter;
    ArrayList<HashMap<String, String>> dataList;
    Boolean isInternetPresent = false;
    ModConnectionDetector connectionDetector;
    Moddb db;
    ProgressBar progressBar;
    String UID;
    JSONObject jsonObj,obj;
    String a;
    TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_newsand_event);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setIcon(R.drawable.logo);

        heading=(TextView) findViewById(R.id.textviewjb);
        Typeface face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");
        heading.setTypeface(face);

        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#4db7ab"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        connectionDetector = new ModConnectionDetector(getApplicationContext());
        listView = (ListView) findViewById(R.id.listview);
        dataList = new ArrayList<HashMap<String,String>>();
        sendDataToWeb();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                        {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                            {

                                                HashMap<String, String> hashMap = dataList.get(position);
                                                String newsID=hashMap.get("NewsID");
                                                Intent intent=new Intent(ActivityNewsandEvent.this,ActivityNewsReader.class);
                                                intent.putExtra("position", newsID);
                                                intent.putExtra("news","2");
                                                startActivity(intent);
                                                overridePendingTransition(R.anim.right_in, R.anim.left_out);

                                            }
        }
        );
    }
    private void sendDataToWeb()
    {
        progressBar.setVisibility(View.VISIBLE);
        isInternetPresent = connectionDetector.isConnectingToInternet();
        if (isInternetPresent)
        {
            ModAsyncTask task = new ModAsyncTask();
            task.delegate = this;
            task.execute("Blank", "newsevents.php");


        }
        else
        {
            connectionDetector.showAlertDialog(this, "No Internet Connection", "You don't have internet connection.", false);
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }, 0);
        }
    }
    public void processFinish(String output)
    {


        progressBar.setVisibility(View.INVISIBLE);
           try {
               jsonObj = new JSONObject(output);
               JSONArray value2=jsonObj.getJSONArray("Getnewsandevents");

               System.out.println("value2    "+value2.toString());

               for (int i=0;i<value2.length();i++)
               {
                   HashMap<String, String> n = new HashMap<String, String>();
                   JSONObject c = value2.getJSONObject(i);
                   n.put("Heading", c.getString("Heading"));
                   n.put("SubHeading", c.getString("SubHeading"));
                   n.put("Image", c.getString("Image"));
                   n.put("NewsID", c.getString("NewsID"));
                   dataList.add(n);

               }

               System.out.println("dataListdataList    "+dataList.toString());
               adapter=new AdapterNewsandEvent(this,dataList,getApplicationContext());
               listView.setAdapter(adapter);

           }
           catch (JSONException e1)
           {
               e1.printStackTrace();
           }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (android.R.id.home==item.getItemId())
        {
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
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

}
