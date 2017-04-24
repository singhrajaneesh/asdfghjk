package com.ekant.justbiz;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ekant on 14/12/15.
 */

public class AdapterChat extends BaseAdapter
{
    private static LayoutInflater inflater=null;
    private Activity activity;
    public Context con;
    String UID;
    private ArrayList<HashMap<String,String>> data;
    public ModImageLoader imageLoader;
    private LinearLayout chatcontainer;
// One-to-one Connection

    public AdapterChat(Activity a, ArrayList<HashMap<String, String>> d, Context context,String uid)
    {
        activity = a;
        data = d;
        con = context;
        UID = uid;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LinearLayout vi= (LinearLayout)convertView;
        if (convertView == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = (LinearLayout) inflater.inflate(R.layout.adapter_chat_item, null);
        }
        chatcontainer = (LinearLayout) vi.findViewById(R.id.chatlayout);

        HashMap<String,String> message = data.get(position);
        TextView profiletext = (TextView) vi.findViewById(R.id.ChatMsg);


        profiletext.setText(message.get("Message"));

        if (message.get("FromUID").equals(UID))
        {
            chatcontainer.setGravity(Gravity.LEFT);
            profiletext.setBackgroundResource(R.drawable.curve_corner_right);


        }

        else
        {


            chatcontainer.setGravity(Gravity.RIGHT);
            profiletext.setBackgroundResource(R.drawable.curve_corner_left);

        }
        return vi;
    }


}

