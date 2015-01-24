package com.example.admin.beaver;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;


import com.example.admin.Configurations.HttpGetEvents;
import com.example.admin.model.Event;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;


public class PageAccueil extends Activity {

    private final Logger log = Logger.getLogger(PageAccueil.class);
    private ListView listEvent;
    private Button btnajout;
    private String className ;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        BasicConfigurator.configure();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_accueil);
        this.listEvent = (ListView) findViewById(R.id.list);
        this.btnajout = (Button) findViewById(R.id.btnajoutevent);

        btnajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageAccueil.this, pageCreationEvent.class);
                startActivity(intent);
            }
        });

        listEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PageAccueil.this, PageEvenement.class);
                Event selectedEvent = (Event) parent.getItemAtPosition(position);
                ObjectMapper mapper = new ObjectMapper();
                String jsonSelectedEvent = "";
                try {
                    jsonSelectedEvent = mapper.writeValueAsString(selectedEvent);
                    log.info("jsonSelectedEvent : " + jsonSelectedEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                log.info("Evenement choisi : " + selectedEvent.geteTitle());

                if(jsonSelectedEvent != "") {
                    intent.putExtra("selectedEvent", jsonSelectedEvent);
                    startActivity(intent);
                } else
                {
                    log.info("unable to map event to jsonObject");
                }
            }
        });
        new HttpGetEvents().execute(this.listEvent, this);
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


