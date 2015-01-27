package com.example.admin.Configurations;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.admin.model.Buyer;
import com.example.admin.model.Event;
import com.example.admin.model.Participant;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.BasicConfigurator;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marianne on 27/01/15.
 */
public class HttpGetBuyers extends AsyncTask<Object, String, String> {

    private String url = "http://10.0.2.2:8080/Buyer/get";
    private List<Buyer> buyerList;
    Activity activity;
    ListView listView;

    @Override
    protected String doInBackground(Object... params) {
        Participant participant = (Participant)params[0];
        activity = (Activity) params[1];
        listView = (ListView) params[2];

        return addParticipant(participant);
    }

    protected String addParticipant(Participant participant)
    {
        BufferedReader inStream = null;
        String result = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            ObjectMapper mapper = new ObjectMapper();
            StringEntity se = null;
            try {
                se = new StringEntity(mapper.writeValueAsString(participant));
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
            while((line = inStream.readLine()) != null)
                result += line;
            inStream.close();

            buyerList = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(result);

            for (int i=0; i<jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                buyerList.add(mapper.readValue(jsonObject.toString(), Buyer.class));
            }
        } catch (Exception e) {
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
        if(buyerList.size() != 0) {
            BasicConfigurator.configure();
            ArrayAdapter<Buyer> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, buyerList);
            listView.setAdapter(adapter);
        } else
        {
            System.out.println("Aucun achat n'a été effectué");
        }

    }

}
