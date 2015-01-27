package com.example.admin.Configurations;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.admin.model.Event;
import com.example.admin.model.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marianne on 23/01/15.
 */
public class HttpGetEvents extends AsyncTask<Object, Void, String>{

    private String url = "http://10.0.2.2:8080/Event/getByUser";
    private String result ;
    private ListView listView;
    private Activity activity;
    private List<Event> eventList;
    private final Logger log = Logger.getLogger(HttpGetEvents.class);
    @Override
    protected String doInBackground(Object... params) {
        this.listView = (ListView) params[0];
        this.activity = (Activity) params[1];

        User user = new User();
        user.setuId((Integer)params[2]);

        return getInputStreamFromUrl(url, user);
    }

    final String getInputStreamFromUrl(String url, User user) {
        BufferedReader inStream = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            ObjectMapper mapper = new ObjectMapper();
            StringEntity se = null;
            try {
                se = new StringEntity(mapper.writeValueAsString(user));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!se.equals(null))
                httpPost.setEntity(se);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse response = httpClient.execute(httpPost);
            inStream = new BufferedReader( new InputStreamReader( response.getEntity().getContent()));


            String line = "";
            result = "";
            while((line = inStream.readLine()) != null)
                result += line;
            inStream.close();

            eventList = new ArrayList<Event>();
            JSONArray jsonArray = new JSONArray(result);

            for (int i=0; i<jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                eventList.add(mapper.readValue(jsonObject.toString(), Event.class));
            }
            result = "success";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            if (inStream != null)
            {
                try {
                    inStream.close(); } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    protected void onPostExecute(String result)
    {
        BasicConfigurator.configure();
        log.info("Events number : " + eventList.size());
        ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(activity, android.R.layout.simple_list_item_1, eventList);
        listView.setAdapter(adapter);

    }
}
