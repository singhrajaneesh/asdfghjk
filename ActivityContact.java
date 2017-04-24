package com.ekant.justbiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityContact extends AppCompatActivity
{
    ListView listView ;
    AdapterContact adapter;
    ArrayList<HashMap<String, String>> dataList;
    Moddb db;
    String message,name;
    TextView contact,contactalertmessage;
    String shraredUID;
    String MyPREFERENCES = "MyPREFERENCES";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setIcon(R.drawable.logo);

        dataList = new ArrayList<HashMap<String, String>>();
        db = new Moddb(this);
        listView = (ListView) findViewById(R.id.listView);
        Typeface face = Typeface.createFromAsset(getAssets(), "normal_futura.ttf");

        contact=(TextView) findViewById(R.id.contact);

        contact.setTypeface(face);




        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String strJson = sharedpreferences.getString("UserInfo", null);
        shraredUID = sharedpreferences.getString("UID", null);
        dataList = db.getfriends(shraredUID);

        System.out.println("dataList  "+dataList);

        if (dataList.size()==0)
        {

            contactalertmessage=(TextView) findViewById(R.id.contactalertmessage);
            contactalertmessage.setTypeface(face);
            contactalertmessage.setVisibility(View.VISIBLE);
            contactalertmessage.setTypeface(face);
            listView.setVisibility(View.INVISIBLE);


        }
        else
        {


            listView.setVisibility(View.VISIBLE);
            adapter = new AdapterContact(this,dataList,getApplicationContext());
            listView.setAdapter(adapter);
            //   LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
                {
                    HashMap<String, String> n = new HashMap<String, String>();
                    n = dataList.get(position);
                    ArrayList<HashMap<String, String>> newlist = db.getfriends(shraredUID);
                    dataList.clear();
                    dataList.addAll(0, newlist);
                    adapter.notifyDataSetChanged();
                    finish();
                    Intent i = new Intent(ActivityContact.this, ActivityChatDescription.class);
                    i.putExtra("FriendID", n.get("FriendID"));
                    i.putExtra("FriendName", n.get("FriendName"));
                    startActivity(i);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
            });
        }

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Firebase broadcastref = new Firebase(FireBaseUrl.FIREBASE_URL).child("BroadcastMessage");
        Query broadcastquery = broadcastref.orderByChild("PersonID").equalTo(shraredUID);
        broadcastquery.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild)
            {
                Moddb db1 = new Moddb(getBaseContext());
                ArrayList<HashMap<String, String>> newlist = db1.getfriends(shraredUID);
                dataList.clear();
                dataList.addAll(0, newlist);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (android.R.id.home==item.getItemId())
        {
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }


}
