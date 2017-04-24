package com.ekant.justbiz;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.WakefulBroadcastReceiver;

public class GCMBroadCastReceiver extends WakefulBroadcastReceiver {
    public GCMBroadCastReceiver()
    {

    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle extras = intent.getExtras();
        Intent msgrcv = new Intent("Msg");
        msgrcv.putExtra("message", extras.getString("message"));
        msgrcv.putExtra("name", extras.getString("name"));
        msgrcv.putExtra("flag", extras.getString("flag"));
        msgrcv.putExtra("uid", extras.getString("uid"));

        LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);
        ComponentName comp = new ComponentName(context.getPackageName(),GCMIntentService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}
