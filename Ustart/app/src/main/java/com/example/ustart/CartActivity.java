package com.example.ustart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ustart.adapter.CartRecViewAdapter;
import com.example.ustart.adapter.ItemsRecViewAdapter;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recView;
    private CartRecViewAdapter adapter;
    public static ArrayList<Items> items = new ArrayList<Items>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        adapter = new CartRecViewAdapter(getApplicationContext());
        //uses itemlist from main activity
        adapter.setItemsList(MainActivity.itemsList);

        recView = findViewById(R.id.itemsRecView);
        recView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recView.setAdapter(adapter);
    }
}