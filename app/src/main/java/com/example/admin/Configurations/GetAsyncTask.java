package com.example.admin.Configurations;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by Marianne on 23/01/15.
 */
public class GetAsyncTask extends AsyncTask<String, String, JSONObject>{

    private static String url = "http://10.0.2.2:8080/";
    private static String endUrl ;

    public static String getEndUrl() {
        return endUrl;
    }

    public static void setEndUrl(String endUrl) {
        GetAsyncTask.endUrl = endUrl;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        getInputStreamFromUrl(url + endUrl);
        return null ;
    }

    public static InputStream getInputStreamFromUrl(String url) {
        InputStream content = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            content = response.getEntity().getContent();
        } catch (Exception e) {
            System.out.println("probleme get : " + e.getMessage());
        }
        return content;
    }
}
