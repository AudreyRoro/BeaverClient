package com.example.admin.beaver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.admin.Adapters.ConcernedAdapter;
import com.example.admin.Configurations.HttpPostBuyer;
import com.example.admin.model.Buyer;
import com.example.admin.model.Concerned;
import com.example.admin.model.Event;
import com.example.admin.model.Participant;
import com.example.admin.model.User;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Marianne on 26/01/15.
 */
public class PageAchat extends ActionBarActivity {

    Intent parentIntent;
    Button btn_add_purchase ;
    EditText title_purchase ;
    EditText description_purchase;
    EditText value_purchase ;
    ConcernedAdapter participantArrayAdapter;
    Event event ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_achat);

        /* Initialize elements */
        btn_add_purchase = (Button) findViewById(R.id.add_purchase);
        title_purchase = (EditText) findViewById(R.id.title_purchase);
        description_purchase = (EditText) findViewById(R.id.description_purchase);
        value_purchase = (EditText) findViewById(R.id.value_purchase);

        /* Get the selectedEvent */
        parentIntent = getIntent();
        ObjectMapper mapper = new ObjectMapper();
        try {
            event = (Event) mapper.readValue(parentIntent.getStringExtra("selectedEvent"), Event.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Concerned> concernedList = new ArrayList<>();

        for (Participant participant : event.getParticipants())
        {
            Concerned concerned = new Concerned();
            concerned.setParticipant(participant);
            concerned.setChecked(false);
            concernedList.add(concerned);
        }

        participantArrayAdapter = new ConcernedAdapter(this, R.layout.activity_page_achat, concernedList);
        ListView listView = (ListView) findViewById(R.id.listView_Concerned);
        listView.setAdapter(participantArrayAdapter);

        btn_add_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* User (moi) à modifier */
                User user = new User();
                user.setuId(2);
                user.setuPseudo("marianne");
                user.setuPassword("a94a8fe5ccb19ba61c4c0873d391e987982fbbd3");
                user.setuMail("marianne@koehl.com");

                Participant participant = new Participant();
                participant.setUser(user);
                participant.setEvent(event);

                Buyer buyer = new Buyer();
                buyer.setbTitle(title_purchase.getText().toString());
                buyer.setbDescription(description_purchase.getText().toString());
                buyer.setbValue(Integer.parseInt(value_purchase.getText().toString()));
                buyer.setParticipant(participant);


                List<Concerned> listConcernedForBuyer = new ArrayList<>();

                List<Concerned> list = participantArrayAdapter.getConcernedList();
                for (Concerned concerned : list)
                {
                    if(concerned.isChecked())
                        listConcernedForBuyer.add(concerned);

                }

                buyer.setConcernedSet(new HashSet<>(listConcernedForBuyer));

                new HttpPostBuyer().execute(buyer, getApplicationContext());

            }
        });

    }

}
