package com.example.admin.Configurations;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.admin.Adapters.GiverAdapter;
import com.example.admin.Adapters.ReceiverAdapter;
import com.example.admin.beaver.R;
import com.example.admin.model.Buyer;
import com.example.admin.model.Debt;
import com.example.admin.model.Participant;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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
public class HttpGetDebts extends AsyncTask<Object, String, String> {

    private String url = "http://10.0.2.2:8080/Debt/get";
    private Participant participant;
    private List<Debt> debtList;
    private Activity activity;
    private ListView listViewGiver;
    private ListView listViewReceiver;
    private GiverAdapter giverAdapter;
    private ReceiverAdapter receiverAdapter;


    @Override
    protected String doInBackground(Object... params) {
        participant = (Participant)params[0];
        activity = (Activity) params[1];
        listViewGiver = (ListView) params[2];
        listViewReceiver = (ListView) params[3];
        return getDebt(participant);
    }

    protected String getDebt(Participant participant)
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

            debtList = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(result);

            for (int i=0; i<jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                debtList.add(mapper.readValue(jsonObject.toString(), Debt.class));
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
        if(debtList.size() != 0) {

            for (Debt debt : debtList)
            {
                if(debt.getReceiver().getUser().getuId() == participant.getUser().getuId())
                {
                    List<Debt> giverList = new ArrayList<>();
                    giverList.add(debt);
                    configureListViewGiver(giverList);

                }

                if(debt.getGiver().getUser().getuId() == participant.getUser().getuId())
                {
                    List<Debt> receiverList = new ArrayList<>();
                    receiverList.add(debt);
                    configureListViewReceiver(receiverList);
                }
            }

        } else
        {
            System.out.println("Aucun achat n'a été effectué");
        }

    }

    private void configureListViewGiver(List<Debt> debtList)
    {
        giverAdapter = new GiverAdapter(activity, R.layout.activity_page_dette, debtList);
        listViewGiver.setAdapter(giverAdapter);
    }

    private void configureListViewReceiver(List<Debt> debtList)
    {
        receiverAdapter = new ReceiverAdapter(activity, R.layout.activity_page_dette, debtList);
        listViewReceiver.setAdapter(receiverAdapter);
    }

}
