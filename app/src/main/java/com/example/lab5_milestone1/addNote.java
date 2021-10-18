package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class addNote extends AppCompatActivity {

    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        if (noteid != -1){
            Note note = userloggedin.notes.get(noteid);
            String noteContent = note.getContent();
            EditText noteField = (EditText) findViewById(R.id.note);
            noteField.setText(noteContent);
        }
    }

    public void saveNote(){

        TextView noteField = (TextView) findViewById(R.id.note);
        String content = noteField.getText().toString();

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "");
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if(noteid != -1){
            title = "NOTE_" + (noteid + 1);
            DBHelper.updateNote(title, date, content, username);
        }else{

        }
    }
}