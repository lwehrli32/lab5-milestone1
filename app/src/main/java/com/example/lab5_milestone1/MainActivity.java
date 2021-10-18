package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {

    public void userLogin(View view){
        EditText usernameField = (EditText) findViewById(R.id.username);
        String username = usernameField.getText().toString();
        loginTheUser(username);
    }

    public void loginTheUser(String username){
        Intent intent = new Intent(this, userloggedin.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}