package com.example.admin.Configurations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.admin.beaver.PageAccueil;
import com.example.admin.beaver.PageConnexion;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by ACER on 26/01/2015.
 */
public class HttpPostLogin extends AsyncTask<JSONObject, Integer, JSONObject> {

    private static String url = "http://192.168.43.148:8080/";
    private static String endUrl;

    public static String getEndUrl() {
        return endUrl;
    }

    public static void setEndUrl(String endUrl) {
        HttpPostLogin.endUrl = endUrl;
    }

    @Override
    protected JSONObject doInBackground(JSONObject... params) {
        JSONObject res = sendJSONObject(params[0], url + endUrl);
        return res;
    }


    public static JSONObject sendJSONObject(JSONObject jsonObject, String finalUrl) {
        HttpClient httpclient = new DefaultHttpClient();

        try {

            HttpPost httppost = new HttpPost(finalUrl);

            StringEntity se = new StringEntity(jsonObject.toString());

            httppost.setEntity(se);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");

            HttpResponse response = httpclient.execute(httppost);
            String temp = EntityUtils.toString(response.getEntity());


            //Log.i("tag", temp);


        } catch (ClientProtocolException e) {

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    protected void onPostExecute(JSONObject result) {
        //System.out.println("result : " + result.toString());

        if ((!result.equals("") && !result.equals(null))){
            session.createLoginSession(result, pseudo); // entrer en session l'id et le pseudo de l'objet result
            Intent intent = new Intent(getApplicationContext(), PageAccueil.class); // création de l'intent
            startActivity(intent); // lancement de l'intent
        }
        else{

        }
    }
}
