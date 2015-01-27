package com.example.admin.beaver;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.Configurations.HttpGetUsers;
import com.example.admin.Configurations.SessionManager;
import com.example.admin.model.Event;
import com.example.admin.model.Participant;

import org.codehaus.jackson.map.ObjectMapper;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PageAjoutParticipant extends ActionBarActivity {

    Button btn_recherche;
    EditText participant;
    TextView reponse;
    Event event;
    ListView listView;
    Activity activity;
    SessionManager session;
    List<Participant> participantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_ajout_participant);

        session = new SessionManager(getApplicationContext());
        String session_pseudo = session.getSessionPseudo();
        int session_id = session.getSessionID();

        btn_recherche = (Button) findViewById(R.id.button_recherche);
        participant = (EditText) findViewById(R.id.participant);
        reponse = (TextView) findViewById(R.id.reponse);
        listView = (ListView) findViewById(R.id.list_addParticipants);
        activity = this;

        ObjectMapper objectMapper = new ObjectMapper();
        Intent intent = getIntent();

        try {
            event = objectMapper.readValue(intent.getStringExtra("selectedEvent"), Event.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        participantList = new ArrayList<>();
        participantList.addAll(event.getParticipants());

        ArrayAdapter<Participant> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, participantList);
        listView.setAdapter(adapter);


        btn_recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pseudoNewParticipant = participant.getText().toString();

                // Check if participant is already added
                boolean alreadyParticipant = false;
                if (! event.equals(null))
                    for (Participant participant : participantList) {
                        if (participant.getUser().getuPseudo().equals(pseudoNewParticipant))
                            alreadyParticipant = true;
                    }

                if (!alreadyParticipant)
                    rechercheParticipant(pseudoNewParticipant, reponse, event, activity, listView);
                else
                    reponse.setText("L'utilisateur participe déjà à l'évenement");
            }
        });

        Button button_return = (Button) findViewById(R.id.button_returnEvent);
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageAjoutParticipant.this, PageEvenement.class);

                intent.putExtras(getIntent());
                startActivity(intent);
            }
        });

    }

    public void rechercheParticipant(String user, TextView textView, Event event, Activity activity, ListView listView){
        new HttpGetUsers().execute(user, textView, event, activity, listView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page_ajout_participant, menu);
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
