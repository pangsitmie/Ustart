package com.example.ustart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);


        /*Toolbar*/
        //setSupportActionBar(toolbar);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        //drawer.addDrawerListener(toggle);
        //toggle.syncState();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "icon clicked", Toast.LENGTH_SHORT).show();
                drawer.openDrawer(GravityCompat.START);
            }
        });






    }
    //DRAWER NAV LISTENER
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_profile:
                Toast.makeText(this, "Stay tune for the next update!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_notification:
                Toast.makeText(this, "Stay tune for the next update!", Toast.LENGTH_SHORT).show();
//                Intent intent2 = new Intent(getApplicationContext(), ActivitySecurity.class);
//                startActivity(intent2);
                break;
            case R.id.nav_purchase:
                Toast.makeText(this, "Stay tune for the next update!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_setting:
                Toast.makeText(this, "Stay tune for the next update!", Toast.LENGTH_SHORT).show();
//                Intent intent4  = new Intent(getApplicationContext(), ActivityDonate.class);
//                startActivity(intent4);
                break;
            case R.id.nav_share:
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Your Subject");
                    String sAux = "\nCheck out this amazing application\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=com.roundbytes.myportfolio \n"; // here define package name of you app
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch (Exception e) {
                    Log.e(">>>", "Error: " + e);
                }
                break;
            case R.id.nav_logout:
//                FirebaseAuth.getInstance().signOut();
//                Intent intent1 = new Intent(getApplicationContext(), WelcomeActivity.class);
//                startActivity(intent1);
                Toast.makeText(this, "Log Out Successful", Toast.LENGTH_SHORT).show();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //bottom nav listener
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            /*selectedFragment = new StocksFragment();*/
                            Toast.makeText(getApplicationContext(), "Stay tune for the next update", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.nav_search:
//                            selectedFragment = new CryptoFragment();
//                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                            break;
                        case R.id.nav_orders:
                            /*selectedFragment = new StocksFragment();*/
                            Toast.makeText(getApplicationContext(), "Stay tune for the next update", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.nav_store:
                            /*selectedFragment = new StocksFragment();*/
                            Toast.makeText(getApplicationContext(), "Stay tune for the next update", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.nav_wallet:
                            /*selectedFragment = new StocksFragment();*/
                            Toast.makeText(getApplicationContext(), "Stay tune for the next update", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();*/
                    return true;
                }

            };
}