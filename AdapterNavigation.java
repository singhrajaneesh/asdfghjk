package com.ekant.justbiz;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterNavigation extends BaseAdapter {

    private static LayoutInflater inflater=null;
    private Activity activity;
    public Context con;
    private int id;
    private String[] items;
    Boolean fileExists;
    public String[] images =  new String[]{"ico_users","ico_messages","news12","guide_icon","shared","ico_changepassword","quetion","ico_logout"};
//
//    "ico_messages",
//
    public AdapterNavigation(Activity a, String[] osArray, Context context)
    {

        items = osArray;
        con = context;
        activity = a;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount()
    {
        return items.length;
    }


    @Override
    public Object getItem(int position)
    {
        return position;
    }


    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        View mView = v;

        if (mView == null) mView = inflater.inflate(R.layout.adapter_navigation_item, null);
        Typeface face = Typeface.createFromAsset(activity.getAssets(), "normal_futura.ttf");

        TextView text = (TextView) mView.findViewById(R.id.tv131);
        text.setText(items[position]);
        ImageView image = (ImageView) mView.findViewById(R.id.image51);


        int id = activity.getResources().getIdentifier(images[position], "drawable", activity.getPackageName());
        image.setImageResource(id);
        text.setTypeface(face);



        return mView;
    }
}
