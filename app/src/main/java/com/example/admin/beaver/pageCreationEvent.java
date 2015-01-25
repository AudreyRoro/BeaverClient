package com.example.admin.beaver;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.Configurations.ServerConnection;
import com.example.admin.Configurations.SessionManager;
import com.example.admin.model.Event;

import org.json.JSONException;
import org.json.JSONObject;


public class pageCreationEvent extends ActionBarActivity {

    Button btnok;
    EditText titre, description;
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_creation_event);

        session = new SessionManager(getApplicationContext());
        int session_id = session.getSessionID();
        String session_pseudo = session.getSessionPseudo();

        btnok=(Button) findViewById(R.id.btncreate);
        titre = (EditText) findViewById(R.id.titre_event);
        description = (EditText) findViewById(R.id.description_event);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event event = new Event();
                event.seteTitle(titre.getText().toString());
                event.seteDescription(description.getText().toString());

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("eTitle", event.geteTitle());

                    jsonObject.put("eDescription", event.geteDescription());
                    jsonObject.put("eBeginDate", event.geteBeginDate());
                    jsonObject.put("eEndDate", event.geteEndDate());
                    jsonObject.put("eCreationDate", event.geteCreationDate());
                    ServerConnection connection = new ServerConnection();
                    connection.setEndUrl("Event/createEvent");
                    connection.execute(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page_creation_event, menu);
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
