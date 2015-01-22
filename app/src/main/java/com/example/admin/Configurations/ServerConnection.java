package com.example.admin.Configurations;

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
public class ServerConnection {

    private static String url;

    public ServerConnection(){
        url = "http://localhost:8080/";
    }

    public JSONObject sendJSONObject (JSONObject jsonObject, String endUrl)
    {
        HttpParams myParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(myParams, 10000);
        HttpConnectionParams.setSoTimeout(myParams, 10000);
        HttpClient httpclient = new DefaultHttpClient(myParams);
        String json= jsonObject.toString();

        try {

            HttpPost httppost = new HttpPost(url + endUrl);
            httppost.setHeader("Content-type", "application/json");

            StringEntity se = new StringEntity(jsonObject.toString());
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httppost.setEntity(se);

            HttpResponse response = httpclient.execute(httppost);
            String temp = EntityUtils.toString(response.getEntity());


            //Log.i("tag", temp);


        } catch (ClientProtocolException e) {

        } catch (IOException e) {
        }
        return null;
    }
}
