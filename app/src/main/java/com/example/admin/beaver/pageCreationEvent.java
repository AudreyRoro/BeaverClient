package com.example.admin.beaver;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.Configurations.HttpPostEvent;
import com.example.admin.Configurations.ServerConnection;
import com.example.admin.Configurations.SessionManager;
import com.example.admin.model.Event;
import com.example.admin.model.User;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class pageCreationEvent extends ActionBarActivity {

    Button btnok;
    EditText titre, description;
    TextView connectedUserText;
    SessionManager session;
    Intent parentIntent ;
    User currentUser;

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
        connectedUserText = (TextView) findViewById(R.id.connectedUserTextView);
        connectedUserText.setText(session_pseudo);

        parentIntent = getIntent();

        ObjectMapper mapper = new ObjectMapper();
        try {
            currentUser = mapper.readValue(parentIntent.getStringExtra("currentUser"), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event event = new Event();
                event.seteTitle(titre.getText().toString());
                event.seteDescription(description.getText().toString());

                    HttpPostEvent httpPostEvent = new HttpPostEvent();
                    httpPostEvent.execute(event, currentUser, getApplicationContext());
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
