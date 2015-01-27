package com.example.admin.Configurations;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.admin.beaver.PageEvenement;
import com.example.admin.model.Buyer;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by Marianne on 26/01/15.
 */
public class HttpPostBuyer extends AsyncTask<Object, Integer, String> {

    private String url = "http://10.0.2.2:8080/Buyer/add";
    private Context context;

    @Override
    protected String doInBackground(Object... params) {
        Buyer buyer = (Buyer) params[0];
        context = (Context) params[1];

        return sendBuyer(buyer);

    }

    protected String sendBuyer (Buyer buyer)
    {
        ObjectMapper mapper = new ObjectMapper();

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        StringEntity se = null;
        try {
            se = new StringEntity(mapper.writeValueAsString(buyer));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!se.equals(null))
            httppost.setEntity(se);

        httppost.setHeader("Accept", "application/json");
        httppost.setHeader("Content-type", "application/json");

        HttpResponse response = null;
        String temp = null;
        try {
            response = httpclient.execute(httppost);
            temp = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }

    protected void onPostExecute(String result) {

        Intent intent = new Intent(context, PageEvenement.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);


    }
}
