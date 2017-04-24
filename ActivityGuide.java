package com.ekant.justbiz;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityGuide extends AppCompatActivity  implements View.OnClickListener{


    Button button1,button2;
    TextView textView1,textView2,heading;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setIcon(R.drawable.logo);

        Typeface face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");

        //Video Button
        button1=(Button)findViewById(R.id.button2);
        // Image Button
        button2=(Button)findViewById(R.id.button3);
        // Video and Image Button click listener
        button1.setOnClickListener((View.OnClickListener) this);
        button2.setOnClickListener((View.OnClickListener) this);
        //Text for Video.(Watch Intro Video)
        textView1=(TextView) findViewById(R.id.videos);
        //Text for Image.(Watch Intro SlideShow)
        textView2=(TextView) findViewById(R.id.image);
        heading=(TextView) findViewById(R.id.heading);
        //Watch Intro Video click move to Videos activity
        textView1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ActivityGuide.this, ActivityVideo.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        //  Watch Intro SlideShow click move to Image Slider
        textView2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGuide.this, ActivityImage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });


        textView1.setTypeface(face);
        textView2.setTypeface(face);
        heading.setTypeface(face);


    }
    // Both Video and Image button click
    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.button2){
            finish();
            Intent intent = new Intent(ActivityGuide.this, ActivityVideo.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
        else if(v.getId() == R.id.button3)
        {
            finish();
            Intent intent = new Intent(ActivityGuide.this, ActivityImage.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
    }
}



