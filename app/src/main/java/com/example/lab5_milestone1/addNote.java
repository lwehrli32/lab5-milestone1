package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class addNote extends AppCompatActivity {

    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid", -1);

        if (noteid != -1){
            Note note = userloggedin.notes.get(noteid);
            String noteContent = note.getContent();
            EditText noteField = (EditText) findViewById(R.id.note);
            noteField.setText(noteContent);
        }
    }

    public void saveNote(View view){

        TextView noteField = (TextView) findViewById(R.id.note);
        String content = noteField.getText().toString();

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "");
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        if(noteid == -1){
            title = "NOTE_" + (userloggedin.notes.size() + 1);
            try {
                Context newcontext = getApplicationContext();
                dbHelper.saveNotes(username, title, content, date, newcontext);
            }catch(IOException e){
                System.out.println("Cannot save note");
            }
        }else{
            title = "NOTE_" + (noteid + 1);
            try {
                Context newcontext = getApplicationContext();
                dbHelper.updateNote(title, date, content, username, newcontext);
            }catch(IOException e){
                System.out.println("Cannot save note");
            }
        }

        sqLiteDatabase.close();

        gotoSecondActivity();
    }

    public void gotoSecondActivity(){
        Intent intent = new Intent(this, userloggedin.class);
        startActivity(intent);
    }
}