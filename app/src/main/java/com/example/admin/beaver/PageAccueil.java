package com.example.admin.beaver;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;

import com.example.admin.Configurations.GetAsyncTask;
import com.example.admin.model.Event;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class PageAccueil extends Activity {

    ListView listEvent;
    Button btnajout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_accueil);

        btnajout = (Button) findViewById(R.id.btnajoutevent);
        listEvent = (ListView) findViewById(R.id.list);
        ObjectMapper mapper = new ObjectMapper();

        List<Event> eventList = new ArrayList<Event>();
        GetAsyncTask getTask = new GetAsyncTask();
        getTask.setEndUrl("Event/getByUser");
        try {
            JSONArray array = new JSONArray(getTask.execute("Event/getByUser"));
            for(int i=0; i<array.length();i++){
                JSONObject obj= null;
                obj = array.getJSONObject(i);

                try {
                    eventList.add(mapper.readValue(obj.toString(), Event.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(this,
                android.R.layout.simple_list_item_1, eventList);
        listEvent.setAdapter(adapter);


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

                //int id = listEvent.getSelectedItem();
                intent.putExtra("id", id );
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


