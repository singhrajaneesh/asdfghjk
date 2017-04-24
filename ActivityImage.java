package com.ekant.justbiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityImage extends Activity
{
    TextView textView;
    Gallery gallery;
    int width, height;
    LinearLayout layout;
    LinearLayout linearLayout;
    Integer[] pics = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6, R.drawable.a7};
    ImageView imageview;
    DisplayMetrics displaymetrics;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Typeface face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");
        layout = (LinearLayout) findViewById(R.id.imageLayout1);
        textView=(TextView) findViewById(R.id.button);
        textView.setTypeface(face);
        linearLayout=(LinearLayout) findViewById(R.id.linearLayouttextview);
        displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.heightPixels;
        height = displaymetrics.widthPixels;
        // imageView=(ImageView) findViewById(R.id.imageview);
        for(int i=0; i<pics.length; i++)
        {
            imageview = new ImageView(this);
            imageview.setId(i);
            imageview.setBackgroundResource(R.drawable.off);
            layout.addView(imageview);
        }
        gallery = (Gallery)findViewById(R.id.thisgallery);
        gallery.setAdapter(new ImageAdapter(this));
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {

            }
        });
    }
    // Mobile on backpress

    @Override
    public void onBackPressed()
    {
        finish();
        ActivityImage.this.overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }

    public class ImageAdapter extends BaseAdapter
    {

        private Context ctx;
        int imageBackground;
        int pre=-1;
        public ImageAdapter(Context c)
        {
            ctx = c;
        }

        public int getCount()
        {

            return pics.length;
        }

        public View getView(int arg0, View convertView, ViewGroup arg2)
        {

            ImageView iv;
            LinearLayout layoutnew = new LinearLayout(getApplicationContext());
            layoutnew.setOrientation(LinearLayout.VERTICAL);

            if (convertView == null)
            {
                iv = new ImageView(ctx);
                iv.setImageResource(pics[arg0]);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                int temp =(int) (height/1.3f);
                int temp_y = (int) ((3*temp)/2.0f);
                iv.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                iv.setPadding(0, 0, 0, 173);
                iv.setBackgroundResource(imageBackground);
            }
            else
            {
                iv = (ImageView) convertView;
            }
            TextView tv = new TextView(ctx);
            tv.setText("");
            tv.setTextColor(0xFFFFFFFF);
            tv.setPadding(0, 15, 0, 0);
            tv.setTextSize(18);
            tv.setGravity(Gravity.CENTER);
            layoutnew.addView(iv);
            layoutnew.addView(tv);
            return layoutnew;

        }

        @Override

        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            if(pre !=-1)
            {
                ImageView img = (ImageView) findViewById(pre);
                img.setBackgroundResource(R.drawable.off);
            }
            ImageView img1 = (ImageView) findViewById(position);
            img1.setBackgroundResource(R.drawable.on);
            this.pre = position;
            if (position==6)
            {
                TextView textView=(TextView) findViewById(R.id.button);
                LinearLayout linearLayout=(LinearLayout) findViewById(R.id.linearLayouttextview);
                TranslateAnimation slide = new TranslateAnimation(0, 0, 100,0 );
                slide.setDuration(1000);
                textView.startAnimation(slide);
                linearLayout.startAnimation(slide);
                textView.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                textView.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.INVISIBLE);
            }
            return position;
        }

    }
    // TextView click move to login
    public void show(View view)
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
        Intent intent=new Intent(ActivityImage.this,ActivityLogin.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }
}