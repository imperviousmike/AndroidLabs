package com.example.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.androidlabs", Context.MODE_PRIVATE);
        String email = prefs.getString("Email", "");
        EditText text = findViewById(R.id.enterEmail);
        text.setText(email);

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(click ->
        {
            Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
            goToProfile.putExtra("EMAIL", text.getText().toString());
            startActivityForResult(goToProfile, 456);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.androidlabs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        EditText email = findViewById(R.id.enterEmail);
        edit.putString("Email", email.getText().toString());
        edit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}

