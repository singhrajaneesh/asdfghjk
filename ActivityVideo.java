package com.ekant.justbiz;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class ActivityVideo extends AppCompatActivity implements View.OnClickListener
{
    WebView webView;
    TextView button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        // This is use for hide action bar message.
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Typeface face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");
        // Place the Video url .
        webView=(WebView) findViewById(R.id.mWebView);
        String frameVideo = "<html><body><br><iframe width=\"100%\" height=\"85%\" src=\"https://www.youtube.com/embed/POAGGvSpRgs\"  frameborder=\"0\" padding=\"0px\" margin=\"0px\"allowfullscreen></iframe></body></html>";
        //String frameVideo = "<iframe title=\\\"YouTube video player\\\" width=\\\"320\\\" height=\\\"460\\\" scrolling=\\\"no\\\" src=\\\"http://stackoverflow.com/questions/15829851/how-to-play-a-video-in-landscape-mode-but-app-support-only-portait-mode\\\" frameborder=\\\"0\\\" allowfullscreen allowTransparency=\\\"true\\\"></iframe>";
        WebView displayYoutubeVideo = (WebView) findViewById(R.id.mWebView);
        button=(TextView) findViewById(R.id.button);
        button.setOnClickListener((View.OnClickListener) this);

        button.setTypeface(face);
        displayYoutubeVideo.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                return false;
            }
        });
        WebSettings webSettings = displayYoutubeVideo.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(false);
        displayYoutubeVideo.loadData(frameVideo, "text/html", "utf-8");
    }
    // To move from activityVidoes to login or any place video will in onReume State
    @Override
    protected void onResume()
    {
        super.onResume();
        try {
            WebView.class.getMethod("onResume").invoke(webView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // If videos is running and move to login than pause videos automatically.
    @Override
    protected void onPause()
    {
        super.onPause();
        try {
            WebView.class.getMethod("onPause").invoke(webView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Mobile on back press you will leave the state.
    @Override
    public void onBackPressed()
    {
        finish();
        Intent intent=new Intent(ActivityVideo.this,ActivityGuide.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        ActivityVideo.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
    // Click on this you will move to login.
    @Override
    public void onClick(View v)
    {
        finish();
        Moddb db = new Moddb(this);
        int value = db.getCount("Guide");
        if(value == 0)
        {
            db.insertLogin("Guide", "1");
        }
        else
        {
            db.updateValue("Guide", "1");
        }
        Intent intent=new Intent(getApplicationContext(),ActivityLogin.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }
}

