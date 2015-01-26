package com.example.admin.beaver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.Configurations.ServerConnection;
import com.example.admin.Configurations.SessionManager;
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
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.widget.Toast.LENGTH_LONG;


public class PageConnexion extends ActionBarActivity {

    Button btnok;
    Button btn_inscription;
    EditText connect_pseudo;
    EditText connect_password;
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_connexion);
        session = new SessionManager(getApplicationContext());
        btnok=(Button) findViewById(R.id.btnok);
        btn_inscription=(Button) findViewById(R.id.btn_inscription);
        connect_password=(EditText) findViewById(R.id.connect_password);
        connect_pseudo=(EditText) findViewById(R.id.connect_pseudo);


        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PageConnexion.this, PageAccueil.class);

                String pseudo = connect_pseudo.getText().toString();
                String password = "";
                try {
                    password = SHA1(connect_password.getText().toString());
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("pseudo", pseudo);
                    jsonObject.put("password", password);
                    ServerConnection connection = new ServerConnection();
                    connection.setEndUrl("User/login");
                    //connection.execute(jsonObject);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                /* Affichage "connexion réussie" ou "échec" */
                if ((!pseudo.equals("")) && (!connect_password.getText().toString().equals(""))) {
                   Toast.makeText(getApplicationContext(), "Connexion réussie", LENGTH_LONG).show();
                   session.createLoginSession(1, pseudo);

                   startActivity(intent);
               }
                   else{
                    Toast.makeText(getApplicationContext(),"Identifiants invalides",LENGTH_LONG).show();
                }


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

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                }
                else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }


    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
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
