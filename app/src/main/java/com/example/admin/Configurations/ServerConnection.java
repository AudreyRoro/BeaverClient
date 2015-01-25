package com.example.admin.Configurations;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Marianne on 22/01/15.
 */
public class ServerConnection extends AsyncTask<JSONObject, Integer, JSONObject>{

    private static String url = "http://10.0.2.2:8080/";
    private static String endUrl ;

    public static String getEndUrl() {
        return endUrl;
    }

    public static void setEndUrl(String endUrl) {
        ServerConnection.endUrl = endUrl;
    }

    @Override
    protected JSONObject doInBackground(JSONObject... params) {
        sendJSONObject(params[0], url + endUrl);
        return null;
    }



    public static JSONObject sendJSONObject (JSONObject jsonObject, String finalUrl)
    {
        HttpClient httpclient = new DefaultHttpClient();

        try {

            HttpPost httppost = new HttpPost(finalUrl);

            StringEntity se = new StringEntity(jsonObject.toString());

            httppost.setEntity(se);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");

            HttpResponse response = httpclient.execute(httppost);
            String temp = EntityUtils.toString(response.getEntity());


            //Log.i("tag", temp);


        } catch (ClientProtocolException e) {

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
