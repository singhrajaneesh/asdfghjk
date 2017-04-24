package com.ekant.justbiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ActivityChatDescription extends AppCompatActivity
{

    String url = "http://www.justbusinesses.net/bridge/uploads/company-logos/";
    ListView listView;
    AdapterChat adapter;
    ArrayList<HashMap<String, String>> dataList;
    String OtherMemberID, name, logo,imageurl1,UID;
    public ModImageLoader imageLoader;
    Moddb db;
    EditText editText;
    Button button;
    Boolean isInternetPresent = false;
    ModConnectionDetector connectionDetector;
    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_description);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setIcon(R.drawable.logo);
        Intent intent = getIntent();
        OtherMemberID = intent.getStringExtra("FriendID");

        name = intent.getStringExtra("FriendName");
        getSupportActionBar().setTitle(" " + name);
        logo = intent.getStringExtra("Logo");
        imageurl1 = url + logo;
        imageLoader = new ModImageLoader(getApplicationContext());
        connectionDetector = new ModConnectionDetector(getApplicationContext());
        editText = (EditText) findViewById(R.id.textsend);
        button = (Button) findViewById(R.id.sendmessage);
        db = new Moddb(this);

        UID = db.getValue("UID");
        db.updateMessage(UID);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setDivider(null);
        dataList = new ArrayList<HashMap<String, String>>();
        dataList = db.getfirebasemessage(UID, OtherMemberID);
        adapter = new AdapterChat(this, dataList, getApplicationContext(),UID);
        listView.setAdapter(adapter);
        listView.setSelection(listView.getAdapter().getCount() - 1);
        ref = new Firebase(FireBaseUrl.FIREBASE_URL).child("Messages");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Firebase messageref = new Firebase(FireBaseUrl.FIREBASE_URL).child("Messages");
        Query messagequery = messageref.orderByChild("ToUID").equalTo(UID);

        messagequery.addChildEventListener(new ChildEventListener()
        {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String previousChild)
        {
            ArrayList<HashMap<String,String>> newlist  = db.getfirebasemessage(UID, OtherMemberID);
            dataList.clear();
            dataList.addAll(0, newlist);
            adapter.notifyDataSetChanged();
            listView.setSelection(listView.getAdapter().getCount() - 1);

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
        Query messagequery1 = messageref.orderByChild("FromUID").equalTo(UID);

        messagequery1.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild)
            {
                ArrayList<HashMap<String,String>> newlist  = db.getfirebasemessage(UID, OtherMemberID);
                dataList.clear();
                dataList.addAll(0, newlist);
                adapter.notifyDataSetChanged();
                listView.setSelection(listView.getAdapter().getCount() - 1);

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

    public void send(View view)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss.SSS");
        String currentDateandTime = sdf.format(new Date());

        String message = editText.getText().toString().trim();


        if (message==null || message.trim().equals(""))
        {

            System.out.println("Blank value");

        }

        else {
            HashMap<String, String> hashmessage = new HashMap<String, String>();

            hashmessage.put("ToUID", OtherMemberID);
            hashmessage.put("FromUID", UID);
            hashmessage.put("Message", message);
            hashmessage.put("Date", currentDateandTime);
            hashmessage.put("Viewed", "0");
            ref.push().setValue(hashmessage);
            //ref.push().setValue(hashmessage, ServerValue.TIMESTAMP);
            editText.setText("");
        }

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
        Intent i5 = new Intent(ActivityChatDescription.this, ActivityContact.class);
        startActivity(i5);
        this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (android.R.id.home==item.getItemId())
        {
            finish();
            Intent i5 = new Intent(ActivityChatDescription.this, ActivityContact.class);
            startActivity(i5);
            this.overridePendingTransition(R.anim.left_in, R.anim.right_out);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
