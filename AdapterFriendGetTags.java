package com.ekant.justbiz;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ekant on 07/03/16.
 */
public class AdapterFriendGetTags extends BaseAdapter
{
    private LayoutInflater inflater=null;
    private Activity activity;
    public Context con;
    private ArrayList<HashMap<String, String>> data;
    public AdapterFriendGetTags(Activity a, ArrayList<HashMap<String, String>> d, Context context)
    {
        activity = a;
        data = d;
        con = context;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount()
    {

        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)

    {

        LinearLayout vi= (LinearLayout)convertView;
        if (convertView == null)
            vi = (LinearLayout)inflater.inflate(R.layout.friendverticalstag, null);
        HashMap<String, String> Category = data.get(position);
        Typeface face = Typeface.createFromAsset(activity.getAssets(), "normal_futura.ttf");
        TextView title = (TextView) vi.findViewById(R.id.name);



        TextView category = (TextView) vi.findViewById(R.id.friendcategory);
        category.setText(Category.get("Category"));

        TextView subcategory = (TextView) vi.findViewById(R.id.friendsubcategory);
        subcategory.setText(Category.get("SubCategories"));

        TextView vertical = (TextView) vi.findViewById(R.id.friendvertical);
        vertical.setText(Category.get("Verticals"));



      //  title.setTypeface(face);

        category.setTypeface(face);

        subcategory.setTypeface(face);

        vertical.setTypeface(face);

        return vi;
    }
}
