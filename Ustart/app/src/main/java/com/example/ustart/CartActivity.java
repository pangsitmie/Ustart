package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ustart.adapter.CartRecViewAdapter;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recView;
    private CartRecViewAdapter adapter;
    private TextView itemsTotalTV, taxTV;

    private double totalCheckOutPrice = 0;


    //public static ArrayList<Items> items = new ArrayList<Items>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        itemsTotalTV = findViewById(R.id.itemsTotalTV);
        taxTV = findViewById(R.id.taxTV);

        adapter = new CartRecViewAdapter(getApplicationContext());
        //uses itemlist from main activity
        adapter.setCartList(MainActivity.cartList);

        recView = findViewById(R.id.itemsRecView);
        recView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recView.setAdapter(adapter);

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

}