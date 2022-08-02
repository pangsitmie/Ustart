package com.roundbytes.foodrescueseller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class WelcomeActivity extends AppCompatActivity {

    public static String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        URL = getString(R.string.API_URL);


        if(SaveSharedPreference.getUID(WelcomeActivity.this).length() == 0)
        {
            // call Login Activity
        }
        else
        {
            // Stay at the current activity.
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }
}