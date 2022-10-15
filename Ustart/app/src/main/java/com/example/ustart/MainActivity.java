package com.example.ustart;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.model.SaveSharedPreference;
import com.example.ustart.views.ExploreFragment;
import com.example.ustart.views.HomeFragment;
import com.example.ustart.views.MarketFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ImageView toolbarIcon, cartIcon;
    private TextView toolbarTitle;
    public static TextView cartCount;
    public static CardView cartCountCV;

    public static ArrayList<Items> itemsList = new ArrayList<Items>();
    public static ArrayList<Items> cartList = new ArrayList<Items>();
    private ProgressDialog dialog;
    public static String URL;
    //public ArrayList<String> imgURL= new ArrayList<>();

    String TAG = "response";
    public static int totalPage, dataCount, showPage = 5;


    //private FoodableDatabase database;


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
        //getExploreData();
        getDataByType("1", String.valueOf(showPage), "1");

        getCart(SaveSharedPreference.getUID(getApplicationContext()));


        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        cartIcon = findViewById(R.id.shoppingCartIcon);
        cartCountCV = findViewById(R.id.cartCountCV);
        cartCount = findViewById(R.id.cartCount);
//        Initialize database
        //database = FoodableDatabase.getInstance(getApplicationContext());


//        navController = Navigation.findNavController(this, R.id.nav_view);

        toolbarIcon = toolbar.findViewById(R.id.toolbarIcon);
        toolbarTitle = toolbar.findViewById(R.id.toolbarTitle);

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadItemImg();
                loadDesc();
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
        cartCount.setText(String.valueOf(cartList.size()));
        checkCartCount();
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


    private void getDataByType(String iTypeIdx, String showPage, String page) {
        String url = URL + "purdSearch";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    totalPage = Integer.parseInt(jsonObject.getString("totalPage"));
                    dataCount = Integer.parseInt(jsonObject.getString("dataCount"));
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            int ipd = Integer.parseInt(jsonObject1.getString("ipd"));
                            String ivender = jsonObject1.getString("ivender");
                            String nname = jsonObject1.getString("nname");
                            double qprice = Double.parseDouble(jsonObject1.getString("qprice"));
                            int qquantity = Integer.parseInt(jsonObject1.getString("qquantity"));
                            String itype = jsonObject1.getString("itype");
                            String iunit = jsonObject1.getString("iunit");


                            //FIXME: change the date
                            String dindate_str = jsonObject1.getString("dindate");
                            String dlinedate_str = jsonObject1.getString("dlinedate");

                            Date dindate=new SimpleDateFormat("dd/MM/yyyy").parse(dindate_str);
                            Date dlinedate=new SimpleDateFormat("dd/MM/yyyy").parse(dlinedate_str);

                            System.out.println(ipd +" DINDATE: "+dindate_str+"\t\t"+dindate);
                            System.out.println(ipd+" DLINEDATE"+dlinedate_str+"\t\t"+dlinedate);

                            double dfinalprice = Double.parseDouble(jsonObject1.getString("dfinalprice"));

                            ArrayList<String> typeList = new ArrayList<>(Arrays.asList(itype.split(",")));
                            ArrayList<String> unitList = new ArrayList<>(Arrays.asList(iunit.split(",")));


                            LocalDate date2 = LocalDate.of(2022, 7, 6);
                            String dateStr = date2.toString();

//                            ItemEntity itemEntity = new ItemEntity(ipd, ivender, nname, qprice, qquantity, dfinalprice, dateStr, dateStr, "", "");
//                            database.fooadableDao().insertItemEntity(itemEntity);
                            itemsList.add(new Items(ipd, ivender, nname, typeList, unitList, qprice, qquantity, dfinalprice, date2, date2, "", ""));

                            Log.d(TAG, "onResponse: " + ipd + nname);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("itype", iTypeIdx);
                params.put("showPage", showPage);
                params.put("page", page);

                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);

        dialog.dismiss();
    }

    private void loadItemImg() {
        for (int idx = 0; idx < itemsList.size(); idx++) {
            int ipd = itemsList.get(idx).getIpd();

            int ii = idx;
            String url = URL + "purdPic?ipd=" + ipd;
            Log.d("IMG", url);

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
//                        Log.d(TAG, jsonArray.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String eurl = jsonObject.getString("eurl");
                            Log.d("IMG EURL", eurl);
                            itemsList.get(ii).setImgURL(eurl);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(stringRequest);
        }

    }

    private void loadDesc() {
        for (int idx = 0; idx < itemsList.size(); idx++) {
            int ipd = itemsList.get(idx).getIpd();

            int ii = idx;
            String url = URL + "purdCaption?ipd=" + ipd;
            Log.d("IMG", url);

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String ememo = jsonObject.getString("ememo");
                            Log.d("ememo", ememo);
                            itemsList.get(ii).setDesc(ememo);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(stringRequest);
        }

    }

    private void getCart(String iuid) {
        cartList.clear();
        String url = getApplicationContext().getString(R.string.API_URL) + "purdCar?iuid=" + iuid;
        Log.d("IMG", url);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = Integer.parseInt(jsonObject.getString("id"));
                        String iuid = jsonObject.getString("iuid");
                        int ipd = Integer.parseInt(jsonObject.getString("ipd"));
                        int uqquantity = Integer.parseInt(jsonObject.getString("uqquantity"));
                        String uqprice = jsonObject.getString("uqprice");
                        String nname = jsonObject.getString("nname");
                        double qprice = Double.parseDouble(jsonObject.getString("qprice"));
                        int qquantity = Integer.parseInt(jsonObject.getString("qquantity"));

                        String itype = jsonObject.getString("itype");
                        ArrayList<String> iTypeList = new ArrayList<String>(Arrays.asList(itype.split(",")));
                        String ntype = jsonObject.getString("ntype");

                        String iunit = jsonObject.getString("iunit");
                        ArrayList<String> iUnitList = new ArrayList<String>(Arrays.asList(iunit.split(",")));
                        String nunit = jsonObject.getString("nunit");

                        String dindate = jsonObject.getString("dindate");
                        String dlinedate = jsonObject.getString("dlinedate");

                        LocalDate date2 = LocalDate.of(2022, 7, 6);
                        String dateStr = date2.toString();

                        // TODO: 8/22/2022 harus dapetno ivender e
                        cartList.add(new Items(ipd, "ivender", nname, iTypeList, iUnitList, qprice, uqquantity, qprice, date2, date2, "img url", "desc"));
                        checkCartCount();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }


    public static void checkCartCount() {
        if (cartList.size() == 0) {
            cartCountCV.setVisibility(View.GONE);
        } else {
            cartCountCV.setVisibility(View.VISIBLE);
            cartCount.setText(String.valueOf(cartList.size()));
        }
    }
}