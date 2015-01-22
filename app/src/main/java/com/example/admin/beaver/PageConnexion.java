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


public class PageConnexion extends ActionBarActivity {

    Button btnok;
    Button btn_inscription;
    EditText connect_pseudo;
    EditText connect_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_connexion);

        btnok=(Button) findViewById(R.id.btnok);
        btn_inscription=(Button) findViewById(R.id.btn_inscription);
        connect_password=(EditText) findViewById(R.id.connect_password);
        connect_pseudo=(EditText) findViewById(R.id.connect_pseudo);


        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PageConnexion.this, PageAccueil.class);

                User user=new User();
                user.setuPseudo(connect_pseudo.getText().toString());
                user.setuPassword(connect_password.getText().toString());

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("uPseudo", user.getuPseudo());
                    jsonObject.put("uPassword", user.getuPassword());


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                startActivity(intent);
            }
        });


        btn_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PageConnexion.this, PageInscription.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page_connexion, menu);
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
