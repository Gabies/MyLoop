package com.example.myloop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Logout button clicked");
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();

                goLogin();
            }
        });
    }

    private void goLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}