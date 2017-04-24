package com.ekant.justbiz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
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

public class ActivityNewsReader extends AppCompatActivity  implements ModAsyncResponce {

    final String imageurl = "http://www.justbusinesses.net/uploads/blogimages/smallimages/";
    ImageView newsHeader,facebook,twitter,instagram;
    TextView newsHeading, newsDescription;
    Boolean isInternetPresent = false;
    ModConnectionDetector connectionDetector;
    ProgressBar progressBar;
    JSONObject jsonObj;
    JSONArray jsonArray;
    public ModImageLoader imageLoader;
    String ID,news;
    LinearLayout linearLayoutprogress;
    RelativeLayout relativeLayoutlayoutprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_news_reader);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setIcon(R.drawable.logo);

        Typeface face = Typeface.createFromAsset(getAssets(), "futura_medium.ttf");

        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        linearLayoutprogress=(LinearLayout) findViewById(R.id.llprogress);
        relativeLayoutlayoutprogress=(RelativeLayout) findViewById(R.id.rlprogressbar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        connectionDetector = new ModConnectionDetector(getApplicationContext());



        ID = getIntent().getStringExtra("position");

        news=getIntent().getStringExtra("news");
        newsHeader = (ImageView) findViewById(R.id.newshaderposter);
        newsHeading = (TextView) findViewById(R.id.textviewheading);
        newsDescription = (TextView) findViewById(R.id.textviewdescription);
        imageLoader = new ModImageLoader(this);
        facebook=(ImageView) findViewById(R.id.facebook);
        twitter=(ImageView) findViewById(R.id.twitter);
        instagram=(ImageView) findViewById(R.id.instagram);


        newsHeading.setTypeface(face);
        newsDescription.setTypeface(face);


        sendDatatoWeb(ID);

    }

    public void facebook12(View view)

    {
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_VIEW);
    intent.addCategory(Intent.CATEGORY_BROWSABLE);
    intent.setData(Uri.parse("https://www.facebook.com/justbusinesses/"));
    overridePendingTransition(R.anim.right_in, R.anim.left_out);
    startActivity(intent);
    }

    public void twitter(View view)
    {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://twitter.com/justbusinesses"));
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        startActivity(intent);
    }

    public void instagram(View view)
    {


        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://www.instagram.com/justbusinesses/"));
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        startActivity(intent);

    }



    private void sendDatatoWeb(String ID) {

        relativeLayoutlayoutprogress.setVisibility(View.VISIBLE);
        linearLayoutprogress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        isInternetPresent = connectionDetector.isConnectingToInternet();
        if (isInternetPresent) {
            JSONObject obj = new JSONObject();
            try {

                obj.put("ID", ID);

            } catch (Exception e) {
                e.printStackTrace();

            }

            ModAsyncTask task = new ModAsyncTask();
            task.delegate = this;
            task.execute(obj.toString(), "newseventsdetail.php");



        } else {
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

    public void processFinish(String output) {

        String result = output;


        progressBar.setVisibility(View.INVISIBLE);
        relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
        linearLayoutprogress.setVisibility(View.INVISIBLE);

        if (isInternetPresent) {

            try {

                jsonObj = new JSONObject(result);

                jsonArray = jsonObj.getJSONArray("Getnewsandevents");

                for (int i = 0; i <= jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    String title = json.getString("Title");
                    String description = json.getString("Description");
                    String image = json.getString("Image");
                    newsHeading.setText(title);
                    newsDescription.setText(Html.fromHtml(description));
//
                    ModImageLoader imageLoader = new ModImageLoader(this);
                    String imageurl1 = imageurl + image;
                    imageLoader.newsreader(imageurl1, newsHeader);

                }

            } catch (JSONException e1) {
                e1.printStackTrace();

            }
        } else {
            connectionDetector.showAlertDialog(this, "No Internet Connection", "You don't have internet connection.", false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                    relativeLayoutlayoutprogress.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }, 0);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_friend_profile, menu);//Menu Resource, Menu

      //  menu.add("Add Contacts").setIcon(R.drawable.ico_alert30);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (android.R.id.home == item.getItemId() && news.equals("1"))

        {
            finish();
            Intent intent = new Intent(ActivityNewsReader.this, ActivityDashBoard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }



        if (android.R.id.home == item.getItemId() && news.equals("2"))

        {
            finish();
            Intent intent = new Intent(ActivityNewsReader.this, ActivityNewsandEvent.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }



        switch (item.getItemId())
        {
            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "http://www.justbusinesses.net/blogs");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this site!");

                // String sAux = "\nLet me recommend you this application\n\n";
                //  sAux = sAux + "https://play.google.com/store/apps/details?id=Orion.Soft \n\n";
                // intent.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(intent, "Share"));
                break;


            default:
                Toast.makeText(ActivityNewsReader.this, "Feature comming soon.", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {

       if (news.equals("1"))
       {
           finish();
           Intent intent = new Intent(ActivityNewsReader.this, ActivityDashBoard.class);
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           startActivity(intent);
           overridePendingTransition(R.anim.left_in, R.anim.right_out);
       }
        if (news.equals("2")) {

            finish();
            Intent intent = new Intent(ActivityNewsReader.this, ActivityNewsandEvent.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
                }

    }

}
