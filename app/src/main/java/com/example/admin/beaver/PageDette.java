package com.example.admin.beaver;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.admin.Adapters.ConcernedAdapter;
import com.example.admin.Configurations.SessionManager;
import com.example.admin.model.Concerned;
import com.example.admin.model.Participant;
import com.example.admin.model.User;

import java.util.ArrayList;
import java.util.List;


public class PageDette extends ActionBarActivity {

    SessionManager session;

    
    Intent parentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_dette);

        parentIntent = getIntent();

        session = new SessionManager(getApplicationContext());
        String session_pseudo = session.getSessionPseudo();
        int session_id = session.getSessionID();

        /*Button button = (Button) findViewById(R.id.add_debt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageDette.this, PageAchat.class);
                intent.putExtras(parentIntent);
                startActivity(intent);
            }
        });
*/

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
