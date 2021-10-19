package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class userloggedin extends AppCompatActivity {

    TextView displayMsg;
    SharedPreferences sharedPref;
    int isloggedin = 0;
    public static ArrayList<Note> notes;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = getApplicationContext().getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);
        isloggedin = sharedPref.getInt("loggedin", 0);

        if(isloggedin == 0){
            logout();
        }

        setContentView(R.layout.activity_userloggedin);
        displayMsg =(TextView)findViewById(R.id.displaymsg);
        username = sharedPref.getString("username", "");
        displayMsg.setText("Welcome " + username + "!");

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        notes = dbHelper.readNotes(username, context);
        sqLiteDatabase.close();

        ArrayList<String> displayNotes = new ArrayList<String>();
        for (Note note : notes){
            displayNotes.add(String.format("Title:%s\nDate%s", note.getTitle(), note.getDate()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), addNote.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });

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
                startAddNoteActivity();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void startAddNoteActivity(){
        Intent intent = new Intent(this, addNote.class);
        startActivity(intent);
    }

    public void logout(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("loggedin", 0).apply();
        editor.remove("username").apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}