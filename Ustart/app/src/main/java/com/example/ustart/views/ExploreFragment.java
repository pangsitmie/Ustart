package com.example.ustart.views;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ustart.Items;
import com.example.ustart.MainActivity;
import com.example.ustart.adapter.ItemsRecViewAdapter;
import com.example.ustart.R;
import com.example.ustart.data.FoodableDatabase;
import com.example.ustart.data.entity.CartEntity;
import com.example.ustart.data.entity.ItemEntity;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {
    private RecyclerView recView;
    private ItemsRecViewAdapter adapter;
    public static ArrayList<Items> items = new ArrayList<Items>();
    private FoodableDatabase database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recView = view.findViewById(R.id.itemsRecView);
        database = FoodableDatabase.getInstance(getContext());

        adapter = new ItemsRecViewAdapter(getActivity());
        //uses itemlist from main activity
        //adapter.setItemsList(MainActivity.itemsList);
        adapter.setItemsList(database.fooadableDao().getAllItems());

        for (int i=0;i< MainActivity.itemsList.size();i++)
        {
            Log.d("explore", MainActivity.itemsList.get(i).getImgURL());
        }


        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recView.setAdapter(adapter);

    }
//    @Override
//    public void onItemClicked(ItemEntity data) {
//        CartEntity cartEntity = new CartEntity("masukkan parameter")
//    }
}