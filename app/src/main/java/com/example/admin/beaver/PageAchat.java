package com.example.admin.beaver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.admin.Adapters.ConcernedAdapter;
import com.example.admin.model.Concerned;
import com.example.admin.model.Event;
import com.example.admin.model.Participant;
import com.example.admin.model.User;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marianne on 26/01/15.
 */
public class PageAchat extends Activity {

    Intent parentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_achat);

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

    }

}
