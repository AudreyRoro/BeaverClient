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
import android.widget.TextView;

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
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
               try {
                   newUser.setuPassword(SHA1((inscri_password.getText().toString())));
               } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                   e.printStackTrace();
               }
               newUser.setuPseudo(inscri_pseudo.getText().toString());

               JSONObject jsonObject = new JSONObject();
               try {
                   jsonObject.put("uPseudo", newUser.getuPseudo());
                   jsonObject.put("uMail", newUser.getuMail());
                   jsonObject.put("uPassword", newUser.getuPassword());
                   ServerConnection connection = new ServerConnection();
                   //connection.setEndUrl("User/add");
                   connection.execute(jsonObject);


               } catch (JSONException e) {
                   e.printStackTrace();
               }

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


    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
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
