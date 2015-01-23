package com.example.admin.Configurations;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Marianne on 23/01/15.
 */
public class GetAsyncTask extends AsyncTask<String, String, JSONArray>{

    private static String url = "http://10.0.2.2:8080/";
    private static String endUrl ;

    public static String getEndUrl() {
        return endUrl;
    }

    public static void setEndUrl(String endUrl) {
        GetAsyncTask.endUrl = endUrl;
    }

    @Override
    protected JSONArray doInBackground(String... params) {
        getInputStreamFromUrl(url + endUrl);
        return null ;
    }

    public static JSONArray getInputStreamFromUrl(String url) {
        InputStream content = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            content = response.getEntity().getContent();
        } catch (Exception e) {
            System.out.println("probleme get : " + e.getMessage());
        }
        JSONArray jsonArray = new JSONArray();

        try {
            jsonArray = convertInputStreamToString(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    private static JSONArray convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;

    }
}
