package com.example.ustart;

import static com.example.ustart.MainActivity.checkCartCount;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.adapter.CartRecViewAdapter;
import com.example.ustart.adapter.ItemsRecViewAdapter;
import com.example.ustart.database.AppDatabase;
import com.example.ustart.database.entity.CartEntity;
import com.example.ustart.model.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recView;
    private TextView itemsTotalTV, taxTV;
    private Button checkOutBtn;
    private double totalCheckOutPrice = 0;
    private ArrayList<Items> arrCart = new ArrayList<>();
    //AppDatabase db;
    private CartRecViewAdapter adapter;



    //public static ArrayList<Items> items = new ArrayList<Items>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        arrCart = MainActivity.cartList;
        //get foodable database
//        db = AppDatabase.getAppDatabase(getApplicationContext());


        recView = findViewById(R.id.itemsRecView);
        adapter = new CartRecViewAdapter(getApplicationContext(), arrCart);
        adapter.setCartList(arrCart);
        recView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recView.setAdapter(adapter);
        recView.setHasFixedSize(true);
//        new LoadCartTask().execute();

        itemsTotalTV = findViewById(R.id.itemsTotalTV);
        taxTV = findViewById(R.id.taxTV);
        checkOutBtn = (Button) findViewById(R.id.checkOutBtn);
        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCart();
            }
        });

        totalCheckOutPrice = getTotalCheckOutPrice();
        itemsTotalTV.setText(String.valueOf(totalCheckOutPrice));


        loadItemImg();
        loadDesc();
    }

    private double getTotalCheckOutPrice() {
        double totalCartValue=0;
        for (int i = 0; i < MainActivity.cartList.size(); i++) {
            totalCartValue += MainActivity.cartList.get(i).getdFinalPrice() * MainActivity.cartList.get(i).getqQuantity();
        }
        return  totalCartValue;
    }

//    private class LoadCartTask extends AsyncTask<Void,Void, List<CartEntity>> {
//        @Override
//        protected List<CartEntity> doInBackground(Void... voids) {
//            return db.cartDAO().getAllCart();
//        }
//        @Override
//        protected void onPostExecute(List<CartEntity> cartEntities) { //mahasiswas adalah hasil doInBackground
//            super.onPostExecute(cartEntities);
//            //selesai load data mahasiswa
//            arrCart.clear();
//            arrCart.addAll(cartEntities);
//            adapter.notifyDataSetChanged();
//        }
//    }

    private void loadItemImg() {
        for (int idx = 0; idx < arrCart.size(); idx++) {
            int ipd = arrCart.get(idx).getIpd();

            int ii = idx;
            String url = getApplicationContext().getString(R.string.API_URL) + "purdPic?ipd=" + ipd;
            Log.d("IMG", url);

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                    Log.d("IMG", response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String eurl = jsonObject.getString("eurl");
                            Log.d("IMG EURL", eurl);
                            arrCart.get(ii).setImgURL(eurl);
                        }
//                        adapter.notifyDataSetChanged();
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
        for (int idx = 0; idx < arrCart.size(); idx++) {
            int ipd = arrCart.get(idx).getIpd();

            int ii = idx;
            String url = getApplicationContext().getString(R.string.API_URL) + "purdCaption?ipd=" + ipd;
            Log.d("IMG", url);

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                    Log.d("IMG", response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
//                        Log.d(TAG, jsonArray.toString());
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String ememo = jsonObject.getString("ememo");
                            Log.d("ememo", ememo);
                            arrCart.get(ii).setDesc(ememo);
                        }
                        adapter.notifyDataSetChanged();
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

    private void getCart() {
        String iuid = SaveSharedPreference.getUID(getApplicationContext());
        String url = getApplicationContext().getString(R.string.API_URL) + "purdCar?iuid=" + iuid;
        Log.d("IMG", url);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d("CHECHKOUT", jsonArray.toString());

//                    for (int i=0;i<jsonArray.length();i++){
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        int id = Integer.parseInt(jsonObject.getString("id"));
//                        String iuid = jsonObject.getString("iuid");
//                        int ipd =  Integer.parseInt(jsonObject.getString("ipd"));
//                        int uqquantity = Integer.parseInt(jsonObject.getString("uqquantity"));
//                        String uqprice = jsonObject.getString("uqprice");
//                        String nname = jsonObject.getString("nname");
//                        double qprice = Double.parseDouble(jsonObject.getString("qprice"));
//                        int qquantity = Integer.parseInt(jsonObject.getString("qquantity"));
//
//                        String itype = jsonObject.getString("itype");
//                        ArrayList<String> iTypeList = new ArrayList<String>(Arrays.asList(itype.split(",")));
//                        String ntype = jsonObject.getString("ntype");
//
//                        String iunit = jsonObject.getString("iunit");
//                        ArrayList<String> iUnitList = new ArrayList<String>(Arrays.asList(iunit.split(",")));
//                        String nunit = jsonObject.getString("nunit");
//
//                        String dindate = jsonObject.getString("dindate");
//                        String dlinedate = jsonObject.getString("dlinedate");
//
//                        LocalDate date2 = LocalDate.of(2022, 7, 6);
//                        String dateStr = date2.toString();
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


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}