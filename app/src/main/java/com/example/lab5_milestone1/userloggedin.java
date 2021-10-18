package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences sharedPref;
    int isloggedin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userloggedin);

        sharedPref = getApplicationContext().getSharedPreferences("lab5preferences", Context.MODE_PRIVATE);
        isloggedin = sharedPref.getInt("loggedin", 0);

        if(isloggedin == 0){
            logout();
        }

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
                logout();
                return true;
            case R.id.addnote:
                // do something
                System.out.println("Add note");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void logout(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("loggedin", 0);
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}