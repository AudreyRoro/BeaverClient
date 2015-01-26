package com.example.admin.Configurations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.admin.beaver.PageAccueil;

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
 * Created by Marianne on 22/01/15.
 */
public class ServerConnection extends AsyncTask<Object, Integer, JSONObject>{

    private static String url = "http://10.0.2.2:8080/User/login";
    private Context context;

    @Override
    protected JSONObject doInBackground(Object... params) {
        context = (Context) params[1];
        JSONObject jsonObject = (JSONObject) params[0];
        return sendJSONObject(jsonObject, url);

    }



    public static JSONObject sendJSONObject (JSONObject jsonObject, String finalUrl)
    {
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

        //if ((!result.equals("") && !result.equals(null))){
            /*
            * ton pseudo tu dois le récupérer depuis result.
            * */

            //session.createLoginSession(result, pseudo); // entrer en session l'id et le pseudo de l'objet result
            Intent intent = new Intent(context, PageAccueil.class); // création de l'intent
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);// lancement de l'intent
        //}
        //else{

        //}

    }
}