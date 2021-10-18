package com.example.lab5_milestone1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBHelper {
    SQLiteDatabase sqLiteDatabase;

    public DBHelper(SQLiteDatabase sqLiteDatabase){
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public void createTable(){
        sqLiteDatabase.execSQL("Create table if not exists notes " + "(id Integer primary key, username text, title text, content text, src text)");
    }

    public ArrayList<Note> readNotes(String username){
        createTable();
        Cursor c = sqLiteDatabase.rawQuery(String.format("Select * from notes where username like '%s'", username), null);

        int dateIndex = c.getColumnIndex("date");
        int titleIndex = c.getColumnIndex("title");
        int contentIndex = c.getColumnIndex("content");

        c.moveToFirst();

        ArrayList<Note> notesList = new ArrayList<>();

        while(!c.isAfterLast()){
            String date = c.getString(dateIndex);
            String title = c.getString(titleIndex);
            String content = c.getString(contentIndex);

            Note note = new Note(date, username, title, content);
            notesList.add(note);
            c.moveToNext();
        }

        c.close();
        sqLiteDatabase.close();
        return notesList;
    }

    public void saveNotes(String username, String title, String content, String date){
        createTable();
        sqLiteDatabase.execSQL(String.format("Insert into notes (username, date, title, content) Values ('%s', '%s', '%s', '%s'", username, date, title, content));
    }

    public void updateNote(String title, String date, String content, String username){
        createTable();
        sqLiteDatabase.execSQL(String.format("Update notes set content = '%s', date = '%s' where title = '%s' and username = '%s'", content, date, title, username));
    }
}
