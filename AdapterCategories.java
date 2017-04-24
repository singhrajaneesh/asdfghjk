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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ekant on 11/12/15.
 */
public class AdapterCategories extends BaseAdapter //implements Filterable
{

    final String imageurl = "http://www.justbusinesses.net/bridge/uploads/category-images/";
    private static LayoutInflater inflater=null;
    private Activity activity;
    public Context con;
    private ArrayList<HashMap<String, String>> data;
    public ModImageLoader imageLoader;
    TextView subcategory;

    public AdapterCategories(Activity a, ArrayList<HashMap<String, String>> d, Context context)
    {
        activity = a;
        data = d;
        con = context;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ModImageLoader(activity.getApplicationContext());
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
    public View getView(int position, View convertView, ViewGroup parent)

    {

        View vi = convertView;
        if (convertView == null) vi = inflater.inflate(R.layout.adapter_category_item, null);
        HashMap<String, String> Category = new HashMap<String, String>();
        Category =  data.get(position);
        Typeface face = Typeface.createFromAsset(activity.getAssets(), "normal_futura.ttf");
        subcategory = (TextView) vi.findViewById(R.id.subcategory);
        subcategory.setText(Category.get("Category"));
        subcategory.setTypeface(face);

       // ImageView backgroundimage = (ImageView) vi.findViewById(R.id.categoryimage);
       // backgroundimage.setImageResource(R.drawable.splash);
       // String backgroundurl = imageurl+Category.get("Image");
       // imageLoader.DisplayImage(backgroundurl, backgroundimage);
        return vi;
    }


}
