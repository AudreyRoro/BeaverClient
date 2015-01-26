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
import com.example.admin.model.Buyer;
import com.example.admin.model.Concerned;
import com.example.admin.model.Event;
import com.example.admin.model.Participant;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
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
        Event event = null;
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

        ArrayAdapter<Concerned> participantArrayAdapter = new ConcernedAdapter(this, R.layout.activity_page_achat, concernedList);
        ListView listView = (ListView) findViewById(R.id.listView_Concerned);
        listView.setAdapter(participantArrayAdapter);


        btn_add_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buyer buyer = new Buyer();
                buyer.setbTitle(title_purchase.getText().toString());
                buyer.setbDescription(description_purchase.getText().toString());
                buyer.setbValue(Integer.getInteger(value_purchase.getText().toString()));

                Participant participant = new Participant();


            }
        });

    }

}
