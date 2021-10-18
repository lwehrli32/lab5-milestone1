package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;

public class userloggedin extends AppCompatActivity {

    TextView displayMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userloggedin);

        displayMsg =(TextView)findViewById(R.id.displaymsg);
        Intent intent = getIntent();
        String str = intent.getStringExtra("username");
        displayMsg.setText("Welcome " + str + "!");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                System.out.println("Logout");
                return true;
            case R.id.addnote:
                // do something
                System.out.println("Add note");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}