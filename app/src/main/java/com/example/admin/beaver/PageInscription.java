package com.example.admin.beaver;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.Configurations.ServerConnection;
import com.example.admin.model.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PageInscription extends ActionBarActivity {

    Button btnsave;
    EditText inscri_pseudo;
    EditText inscri_mail;
    EditText inscri_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_inscription);

        btnsave=(Button) findViewById(R.id.btnsave);
        inscri_pseudo= (EditText) findViewById(R.id.inscri_pseudo);
        inscri_mail= (EditText) findViewById(R.id.inscri_mail);
        inscri_password= (EditText) findViewById(R.id.inscri_password);


       btnsave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(PageInscription.this, PageConnexion.class); //lancer page connexion

               User newUser = new User();
               newUser.setuMail(inscri_mail.getText().toString());
               newUser.setuPassword(inscri_password.getText().toString());
               newUser.setuPseudo(inscri_pseudo.getText().toString());

               JSONObject jsonObject = new JSONObject();
               try {
                   jsonObject.put("uPseudo", newUser.getuPseudo());
                   jsonObject.put("uMail", newUser.getuMail());
                   jsonObject.put("uPassword", newUser.getuPassword());
                   ServerConnection connection = new ServerConnection();
                   connection.sendJSONObject(jsonObject, "");


               } catch (JSONException e) {
                   e.printStackTrace();
               }



               startActivity(intent);

           }
       });
    }










    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page_inscription, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
