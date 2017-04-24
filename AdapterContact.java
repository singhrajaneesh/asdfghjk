package com.ekant.justbiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class AdapterContact extends BaseAdapter
{
    final String imageurl = "http://www.justbusinesses.net/bridge/uploads/company-logos/";
    private static LayoutInflater inflater=null;
    private Activity activity;
    public Context con;
    private ArrayList<HashMap<String, String>> data;
    public ModImageLoader imageLoader;


    public AdapterContact(Activity a, ArrayList<HashMap<String, String>> d, Context context)
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View vi = convertView;
        HashMap<String, String> detail = new HashMap<String, String>();
        detail = data.get(position);
        if (convertView == null)
            vi = inflater.inflate(R.layout.adapter_contacts_item, null);
        ImageView profileimage = (ImageView) vi.findViewById(R.id.contactprofileimage);
        String imageurl1 = imageurl + detail.get("FriendLogo");
        new ModIconLoader(activity).DisplayImage(imageurl1, profileimage);
        profileimage.setTag(position);

        profileimage.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent i = new Intent(activity, ActivityFriendProfile.class);
                HashMap<String, String> detail = data.get(position);
                i.putExtra("MemberID", detail.get("FriendID"));
                activity.startActivity(i);
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });

        Typeface face = Typeface.createFromAsset(activity.getAssets(), "normal_futura.ttf");
        TextView profiletext = (TextView) vi.findViewById(R.id.profiletext);
        TextView friendcompanyname = (TextView) vi.findViewById(R.id.friendcompanyname);
        TextView friendindustries = (TextView) vi.findViewById(R.id.friendindustries);
        TextView msgcount = (TextView) vi.findViewById(R.id.msgcount);




        profiletext.setText(detail.get("FriendCompany"));
        friendcompanyname.setText(detail.get("FriendName"));
        friendindustries.setText(detail.get("FriendIndustry"));


        profiletext.setTypeface(face);
        friendcompanyname.setTypeface(face);
        friendindustries.setTypeface(face);




        int value = Integer.parseInt(detail.get("ChatCount"));

        if(value!=0)
        {
            msgcount.setVisibility(View.VISIBLE);
            msgcount.setText(detail.get("ChatCount"));
        }
        else
        {
            msgcount.setVisibility(View.INVISIBLE);
        }

        return vi;
    }


}

///// Important to curve corner image
//
//    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
//        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
//                .getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(output);
//
//        final int color = 0xff424242;
//        final Paint paint = new Paint();
//        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//        final RectF rectF = new RectF(rect);
//        final float roundPx = pixels;
//
//        paint.setAntiAlias(true);
//        canvas.drawARGB(0, 0, 0, 0);
//        paint.setColor(color);
//        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
//
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, rect, rect, paint);
//
//        return output;
//    }
