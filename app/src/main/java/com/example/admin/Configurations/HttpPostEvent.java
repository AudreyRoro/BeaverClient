package com.example.admin.Configurations;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.admin.beaver.PageAccueil;
import com.example.admin.model.Event;
import com.example.admin.model.Participant;
import com.example.admin.model.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by Marianne on 27/01/15.
 */
public class HttpPostEvent extends AsyncTask<Object, Integer, String>{

    private String url = "http://10.0.2.2:8080/Event/createEvent";
    private Context context;
    private User user;

    @Override
    protected String doInBackground(Object... params) {
        Event event = (Event) params[0];
        user = (User) params[1];
        context = (Context) params[2];

        String res = sendJSONObject(event, user, url);
        return res;
    }


    public static String sendJSONObject(Event event, User user, String finalUrl) {
        HttpClient httpclient = new DefaultHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        try {

            HttpPost httppost = new HttpPost(finalUrl);

            StringEntity se = null;

            try {
                se = new StringEntity(mapper.writeValueAsString(event));
            } catch (IOException e) {
                e.printStackTrace();
            }

            httppost.setEntity(se);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");

            HttpResponse response = httpclient.execute(httppost);
            String temp = EntityUtils.toString(response.getEntity());

            if (!temp.isEmpty())
            {
                return temp;
            }

        } catch (ClientProtocolException e) {

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    protected void onPostExecute(String result) {



        if(result!=null) {
            if(!result.isEmpty())
            {
                ObjectMapper mapper = new ObjectMapper();

                Event event = null;
                try {
                    event = mapper.readValue(result, Event.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (event.geteId() != 0) {
                    Participant participant = new Participant();
                    participant.setEvent(event);
                    participant.setUser(user);

                    new HttpPostParticipant().execute(participant, "EVENT");
                } else {
                    Toast.makeText(context, "Evenement ou participant non ajouté", LENGTH_LONG).show();
                }
                Intent intent = new Intent(context, PageAccueil.class); // création de l'intent
                try {
                    intent.putExtra("currentUser", mapper.writeValueAsString(user));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (intent.hasExtra("currentUser")) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);// lancement de l'intent
                }
            }
            else{
                Toast.makeText(context, "Utilisateur non reconnu", LENGTH_LONG).show();
            }
        }

    }
}