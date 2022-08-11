package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ustart.adapter.CartRecViewAdapter;
import com.example.ustart.adapter.ItemsRecViewAdapter;
import com.example.ustart.database.AppDatabase;
import com.example.ustart.database.entity.CartEntity;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recView;
    private TextView itemsTotalTV, taxTV;

    private double totalCheckOutPrice = 0;
    ArrayList<CartEntity> arrCart = new ArrayList<>();
    AppDatabase db;
    CartRecViewAdapter adapter;


    //public static ArrayList<Items> items = new ArrayList<Items>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //get foodable database
        db = AppDatabase.getAppDatabase(getApplicationContext());
        recView = findViewById(R.id.itemsRecView);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CartRecViewAdapter(arrCart);
        recView.setAdapter(adapter);
        new LoadCartTask().execute();

        itemsTotalTV = findViewById(R.id.itemsTotalTV);
        taxTV = findViewById(R.id.taxTV);

        totalCheckOutPrice = getTotalCheckOutPrice();
        itemsTotalTV.setText(String.valueOf(totalCheckOutPrice));


    }

    private double getTotalCheckOutPrice() {
        double totalCartValue=0;
        for (int i = 0; i < MainActivity.cartList.size(); i++) {
            totalCartValue += MainActivity.cartList.get(i).getdFinalPrice() * MainActivity.cartList.get(i).getqQuantity();
        }
        return  totalCartValue;
    }

    private class LoadCartTask extends AsyncTask<Void,Void, List<CartEntity>> {
        @Override
        protected List<CartEntity> doInBackground(Void... voids) {
            return db.cartDAO().getAllCart();
        }
        @Override
        protected void onPostExecute(List<CartEntity> cartEntities) { //mahasiswas adalah hasil doInBackground
            super.onPostExecute(cartEntities);
            //selesai load data mahasiswa
            arrCart.clear();
            arrCart.addAll(cartEntities);
            adapter.notifyDataSetChanged();
        }
    }


}