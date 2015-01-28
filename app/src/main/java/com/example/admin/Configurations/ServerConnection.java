package com.example.admin.Configurations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.admin.beaver.PageAccueil;
import com.example.admin.model.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by Marianne on 22/01/15.
 */
public class ServerConnection extends AsyncTask<Object, Integer, JSONObject>{

    private String url = "http://10.0.2.2:8080/";
    //private static String url = "http://192.168.43.148:8080/";
    private static String endUrl;

    public static String getEndUrl() {
        return endUrl;
    }

    public static void setEndUrl(String endUrl) {
        ServerConnection.endUrl = endUrl;
    }

    private Context context;
    private SessionManager session;

    @Override
    protected JSONObject doInBackground(Object... params) {
        context = (Context) params[1];
        session = new SessionManager(context);
        JSONObject jsonObject = (JSONObject) params[0];
        JSONObject res = sendJSONObject(jsonObject, url + endUrl);
        return res;
    }


    public static JSONObject sendJSONObject(JSONObject jsonObject, String finalUrl) {
        HttpClient httpclient = new DefaultHttpClient();
        String temp = null;
        try {

            HttpPost httppost = new HttpPost(finalUrl);

            StringEntity se = new StringEntity(jsonObject.toString());

            httppost.setEntity(se);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");

            HttpResponse response = httpclient.execute(httppost);
            temp = EntityUtils.toString(response.getEntity());


            //Log.i("tag", temp);


        } catch (ClientProtocolException e) {

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            return new JSONObject(temp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(JSONObject result) {
        ObjectMapper mapper = new ObjectMapper();

        if(result!=null) {
            if(!result.toString().equals("{}")) {
                User user = session.createLoginSession(result); // entrer en session l'id et le pseudo de l'objet result
                Toast.makeText(context, "Connection réussie", LENGTH_LONG).show();
                Intent intent = new Intent(context, PageAccueil.class); // création de l'intent

                try {
                    intent.putExtra("currentUser", mapper.writeValueAsString(user));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);// lancement de l'intent
            }
            else{
                Toast.makeText(context, "Utilisateur non reconnu", LENGTH_LONG).show();
            }
        }

    }

}