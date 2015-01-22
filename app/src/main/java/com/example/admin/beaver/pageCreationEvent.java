package com.example.admin.beaver;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import com.example.admin.Configurations.ServerConnection;
import com.example.admin.model.Event;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Date;


public class pageCreationEvent extends ActionBarActivity {

    Button btnok;
    EditText titre, description;
    CalendarView calendrierDeb;
    CalendarView calendrierFin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_creation_event);

        btnok=(Button) findViewById(R.id.btncreate);
        titre = (EditText) findViewById(R.id.titre_event);
        description = (EditText) findViewById(R.id.description_event);
        calendrierDeb = (CalendarView) findViewById(R.id.calendrier_debut);
        calendrierFin = (CalendarView) findViewById(R.id.calendrier_fin);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(pageCreationEvent.this, PageAccueil.class);
                startActivity(intent);

                Event event = new Event();
                event.seteTitle(titre.getText().toString());
                event.seteDescription(description.getText().toString());
                event.seteBeginDate(new Timestamp(calendrierDeb.getDate()));
                event.seteEndDate(new Timestamp(calendrierFin.getDate()));
                event.seteCreationDate(new Timestamp(new Date().getTime()));


                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("eTitle", event.geteTitle());
                    jsonObject.put("eDescription", event.geteDescription());
                    jsonObject.put("eBeginDate", event.geteBeginDate());
                    jsonObject.put("eEndDate", event.geteEndDate());
                    jsonObject.put("eCreationDate", event.geteCreationDate());



                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
