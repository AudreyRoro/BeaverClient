package com.example.admin.beaver;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.Adapters.ConcernedAdapter;
import com.example.admin.Adapters.GiverAdapter;
import com.example.admin.Adapters.ReceiverAdapter;
import com.example.admin.Configurations.HttpGetDebts;
import com.example.admin.Configurations.HttpGetUsers;
import com.example.admin.Configurations.SessionManager;
import com.example.admin.model.Concerned;
import com.example.admin.model.Debt;
import com.example.admin.model.Event;
import com.example.admin.model.Participant;
import com.example.admin.model.User;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PageDette extends ActionBarActivity {

    SessionManager session;
    GiverAdapter giverAdapter;
    ReceiverAdapter receiverAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_dette);

        session = new SessionManager(getApplicationContext());
        String session_pseudo = session.getSessionPseudo();
        int session_id = session.getSessionID();

        ListView giver_listView = (ListView) findViewById(R.id.liste_debts_giver);
        ListView receiver_listView = (ListView) findViewById(R.id.liste_debts_receiver);

        LayoutInflater layoutInflater = getLayoutInflater();
        ViewGroup header_giver = (ViewGroup) layoutInflater.inflate(R.layout.header_list_view, giver_listView, false);
        ViewGroup header_receiver = (ViewGroup) layoutInflater.inflate(R.layout.header_list_view, receiver_listView, false);
        TextView txt_header_receiver = (TextView) header_receiver.findViewById(R.id.header_text);
        TextView txt_header_giver = (TextView) header_giver.findViewById(R.id.header_text);

        txt_header_giver.setText("Ce qu'on vous doit : ");
        txt_header_receiver.setText("Ce que vous devez : ");

        giver_listView.addHeaderView(header_giver);
        receiver_listView.addHeaderView(header_receiver);

        ObjectMapper mapper = new ObjectMapper();
        Participant participant = new Participant();

        try {
            participant.setUser(mapper.readValue(getIntent().getStringExtra("currentUser"), User.class));
            participant.setEvent(mapper.readValue(getIntent().getStringExtra("selectedEvent"), Event.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        new HttpGetDebts().execute(participant, this, giver_listView, receiver_listView);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page_dette, menu);
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
