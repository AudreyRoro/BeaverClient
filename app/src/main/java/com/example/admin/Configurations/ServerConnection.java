package com.example.admin.Configurations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.admin.beaver.PageAccueil;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Marianne on 22/01/15.
 */
public class ServerConnection extends AsyncTask<JSONObject, Integer, JSONObject>{


    private static String url = "http://192.168.43.148:8080/";
    private static String endUrl;

    public static String getEndUrl() {
        return endUrl;
    }

    public static void setEndUrl(String endUrl) {
        ServerConnection.endUrl = endUrl;
    }

    @Override
    protected JSONObject doInBackground(JSONObject... params) {
        JSONObject res = sendJSONObject(params[0], url + endUrl);
        return res;
    }


    public static JSONObject sendJSONObject(JSONObject jsonObject, String finalUrl) {
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