package com.roundbytes.fooable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.roundbytes.fooable.model.SaveSharedPreference;
import com.roundbytes.fooable.R;

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