package com.example.admin.Configurations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.admin.beaver.PageAccueil;
import com.example.admin.beaver.PageConnexion;
import com.example.admin.model.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by ACER on 26/01/2015.
 */
public class HttpPostLogin extends AsyncTask<Object, Integer, JSONObject> {

    private static String url = "http://10.0.2.2:8080/User/login";
    //private static String url = "http://192.168.43.148:8080/User/login";
    private Context context;
    SessionManager session;

    @Override
    protected JSONObject doInBackground(Object... params) {
        context = (Context) params[1];
        session = new SessionManager(context);
        JSONObject jsonObject = (JSONObject) params[0];
        return sendJSONObject(jsonObject, url);

    }



    public static JSONObject sendJSONObject (JSONObject jsonObject, String finalUrl)
    {
        HttpClient httpclient = new DefaultHttpClient();
        JSONObject jsonObj=new JSONObject();
        try {

            HttpPost httppost = new HttpPost(finalUrl);

            StringEntity se = new StringEntity(jsonObject.toString());

            httppost.setEntity(se);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");

            HttpResponse response = httpclient.execute(httppost);
            String temp = EntityUtils.toString(response.getEntity());
            try {
                jsonObj = new JSONObject(temp);
            }catch (JSONException e){}



            //Log.i("tag", temp);


        } catch (ClientProtocolException e) {

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return jsonObj;
    }

    protected void onPostExecute(JSONObject result) {

        if(result!=null) {
            if(!result.toString().equals("{}")) {
                session.createLoginSession(result); // entrer en session l'id et le pseudo de l'objet result
                Toast.makeText(context, "Connection réussie", LENGTH_LONG).show();
                Intent intent = new Intent(context, PageAccueil.class); // création de l'intent
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);// lancement de l'intent
            }
            else{
                Toast.makeText(context, "Utilisateur non reconnu", LENGTH_LONG).show();
            }
        }

    }

}