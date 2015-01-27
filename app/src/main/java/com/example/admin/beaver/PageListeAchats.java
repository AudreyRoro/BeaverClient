package com.example.admin.beaver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.Configurations.HttpGetBuyers;
import com.example.admin.Configurations.HttpGetUsers;
import com.example.admin.Configurations.SessionManager;
import com.example.admin.model.Event;
import com.example.admin.model.Participant;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marianne on 27/01/15.
 */
public class PageListeAchats extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_liste_achats);

        ObjectMapper mapper = new ObjectMapper();
        Participant participant = null;
        try {
            participant = mapper.readValue(getIntent().getStringExtra("selectedParticipant"), Participant.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView infos_purchase_participant = (TextView) findViewById(R.id.infos_purchase_participant);
        infos_purchase_participant.setText("Achats de " + participant.getUser().getuPseudo());

        ListView listView_Buyer = (ListView) findViewById(R.id.listView_Buyer);
        listView_Buyer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        new HttpGetBuyers().execute(participant, this, listView_Buyer);

        Button button_returnEvent = (Button) findViewById(R.id.button_returnEvent);
        button_returnEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageListeAchats.this, PageEvenement.class);
                intent.putExtras(getIntent());
                startActivity(intent);
            }
        });

        Button add_purchase = (Button) findViewById(R.id.btn_ajout);
        add_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageListeAchats.this, PageAchat.class);
                intent.putExtras(getIntent());
                startActivity(intent);
            }
        });




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
