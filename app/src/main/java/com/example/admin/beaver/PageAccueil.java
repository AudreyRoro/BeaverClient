package com.example.admin.beaver;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;


public class PageAccueil extends ActionBarActivity {

    ListView listEvent;
    Button btnajoutevent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_accueil);

        btnajoutevent = (Button) findViewById(R.id.btnajoutevent);
        listEvent = (ListView) findViewById(R.id.listEvent);

        List<String> liste = new ArrayList<String>();
        liste.add("Soirée du nouvel an");
        liste.add("Crémaillère");

        ArrayAdapter<String> adaptateur = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, liste);
        listEvent.setAdapter(adaptateur);

        btnajoutevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageAccueil.this, pageCreationEvent.class); //permet de passer à la page de création d'un event
                startActivity(intent);
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
