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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ekant on 06/02/16.
 */
public class AdapterLiveMessage extends BaseAdapter
{
    Activity activity;
    Context con;
    ArrayList<HashMap<String,String>>list;
    private static LayoutInflater inflater=null;
    final String companylogourl = "http://www.justbusinesses.net/bridge/uploads/company-logos/";


    public AdapterLiveMessage(Activity a, ArrayList<HashMap<String, String>> d, Context context)

    {
        activity = a;
        list = d;
        con = context;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount()
    {
        return list.size();
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

    public View getView(final int position, final View convertView, ViewGroup parent)
    {
        ModSwipeLayout vi= (ModSwipeLayout)convertView;
        if (convertView == null)
            vi = (ModSwipeLayout)inflater.inflate(R.layout.adapter_livemessage_item, null);
        vi.setShowMode(ModSwipeLayout.ShowMode.PullOut);
        vi.addDrag(ModSwipeLayout.DragEdge.Left, vi.findViewById(R.id.bottomwrapper));
        vi.addDrag(ModSwipeLayout.DragEdge.Right, vi.findViewById(R.id.bottomwrapper2));

        HashMap<String,String> message = list.get(position);

        System.out.println("listlistlistlist  " + list.toString());

        ImageView imageview=(ImageView)vi.findViewById(R.id.liveimageview);

        Typeface face = Typeface.createFromAsset(activity.getAssets(), "normal_futura.ttf");
        String imageurl1 = companylogourl + message.get("FromLogo");
        new ModImageLoader(activity).DisplayImage(imageurl1, imageview);

        TextView textview1=(TextView) vi.findViewById(R.id.companyname1);
        TextView textview2=(TextView) vi. findViewById(R.id.Fromcompany);


        TextView textview3=(TextView) vi.findViewById(R.id.fromindustries);
        TextView textview4=(TextView) vi. findViewById(R.id.companysubname2);



        textview1.setText(message.get("FromCompany"));
        textview2.setText(message.get("FromName"));
        textview3.setText(message.get("FromIndustry"));
        textview4.setText(message.get("Message"));


        textview1.setTypeface(face);
        textview2.setTypeface(face);
        textview3.setTypeface(face);
        textview4.setTypeface(face);




        ImageView delete = (ImageView) vi.findViewById(R.id.trashdashboard);
        delete.setTag(position);
        final ImageView like = (ImageView) vi.findViewById(R.id.chat);
        like.setTag(position);

       vi.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {

               Toast.makeText(activity, "Please swipe left or right for options", Toast.LENGTH_SHORT).show();


           }
       });


        return vi;

    };
}