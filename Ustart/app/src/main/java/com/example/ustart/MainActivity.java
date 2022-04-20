package com.example.ustart;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private ImageView toolbarIcon;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //disable application icon from ActionBar


        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);

        toolbarIcon = toolbar.findViewById(R.id.toolbarIcon);
        toolbarTitle = toolbar.findViewById(R.id.toolbarTitle);
        /*Toolbar*/
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setSupportActionBar(toolbar);


//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        //drawer.addDrawerListener(toggle);
        //toggle.syncState();


        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "icon clicked", Toast.LENGTH_SHORT).show();
                drawer.openDrawer(GravityCompat.START);
            }
        });

        toolbarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "title clicked", Toast.LENGTH_SHORT).show();
            }
        });





    }
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        return false;
//    }
}