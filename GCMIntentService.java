package com.ekant.justbiz;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;


public class GCMIntentService extends IntentService
{
    SharedPreferences prefs;
    NotificationCompat.Builder notification;
    NotificationManager manager;
    public GCMIntentService()
    {
        super("GCMIntentService");
    }
    public  Context context;

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);
        prefs = getSharedPreferences("Chat", 0);


        if (!extras.isEmpty())
        {

            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType))
            {
                Log.e("L2C","Error");

            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType))
            {
                Log.e("L2C","Error");

            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType))
            {

                if(!prefs.getString("CURRENT_ACTIVE","").equals(extras.getString("message")))
                {
                    sendNotification(extras.getString("message"), extras.getString("name"), extras.getString("flag"));
                }
                Log.i("TAG", "Received: " + extras.getString("msg"));
            }
        }
        GCMBroadCastReceiver.completeWakefulIntent(intent);
    }
    private void sendNotification(String msg,String title, String flag )
    {

        //Moddb db = new Moddb(this);
        //db.insertMessage(title,msg,flag);
        Bundle args = new Bundle();
        Intent chat = new Intent(this, ActivityDashBoard.class);
        chat.putExtra("INFO", args);
        notification = new NotificationCompat.Builder(this);
        notification.setContentTitle(title);
        notification.setContentText(msg);
        notification.setTicker("New Message !");
        notification.setSmallIcon(R.mipmap.ic_launcher);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 1000, chat, PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setContentIntent(contentIntent);
        notification.setAutoCancel(true);
        //manager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //manager.notify(0, notification.build());

    }




}
