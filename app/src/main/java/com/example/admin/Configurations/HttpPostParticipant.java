package com.example.admin.Configurations;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.admin.model.Participant;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marianne on 25/01/15.
 */
public class HttpPostParticipant extends AsyncTask<Object, Integer, String> {

    private String url = "http://10.0.2.2:8080/Participant/add";
    private Activity activity ;
    private ListView listView;
    private String action ;

    @Override
    protected String doInBackground(Object... params) {
        Participant participant = (Participant) params[0];

        action = (String) params[1];
        String result = null;
        switch(action)
        {
            case "PARTICIPANT" :
                activity = (Activity) params[2];
                listView = (ListView) params[3];
                result = addParticipant(participant);
                break;

            case "EVENT" :
                result = addParticipant(participant);
                break;

            default :
                result = null;
                break;
        }

        return result;

    }

    protected String addParticipant(Participant participant)
    {
        ObjectMapper mapper = new ObjectMapper();

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        StringEntity se = null;
        try {
            se = new StringEntity(mapper.writeValueAsString(participant));
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

        if (action.equals("PARTICIPANT"))
        {
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            List<Participant> participants = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    participants.add(mapper.readValue(jsonObject.toString(), Participant.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(participants.size());
            //listView.setAdapter(null);
            ArrayAdapter<Participant> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, participants);

            listView.setAdapter(adapter);

        }
    }
}