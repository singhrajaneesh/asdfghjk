package com.ekant.justbiz;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekant on 10/05/16.
 */
public class ModgetProfile extends AsyncTask<String, Void, String>
{
    String url = "http://www.justbusinesses.net/bridge/v0/";
    String jsonresult = "";
    public ModProfileResponce delegate = null;

    @Override
    protected String doInBackground(String... params)
    {
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("JSON", params[0]));
        url = url + params[1];
        HttpPost httppost = new HttpPost(url);
        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {

                sb.append(line + "\n");
            }
            result = sb.toString();


        } catch (Exception e)
        {

        }
        finally
        {
            try {
                if (inputStream != null) inputStream.close();
            } catch (Exception squish) {

            }
        }
        return result;
    }
    @Override
    protected void onPostExecute(String result)
    {
        delegate.getProfile(result);

    }
}
