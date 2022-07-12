package com.example.ustart;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.views.ExploreFragment;
import com.example.ustart.views.HomeFragment;
import com.example.ustart.views.MarketFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ImageView toolbarIcon, cartIcon;
    private TextView toolbarTitle;
    public static ArrayList<Items> itemsList = new ArrayList<Items>();
    public static ArrayList<Items> cartList = new ArrayList<Items>();
    private ProgressDialog dialog;
    public static String URL;


//    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        URL = getString(R.string.API_URL);

        //disable application icon from ActionBar
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait ...");
        dialog.setCancelable(true);
        dialog.show();


        //initialize explore items arraylist
        getExploreData();



        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        cartIcon =  findViewById(R.id.shoppingCartIcon);

//        navController = Navigation.findNavController(this, R.id.nav_view);

        toolbarIcon = toolbar.findViewById(R.id.toolbarIcon);
        toolbarTitle = toolbar.findViewById(R.id.toolbarTitle);

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("TAG", String.valueOf(itemsList.size()));
            }
        }, 1000);



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

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
    }

    //DRAWER NAV LISTENER
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
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

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
                            break;
                        case R.id.nav_explore:
                            selectedFragment = new ExploreFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
                            break;
                        case R.id.nav_orders:
                            /*selectedFragment = new StocksFragment();*/
                            Toast.makeText(getApplicationContext(), "Stay tune for the next update", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.nav_store:
                            selectedFragment = new MarketFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
                            break;
                        case R.id.nav_wallet:
                            /*selectedFragment = new StocksFragment();*/
                            Toast.makeText(getApplicationContext(), "Stay tune for the next update", Toast.LENGTH_SHORT).show();
                            break;
                    }
//                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
                    return true;
                }

            };


    private void getExploreData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = URL + "purdData?ipd=";

        for (int i = 1; i <= 3; i++) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + i, null, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONObject response) {
                    //loadingPB.setVisibility(View.GONE);

                    //when response is not null
                    //dismiss progress dialog

                    try {
                        int ipd = Integer.parseInt(response.getString("ipd"));
                        String iVender = response.getString("ivender");
                        String nName = response.getString("nname");
                        double qPrice = Double.parseDouble(response.getString("qprice"));
                        int qQuantity = Integer.parseInt(response.getString("qquantity"));
                        ArrayList<Integer> type = new ArrayList<Integer>();
                        type.add(1);
                        String iType = response.getString("itype");
                        String iUnit = response.getString("iunit");

//                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        LocalDate date2 = LocalDate.of(2022, 7, 6);
//                        LocalDate dInDate = LocalDate.parse(response.getString("dindate"));
//                        LocalDate dLineDate = LocalDate.parse(response.getString("dlinedate"));
//                        String dLineDate = response.getString("dlinedate");
                        double dFinalPrice = Double.parseDouble(response.getString("dfinalprice"));
                        itemsList.add(new Items(ipd, iVender, nName, type, type, qPrice, qQuantity, dFinalPrice, date2, date2));
                        Log.d("TAG", ipd + iVender + nName + " " + nName + " " + " " + " " + qPrice + " " + qQuantity + " " + dFinalPrice + " " + " ");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //loadingPB.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Failed to get market data", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        }
        dialog.dismiss();
    }
}