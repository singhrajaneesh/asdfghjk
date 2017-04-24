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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ekant on 12/01/16.
 */
public class AdapterSaveTagsMyprofile extends BaseAdapter {


    private LayoutInflater inflater=null;
    private Activity activity;
    public Context con;
    private ArrayList<HashMap<String, String>> data;
    public AdapterSaveTagsMyprofile(Activity a, ArrayList<HashMap<String, String>> d, Context context)
    {
        activity = a;
        data = d;
        con = context;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
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

        RelativeLayout vi= (RelativeLayout)convertView;
        if (convertView == null)
            vi = (RelativeLayout)inflater.inflate(R.layout.myprofilevertical, null);
        Typeface face = Typeface.createFromAsset(activity.getAssets(), "normal_futura.ttf");
        final HashMap<String, String> Category = data.get(position);
        TextView title = (TextView) vi.findViewById(R.id.name);
        title.setText(Category.get("GetTitle"));

        TextView category = (TextView) vi.findViewById(R.id.category);
        category.setText(Category.get("Category"));

        TextView subcategory = (TextView) vi.findViewById(R.id.subcategory);
        subcategory.setText(Category.get("SubCategories"));

        TextView vertical = (TextView) vi.findViewById(R.id.vertical);
        vertical.setText(Category.get("Verticals"));


        title.setTypeface(face);

        category.setTypeface(face);

        subcategory.setTypeface(face);

        vertical.setTypeface(face);


        return vi;
    }
}
