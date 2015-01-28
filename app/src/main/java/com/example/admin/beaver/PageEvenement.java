package com.example.admin.beaver;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.Configurations.SessionManager;
import com.example.admin.model.Event;
import com.example.admin.model.Participant;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PageEvenement extends ActionBarActivity {

    private final Logger log = Logger.getLogger(PageEvenement.class);
    Button btnajout;
    private Intent intent ;
    SessionManager session;
    private ObjectMapper mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_evenement);

        session = new SessionManager(getApplicationContext());
        String session_pseudo = session.getSessionPseudo();
        int session_id = session.getSessionID();


        intent = getIntent();
        Event event = new Event();
        mapper = new ObjectMapper();

        try {
            event = mapper.readValue(intent.getStringExtra("selectedEvent"), Event.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView infos_event_title = (TextView) findViewById(R.id.info_event_title);
        TextView infos_event_description = (TextView) findViewById(R.id.info_event_description);

        infos_event_title.setText(infos_event_title.getText().toString() + " : " + event.geteTitle());
        infos_event_description.setText(infos_event_description.getText().toString() + " : " + event.geteDescription());

        log.info("Nom de l'evenement : " + event.geteTitle());

        final List<Participant> participantList = new ArrayList<>();
        participantList.addAll(event.getParticipants());

        ListView list = (ListView) findViewById(R.id.liste_participants);
        ArrayAdapter<Participant> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, participantList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent buyerIntent = new Intent (PageEvenement.this, PageListeAchats.class);

                buyerIntent.putExtras(getIntent());
                try {
                    buyerIntent.putExtra("selectedParticipant", mapper.writeValueAsString(participantList.get(position)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startActivity(buyerIntent);
            }
        });


        btnajout = (Button) findViewById(R.id.btn_ajout);
        btnajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent participantIntent = new Intent(PageEvenement.this, PageAjoutParticipant.class);
                participantIntent.putExtras(getIntent());
                startActivity(participantIntent);
            }
        });

        Button btn_returnHome = (Button) findViewById(R.id.button_returnHome);
        btn_returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent participantIntent = new Intent(PageEvenement.this, PageAccueil.class);
                participantIntent.putExtras(getIntent());
                startActivity(participantIntent);
            }
        });

        Button btn_goToDebt = (Button) findViewById(R.id.btn_goToDebt);
        btn_goToDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent participantIntent = new Intent(PageEvenement.this, PageDette.class);
                participantIntent.putExtras(getIntent());
                startActivity(participantIntent);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page_evenement, menu);
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
