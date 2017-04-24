package com.ekant.justbiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ActivityChooseCategory extends AppCompatActivity implements  ModAsyncResponce,SearchView.OnQueryTextListener {

    ListView categoryView,searchListview;
    AdapterCategories categoryAdapter;
    ArrayList<HashMap<String, String>> dataList;
    JSONArray categories = null;
    ProgressBar progressBar;
    Boolean isInternetPresent = false;
    ModConnectionDetector connectionDetector;
    HashMap<String, String> n;
    private SearchView mSearchView;
    int flag=0;
    String getIndustry;
    JSONArray searchJsonArray;
    JSONObject searchjsonObject;
    ArrayList<String> strings = new ArrayList<>();
    String MyPREFERENCES = "MyPREFERENCES",shraredUID,InputText;
    SharedPreferences sharedpreferences;
    JSONObject c = null;
    LinearLayout linearLayoutprogress;
    RelativeLayout relativeLayoutlayoutprogress;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);
        categoryView = (ListView) findViewById(R.id.listView);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setIcon(R.drawable.logo);
        Typeface face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");
        mSearchView = (SearchView) findViewById(R.id.search_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        linearLayoutprogress=(LinearLayout) findViewById(R.id.llprogress);
        relativeLayoutlayoutprogress=(RelativeLayout) findViewById(R.id.rlprogressbar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        shraredUID = sharedpreferences.getString("UID", null);
        Intent intent = getIntent();
        getIndustry=intent.getStringExtra("industry");
        searchListview  = (ListView) findViewById(R.id.listView2);

        title=(TextView) findViewById(R.id.title);
        title.setTypeface(face);


        // Search Category list view item Give , Get Business and own business function perform...

        searchListview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                if (getIndustry.equals("Own"))
                {

                    Toast.makeText(ActivityChooseCategory.this, "Feature Comming Soon", Toast.LENGTH_SHORT).show();

//                    Intent i = new Intent(ActivityChooseCategory.this, ActivitySaveAutomatic.class);
//
//
//                    try {
//                        c = searchJsonArray.getJSONObject(position);
//                        i.putExtra("CategoryID", c.getString("CategoryID"));
//                        i.putExtra("CategoryName", c.getString("Category"));
//                        i.putExtra("selected", c.getString("SubCategoryID"));
//                        i.putExtra("selectedSubcatname", c.getString("SubCategory"));
//                        i.putExtra("selectedverticalsID", c.getString("VerticalID"));
//                        i.putExtra("selectedverticals", c.getString("Vertical"));
//                        i.putExtra("Image", c.getString("CategoryImage"));
//                        i.putExtra("industry","searchflow");
//                    }
//                    catch (JSONException e)
//                    {
//                        e.printStackTrace();
//                    }
//                    startActivity(i);
//
//                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }

               else if (getIndustry.equals("Get"))
                {

                    Toast.makeText(ActivityChooseCategory.this, "Feature Comming Soon", Toast.LENGTH_SHORT).show();

                   // Intent i = new Intent(ActivityChooseCategory.this, ActivitySaveTags.class);

            //        try {
                       // c = searchJsonArray.getJSONObject(position);
                       // i.putExtra("CategoryID", c.getString("CategoryID"));
                        //i.putExtra("CategoryName", c.getString("Category"));
                        //i.putExtra("selected", c.getString("SubCategoryID"));
                        //i.putExtra("selectedSubcatname", c.getString("SubCategory"));
                       // i.putExtra("industry", getIntent().getStringExtra("industry"));
                       // i.putExtra("Image", c.getString("CategoryImage"));
                       // i.putExtra("Search","searchflow");



//                        System.out.println("Category   CategoryID" + c.getString("CategoryID"));
//                        System.out.println("Category  Category" + c.getString("Category"));
//                        System.out.println("Category  Vertical" + getIntent().getStringExtra("Vertical"));
//                        System.out.println("Category  Image" + c.getString("Image"));
//                        System.out.println("Category  GetBusiness" + getIntent().getStringExtra("GetBusiness"));
//                        System.out.println("Category  industry" + getIntent().getStringExtra("industry"));


                 //   }
                 //   catch (JSONException e)
                  ///  {
                   //     e.printStackTrace();
                   // }

                  //  startActivity(i);

                  //  overridePendingTransition(R.anim.right_in, R.anim.left_out);







                }
                else  if (getIndustry.equals("Give"))
                {

                    Toast.makeText(ActivityChooseCategory.this, "Feature Comming Soon", Toast.LENGTH_SHORT).show();


//                    Intent i = new Intent(ActivityChooseCategory.this, ActivitySaveAutomatic.class);
//                    try {
//                        c = searchJsonArray.getJSONObject(position);
//                        i.putExtra("CategoryID", c.getString("CategoryID"));
//                        i.putExtra("CategoryName", c.getString("Category"));
//                        i.putExtra("Image", c.getString("CategoryImage"));
//                        i.putExtra("industry", "givesearchflow");
//
//                    }
//                    catch (JSONException e)
//                    {
//                        e.printStackTrace();
//                    }
//                    startActivity(i);
//
//                    overridePendingTransition(R.anim.right_in, R.anim.left_out);

                }


            }});

        connectionDetector = new ModConnectionDetector(getApplicationContext());
        sendDataToWeb();
        relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
        linearLayoutprogress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        dataList = new ArrayList<HashMap<String, String>>();
        categoryView.setAdapter(categoryAdapter);
        // ListView of Normal flow without search view click event perform different task

        categoryView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (getIndustry.equals("Give"))
                {
                    Intent i = new Intent(ActivityChooseCategory.this, ActivityChooseSubCategory.class);

                    try
                    {



                        c = categories.getJSONObject(position);
                      ;
                        i.putExtra("CategoryID", c.getString("CategoryID"));
                        i.putExtra("Category", c.getString("Category"));
                      //  i.putExtra("CompanyName", c.getString("CompanyName"));
                        i.putExtra("Vertical", getIntent().getStringExtra("Vertical"));
                        i.putExtra("Image", c.getString("Image"));
                        i.putExtra("GetBusiness", getIntent().getStringExtra("GetBusiness"));
                        i.putExtra("industry", getIntent().getStringExtra("industry"));

                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    startActivity(i);

                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }

                else if (getIndustry.equals("Get"))
                {
                    Intent i = new Intent(ActivityChooseCategory.this, ActivityChooseSubCategory.class);
                    try {
                        c = categories.getJSONObject(position);
                        i.putExtra("CategoryID", c.getString("CategoryID"));
                        i.putExtra("Category", c.getString("Category"));
                        i.putExtra("Vertical", getIntent().getStringExtra("Vertical"));
                        i.putExtra("Image", c.getString("Image"));
                        i.putExtra("GetBusiness", getIntent().getStringExtra("GetBusiness"));
                        i.putExtra("industry", getIntent().getStringExtra("industry"));
                        i.putExtra("Search", "normalflow");


                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    startActivity(i);

                    overridePendingTransition(R.anim.right_in, R.anim.left_out);

                }

             else    if (getIndustry.equals("Own"))
                {


                    Intent i = new Intent(ActivityChooseCategory.this, ActivityChooseSubCategory.class);
                    try {
                        c = categories.getJSONObject(position);

                        i.putExtra("CategoryID", c.getString("CategoryID"));
                        i.putExtra("Category", c.getString("Category"));
                        i.putExtra("Vertical", getIntent().getStringExtra("Vertical"));
                        i.putExtra("Image", c.getString("Image"));
                        i.putExtra("GetBusiness", getIntent().getStringExtra("GetBusiness"));
                        i.putExtra("industry", getIntent().getStringExtra("industry"));

                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    startActivity(i);

                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }




            }}
        );
        // oncreate finish here...

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (android.R.id.home == item.getItemId())
        {

            if (flag==1)
            {
                searchListview.setVisibility(View.INVISIBLE);
                categoryView.setVisibility(View.VISIBLE);
                flag=0;
            }
            else
            {
                finish();
                ActivityChooseCategory.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed()
    {
        if (flag==1)
        {
            Intent intent=new Intent(ActivityChooseCategory.this,ActivityChooseCategory.class);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
            flag=0;
        }
        else
        {
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }
    }
    private void sendDataToWeb()
    {
        isInternetPresent = connectionDetector.isConnectingToInternet();
        if (isInternetPresent)
        {
            ModAsyncTask task = new ModAsyncTask();
            task.delegate = this;
            task.execute("abc", "categories.php");
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
                    progressBar.setVisibility(View.INVISIBLE);
                }
            },

            0);

        }
    }

    public void processFinish(String output)
    {

        relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
        linearLayoutprogress.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        if (output != null)
        {

            try
            {
                JSONObject jsonObj = new JSONObject(output);
                categories = jsonObj.getJSONArray("Categories");

                System.out.println("categories: "+categories.toString());

                for (int i = 0; i < categories.length(); i++)
                {
                    n = new HashMap<String, String>();
                    JSONObject c = categories.getJSONObject(i);
                    n.put("CategoryID", c.getString("CategoryID"));
                    n.put("Category", c.getString("Category"));
                    n.put("Image", c.getString("Image"));
                    dataList.add(n);

                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }


            categoryAdapter = new AdapterCategories(this, dataList, getApplicationContext());
            categoryView.setAdapter(categoryAdapter);
            categoryView.setTextFilterEnabled(true);
            setupSearchView();

        }
        else

        {
            connectionDetector.showAlertDialog(this, "There is something Wrong", "You don't have internet connection.", false);
        }
    }
    // search view method
    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }


    // SearchView override method for blank text and

    @Override
    public boolean onQueryTextSubmit(String newText)
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(categoryView.getWindowToken(), 0);
        if (TextUtils.isEmpty(newText))
        {
            categoryView.clearTextFilter();

        }
        else
        {

            InputText = newText.toString();
            flag=1;
            JSONObject obj = new JSONObject();
            try
            {
                obj.put("SearchValue",InputText );

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            categorySearch(obj.toString());

        }
        return true;

    }

    @Override
    public boolean onQueryTextChange(String query) {
        return false;
    }


    private void categorySearch(String InputText) {


        class postdataclass extends AsyncTask<String, Void, String>
        {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
                linearLayoutprogress.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                categoryView.setVisibility(View.GONE);
            }


            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("JSON", params[0]));
                DefaultHttpClient httpclient;
                httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://www.justbusinesses.net/bridge/v0/verticalsearch.php");
                try
                {
                 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                  }
                catch (UnsupportedEncodingException e)
                {
                     e.printStackTrace();
                 }
                InputStream inputStream = null;
                 String result = null;
                  try
                  {
                   HttpResponse response = httpclient.execute(httppost);
                   HttpEntity entity = response.getEntity();
                   inputStream = entity.getContent();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                 StringBuilder sb = new StringBuilder();

                 String line = null;
                while ((line = reader.readLine()) != null)
                {

                 sb.append(line + "\n");
                }
                 result = sb.toString();


                } catch (Exception e)

                  {
                // Oops

                  }

                 finally

                 {

                  try
                {
                      if (inputStream != null) inputStream.close();

                }
                 catch (Exception squish)
                 {

                   }
                 }

                  return result;
            }

            @Override
            protected void onPostExecute(String result)
            {

                relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
                linearLayoutprogress.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                searchListview.setVisibility(View.VISIBLE);
                isInternetPresent = connectionDetector.isConnectingToInternet();
                if (isInternetPresent) {


                    ArrayList<HashMap<String, String>> records = new ArrayList<>();

                    try {
                        searchjsonObject = new JSONObject(result);
                        searchJsonArray = searchjsonObject.getJSONArray("SearchResult");
                        System.out.println("searchJsonArray" + searchJsonArray.toString());
                        for (int i = 0; i < searchJsonArray.length(); i++) {
                            JSONObject jsonObject1 = searchJsonArray.getJSONObject(i);
                            HashMap<String, String> searchresult = new HashMap<>();
                            searchresult.put("Category", jsonObject1.getString("Category"));
                            searchresult.put("SubCategory", jsonObject1.getString("SubCategory"));
                            searchresult.put("Vertical", jsonObject1.getString("Vertical"));
                            records.add(searchresult);
                        }
                        AdapterSearch adapter = new AdapterSearch(ActivityChooseCategory.this, records, getApplicationContext());

                        searchListview.setAdapter(adapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                else
                {


                    Toast.makeText(getApplication(), "Oops! Don't have internet connction", Toast.LENGTH_SHORT).show();
                    connectionDetector.showAlertDialog(getApplicationContext(), "There is something Wrong", "You don't have internet connection.", false);
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



        }

        postdataclass g = new postdataclass();
        g.execute(InputText);
    }
}