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

/**
 * Created by ekant on 16/04/16.
 */
public class AdapterNewsandEvent extends BaseAdapter
{
    final String imageurl = "http://www.justbusinesses.net/uploads/blogimages/smallimages/";
    private static LayoutInflater inflater=null;
    private Activity activity;
    public Context con;    private ArrayList<HashMap<String, String>> data;
    public ModImageLoader imageLoader;

    public AdapterNewsandEvent(Activity a, ArrayList<HashMap<String, String>> d, Context context)
    {
        // TODO Auto-generated constructor stub
        activity = a;
        data = d;

        con = context;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ModImageLoader(activity.getApplicationContext());
    }
    @Override
    public int getCount()
    {

        return data.size();
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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.newsandeventchild, null);

        Typeface face = Typeface.createFromAsset(activity.getAssets(), "normal_futura.ttf");
        ImageView profileimage = (ImageView) vi.findViewById(R.id.imageview);
        TextView textView=(TextView) vi.findViewById(R.id.textview1);
     //   TextView textView1=(TextView) vi.findViewById(R.id.textview2);
        HashMap<String, String> detail = new HashMap<String, String>();
        detail = data.get(position);

        System.out.println("detaildetail    "+detail.toString());

        String imageurl1 = imageurl + detail.get("Image");
        new ModImageLoader(activity).newsandevent(imageurl1, profileimage);

        textView.setText(detail.get("Heading"));
       // textView1.setText(detail.get("SubHeading"));

        textView.setTypeface(face);
       // textView1.setTypeface(face);



        return vi;

    }
}

