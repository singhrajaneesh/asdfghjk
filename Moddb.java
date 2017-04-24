package com.ekant.justbiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ekant on 25/12/15.
 */
public class Moddb extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "JustBiz.db";

    public Moddb(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // TODO Auto-generated method stub
        db.execSQL("create table firebaseMessages" + "(ID  INTEGER PRIMARY KEY AUTOINCREMENT,Key text, Date text, FromUID text, Message text, ToUID text,Viewed text)");
        db.execSQL("create table firebaseFriends" + "(Key text, Date text, FriendID text, FriendName text, PersonID text,FriendLogo text,FriendCompany text,FriendIndustry text)");
        db.execSQL("create table firebaseBroadcastMessage" + "(Key text, FromName text, FromUID text, Message text, ToName text, ToUID, Date text,Like text,FromLogo text,FromCompany text,FromIndustry text, ToLogo text, ToCompany text,ToIndustry text)");
        db.execSQL("create table Settings " + " (id integer primary key,name text, value text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS firebaseMessages");
        db.execSQL("DROP TABLE IF EXISTS firebaseFriends");
        db.execSQL("DROP TABLE IF EXISTS firebaseBroadcastMessage");
        db.execSQL("DROP TABLE IF EXISTS Settings");
        onCreate(db);
    }

    //insert into broadcastMessage
    public boolean insertbroadcast(String Key,String FromName,String FromID, String Message,String ToName,String ToUID,String Date,String Like,String FromLogo,
                                   String FromCompany,String FromIndustry,String ToLogo,String ToCompany,String ToIndustry
    )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Key", Key);
        contentValues.put("FromName", FromName);
        contentValues.put("FromUID", FromID);
        contentValues.put("Message", Message);
        contentValues.put("ToName", ToName);
        contentValues.put("ToUID", ToUID);
        contentValues.put("Date", Date);
        contentValues.put("Like", Like);
        contentValues.put("FromLogo", FromLogo);
        contentValues.put("FromCompany", FromCompany);
        contentValues.put("FromIndustry", FromIndustry);
        contentValues.put("ToLogo", ToLogo);
        contentValues.put("ToCompany", ToCompany);
        contentValues.put("ToIndustry", ToIndustry);
        db.insert("firebaseBroadcastMessage", null, contentValues);
        return true;
    }


    // get from broadcast
    public ArrayList<HashMap<String,String>> getbroadcast(String UID) {
        ArrayList<HashMap<String, String>> broadcastmessages = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from firebaseBroadcastMessage where Like = 0 and ToUID ='"+UID+"'", null);
        res.moveToFirst();
        while(res.isAfterLast() == false)
        {
            HashMap<String,String> broadcast = new HashMap<String,String>();
            broadcast.put("Key",res.getString(res.getColumnIndex("Key")));
            broadcast.put("FromName",res.getString(res.getColumnIndex("FromName")));
            broadcast.put("FromUID",res.getString(res.getColumnIndex("FromUID")));
            broadcast.put("Message",res.getString(res.getColumnIndex("Message")));
            broadcast.put("ToUID",res.getString(res.getColumnIndex("ToUID")));
            broadcast.put("ToName",res.getString(res.getColumnIndex("ToName")));
            broadcast.put("Date",res.getString(res.getColumnIndex("Date")));
            broadcast.put("FromLogo",res.getString(res.getColumnIndex("FromLogo")));
            broadcast.put("FromCompany",res.getString(res.getColumnIndex("FromCompany")));
            broadcast.put("FromIndustry",res.getString(res.getColumnIndex("FromIndustry")));
            broadcast.put("ToLogo",res.getString(res.getColumnIndex("ToLogo")));
            broadcast.put("ToCompany",res.getString(res.getColumnIndex("ToCompany")));
            broadcast.put("ToIndustry",res.getString(res.getColumnIndex("ToIndustry")));
            broadcastmessages.add(broadcast);
            res.moveToNext();
        }
        return broadcastmessages;

    }

    // insert into fireBaseFriends

    public boolean insertfirebaseFriends(String Key,String Date,String FriendID, String FriendName,String PersonID,String FriendLogo,String FriendCompany,String FriendIndustry)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Key", Key);
        contentValues.put("Date", Date);
        contentValues.put("FriendID", FriendID);
        contentValues.put("FriendName", FriendName);
        contentValues.put("PersonID", PersonID);
        contentValues.put("FriendLogo", FriendLogo);
        contentValues.put("FriendCompany", FriendCompany);
        contentValues.put("FriendIndustry", FriendIndustry);
        db.insert("firebaseFriends", null, contentValues);
        return true;
    }


    // get into firebasefriends
    public ArrayList<HashMap<String,String>> getfriends(String PersonID)
    {
        ArrayList<HashMap<String, String>> broadcastmessages = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from firebaseFriends where PersonID='"+PersonID+"' group by FriendName", null);
        res.moveToFirst();
        while(res.isAfterLast() == false)
        {
            HashMap<String,String> broadcast = new HashMap<String,String>();
            broadcast.put("Key",res.getString(res.getColumnIndex("Key")));
            broadcast.put("Date",res.getString(res.getColumnIndex("Date")));
            broadcast.put("FriendID",res.getString(res.getColumnIndex("FriendID")));
            String value = res.getString(res.getColumnIndex("FriendID"));
            broadcast.put("ChatCount", String.valueOf(getContactChat(value)));
            broadcast.put("FriendName",res.getString(res.getColumnIndex("FriendName")));
            broadcast.put("PersonID", res.getString(res.getColumnIndex("PersonID")));
            broadcast.put("FriendLogo", res.getString(res.getColumnIndex("FriendLogo")));
            broadcast.put("FriendCompany", res.getString(res.getColumnIndex("FriendCompany")));
            broadcast.put("FriendIndustry", res.getString(res.getColumnIndex("FriendIndustry")));
            broadcastmessages.add(broadcast);
            res.moveToNext();
        }
        return broadcastmessages;
    }
    public int getContactChat(String id)
    {
        int contactChatCount = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from firebaseMessages where viewed = 0 and FromUID ='"+id+"'", null);
        res.moveToFirst();
        contactChatCount = res.getCount();
        return contactChatCount;
    }

    //insert into  firebase Message
    public boolean firebaseMessages(String Key,String Date,String FromUID, String Message,String ToUID,String Viewed)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Key", Key);
        contentValues.put("Date", Date);
        contentValues.put("FromUID", FromUID);
        contentValues.put("Message", Message);
        contentValues.put("ToUID", ToUID);
        contentValues.put("Viewed", Viewed);
        db.insert("firebaseMessages", null, contentValues);
        return true;
    }


// get into firebase message

    public ArrayList<HashMap<String,String>> getfirebasemessage(String FromUID,String ToUID)
    {
        ArrayList<HashMap<String, String>> Messages = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from firebaseMessages where (FromUID='"+FromUID+"' and ToUID='" +ToUID+"') or (ToUID='"+FromUID+"' and FromUID='" +ToUID+"') order by ID asc ";//Date

        Cursor res =  db.rawQuery(query, null);
        res.moveToFirst();
        while(res.isAfterLast() == false)
        {
            HashMap<String,String> Message = new HashMap<String,String>();
            Message.put("Key",res.getString(res.getColumnIndex("Key")));
            Message.put("Date",res.getString(res.getColumnIndex("Date")));
            Message.put("FromUID",res.getString(res.getColumnIndex("FromUID")));
            Message.put("Message",res.getString(res.getColumnIndex("Message")));
            Message.put("ToUID",res.getString(res.getColumnIndex("ToUID")));
            Message.put("Viewed",res.getString(res.getColumnIndex("Viewed")));

            Messages.add(Message);
            res.moveToNext();
        }
        return Messages;
    }


    // use for get count
    public int getFireCount(String Key,String tablename)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from " + tablename + " where Key = '" + Key + "'", null);
        res.moveToFirst();
        if(res.getCount()==1)
        {
            return 1;
        }
        return 0;
    }

    public int getMessageCount(String ToUID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from firebaseMessages where Viewed=0 and ToUID ='"+ToUID+"'", null);
        res.moveToFirst();
        return res.getCount();
    }

    public int updateMessage(String id)
    {
        int contactChatCount = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =  db.rawQuery("update firebaseMessages set Viewed = 1 where ToUID ='" + id + "'", null);
        res.moveToFirst();
        contactChatCount = res.getCount();
        return contactChatCount;
     }


// Get friendCount
    public int getFriendCount()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from firebaseFriends ", null);
        res.moveToFirst();
        return res.getCount();
    }

    // Setting Table
// Function To insert setting Table

    public boolean insertLogin(String name,String value)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("value", value);
        db.insert("Settings", null, contentValues);
        return true;
    }

// Function to update settings table

    public boolean updateValue(String name,String value)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("value", value);
        db.update("Settings", contentValues, "name = ? ", new String[]{name});
        return true;
    }

    // Function to get value from settings table
    public String getValue(String name)
    {
        String hash = new String();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from Settings where name = '" + name+"'", null);
        res.moveToFirst();
        if(res.getCount()==1)
        {
            hash = res.getString(res.getColumnIndex("value"));
        }
        return hash;
    }

    // Setting table getCount
    public int getCount(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from Settings where name = '" + name + "'", null);
        res.moveToFirst();
        if(res.getCount()==1)
        {
            return 1;
        }
        return 0;
    }

    public void deleteMsg(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("firebaseBroadcastMessage", "Key"+"='"+id+"'", null);

    }


}
