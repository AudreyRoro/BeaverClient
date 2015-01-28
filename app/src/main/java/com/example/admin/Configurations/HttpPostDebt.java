package com.example.admin.Configurations;

import android.os.AsyncTask;

import com.example.admin.model.Event;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by Marianne on 27/01/15.
 */
public class HttpPostDebt extends AsyncTask<Object, String, String> {

    private String url = "http://10.0.2.2:8080/Debt/add";

    @Override
    protected String doInBackground(Object... params) {
        Event event = (Event) params[0];
        createNewDebt(event);
        return null;
    }

    public void createNewDebt(Event event){
        event.setParticipants(null);
        ObjectMapper mapper = new ObjectMapper();

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        StringEntity se = null;
        try {
            se = new StringEntity(mapper.writeValueAsString(event));
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
    }
}