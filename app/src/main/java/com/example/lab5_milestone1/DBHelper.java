package com.example.lab5_milestone1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DBHelper {
    SQLiteDatabase sqLiteDatabase;

    public DBHelper(SQLiteDatabase sqLiteDatabase){
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public void createTable(){
        sqLiteDatabase.execSQL("Create table if not exists notes " + "(id Integer primary key, username text, date text, title text, src text)");
    }

    public ArrayList<Note> readNotes(String username, Context context){
        createTable();
        Cursor c = sqLiteDatabase.rawQuery(String.format("Select * from notes where username like '%s'", username), null);
        ArrayList<Note> notesList = new ArrayList<>();

        int dateIndex = c.getColumnIndex("date");
        int titleIndex = c.getColumnIndex("title");
        int srcIndex = c.getColumnIndex("src");

        c.moveToFirst();

        while(!c.isAfterLast()){
            String src = c.getString(srcIndex);
            String date = c.getString(dateIndex);
            String title = c.getString(titleIndex);
            String content = readContent(src, context);

            Note note = new Note(date, username, title, content, src);
            notesList.add(note);
            c.moveToNext();
        }

        c.close();
        sqLiteDatabase.close();
        return notesList;
    }

    public String readContent(String filename, Context context) {
        String fileContents = "";
        StringBuilder content = new StringBuilder();
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);

            BufferedReader reader = new BufferedReader(inputStreamReader);

            fileContents = reader.readLine();
            if (fileContents == null) {
                while (fileContents != null) {
                    fileContents = reader.readLine();
                    if (fileContents != null) {
                        break;
                    }
                }
            }

            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContents;
    }

    public void saveNotes(String username, String title, String content, String date, Context context) throws IOException {
        createTable();
        String fileName = title + "*&*" + username + ".txt";
        String fileContents = content;

        try(FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fileOutputStream.write(fileContents.getBytes());
        }

        String query = String.format("Insert into notes (username, date, title, src) Values ('%s', '%s', '%s', '%s');", username, date, title, fileName);
        sqLiteDatabase.execSQL(query);
    }

    public void updateNote(String title, String date, String content, String username, Context context) throws IOException {
        createTable();

        String fileName = title + "*&*" + username + ".txt";
        String fileContents = content;

        try(FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fileOutputStream.write(fileContents.getBytes());
        }

        String query = String.format("Update notes set date = '%s' where title = '%s' and username = '%s';", date, title, username);
        sqLiteDatabase.execSQL(query);
    }
}
