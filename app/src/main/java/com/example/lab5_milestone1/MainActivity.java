package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    int isloggedin = 0;
    String username = "";

    public void userLogin(View view){

        SharedPreferences.Editor editor = sharedPref.edit();

        EditText usernameField = (EditText) findViewById(R.id.username);
        String username = usernameField.getText().toString();

        editor.putString("username", username);
        editor.putInt("loggedin", 1);
        editor.commit();

        loginTheUser(username);
    }

    public void loginTheUser(String username){
        Intent intent = new Intent(this, userloggedin.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = getApplicationContext().getSharedPreferences("lab5preferences", Context.MODE_PRIVATE);
        isloggedin = sharedPref.getInt("loggedin", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isloggedin == 1){
            username = sharedPref.getString("username", "");
            loginTheUser(username);
        }
    }
}