package com.example.admin.beaver;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;
import android.widget.TextView;


import com.example.admin.Configurations.HttpGetEvents;
import com.example.admin.Configurations.SessionManager;
import com.example.admin.model.Event;
import com.example.admin.model.User;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;


public class PageAccueil extends Activity {

    private final Logger log = Logger.getLogger(PageAccueil.class);
    private ListView listEvent;
    private Button btnajout;
    private TextView connectedUserText;
    private Button btnlogout;
    SessionManager session;
    Intent parentIntent;
    User currentUser ;
    ObjectMapper mapper;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        BasicConfigurator.configure();

       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_accueil);

        mapper = new ObjectMapper();

        parentIntent = getIntent();

        try {
            currentUser = mapper.readValue(parentIntent.getStringExtra("currentUser"), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.listEvent = (ListView) findViewById(R.id.list);
        this.btnajout = (Button) findViewById(R.id.btnajoutevent);
        this.connectedUserText = (TextView) findViewById(R.id.connectedUserTextView);
        this.btnlogout = (Button) findViewById(R.id.btnlogout);

        session = new SessionManager(getApplicationContext());
        String session_pseudo = session.getSessionPseudo();
        int session_id = session.getSessionID();
        connectedUserText.setText(session_pseudo);

        btnajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageAccueil.this, pageCreationEvent.class);
                try {
                    intent.putExtra("currentUser", mapper.writeValueAsString(currentUser));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });

        listEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PageAccueil.this, PageEvenement.class);
                Event selectedEvent = (Event) parent.getItemAtPosition(position);

                String jsonSelectedEvent = "";
                String jsonCurrentUser = "";
                try {
                    jsonSelectedEvent = mapper.writeValueAsString(selectedEvent);
                    jsonCurrentUser = mapper.writeValueAsString(currentUser);
                    log.info("jsonSelectedEvent : " + jsonSelectedEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                log.info("Evenement choisi : " + selectedEvent.geteTitle());

                if(jsonSelectedEvent != "") {
                    intent.putExtra("currentUser", jsonCurrentUser);
                    intent.putExtra("selectedEvent", jsonSelectedEvent);
                    startActivity(intent);
                } else
                {
                    log.info("unable to map event to jsonObject");
                }
            }
        });
        new HttpGetEvents().execute(this.listEvent, this, session.getSessionID());

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page_accueil, menu);
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


