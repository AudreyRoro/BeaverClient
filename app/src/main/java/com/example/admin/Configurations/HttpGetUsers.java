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
 * Created by admin on 24/01/15.
 */
public class HttpGetUsers extends AsyncTask<Object, Void, String> {

    private String url = "http://10.0.2.2:8080/User/getUsers";
    private String result ;
    private Activity activity;
    private List<User> userList;
    private final Logger log = Logger.getLogger(HttpGetUsers.class);
    @Override
    protected String doInBackground(Object... params) {
        this.userList = (List<User>) params[0];
        this.activity = (Activity) params[1];
        return getInputStreamFromUrl(url);
    }

    final String getInputStreamFromUrl(String url) {
        BufferedReader inStream = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpRequest = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpRequest);
            inStream = new BufferedReader( new InputStreamReader( response.getEntity().getContent()));


            String line = "";
            result = "";
            while((line = inStream.readLine()) != null)
                result += line;
            inStream.close();

            userList = new ArrayList<User>();
            JSONArray jsonArray = new JSONArray(result);
            ObjectMapper mapper = new ObjectMapper();
            for (int i=0; i<jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                userList.add(mapper.readValue(jsonObject.toString(), User.class));
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
        log.info("Users number : " + userList.size());

    }
}
