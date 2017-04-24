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

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterDashboardnews extends BaseAdapter{

    final String imageurl = "http://www.justbusinesses.net/uploads/blogimages/smallimages/";
    private static LayoutInflater inflater=null;
    private Activity activity;
    public Context con;
    private ArrayList<HashMap<String, String>> data;
    public ModImageLoader imageLoader;

    public AdapterDashboardnews(Activity a, ArrayList<HashMap<String, String>> d, Context context) {
        // TODO Auto-generated constructor stub

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
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.dashboardnewsitem, null);

        ImageView img=(ImageView) vi.findViewById(R.id.imageView1);

        Typeface face = Typeface.createFromAsset(activity.getAssets(), "normal_futura.ttf");
        TextView tv=(TextView) vi.findViewById(R.id.textView1);
        HashMap<String, String> detail = new HashMap<String, String>();
        detail = data.get(position);
        String imageurl1 = imageurl + detail.get("Image");
        new ModImageLoader(activity).newsDashboard(imageurl1, img);
        tv.setText(detail.get("Heading"));
        tv.setTypeface(face);

        return vi;
    }

}