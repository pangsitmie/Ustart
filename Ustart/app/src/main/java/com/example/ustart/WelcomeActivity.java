package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.ustart.R;
import com.example.ustart.model.SaveSharedPreference;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

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