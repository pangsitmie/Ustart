package com.roundbytes.foodrescueseller.views;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.roundbytes.foodrescueseller.Items;
import com.roundbytes.foodrescueseller.R;
import com.roundbytes.foodrescueseller.adapter.ItemsRecViewAdapter;

import java.time.LocalDate;
import java.util.ArrayList;

public class ExploreFragment extends Fragment {
    private RecyclerView recView;
    private ItemsRecViewAdapter adapter;
    private ArrayList<Items> itemsList = new ArrayList<Items>();

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

        LocalDate date1 = LocalDate.of(2022, 6, 15);
        LocalDate date2 = LocalDate.of(2022, 7, 6);


        itemsList.add(new Items("Milk", "a premium milk",
                "https://firebasestorage.googleapis.com/v0/b/ustart-2304.appspot.com/o/items%2Fmilk.jpg?alt=media&token=644078bc-3c8f-41e0-a555-d2d06abb0624",
                20.0,10.0, date1));
        itemsList.add(new Items("Banana", "a premium banana you will never regret buy it",
                "https://firebasestorage.googleapis.com/v0/b/ustart-2304.appspot.com/o/items%2Fbanana.jpg?alt=media&token=21538bd4-6656-4669-8f6e-c663bd46e6b5",
                50.0,20.0, date2));
        itemsList.add(new Items("Indomie", "a premium indomie",
                "https://firebasestorage.googleapis.com/v0/b/ustart-2304.appspot.com/o/items%2Findomie.png?alt=media&token=a45510c4-b2ac-4d6d-9205-9263aac8a928",
                8.0,4.0, date1));
        itemsList.add(new Items("Oreo", "a premium oreo",
                "https://firebasestorage.googleapis.com/v0/b/ustart-2304.appspot.com/o/items%2Foreo.png?alt=media&token=90343958-b601-4cbc-8624-ebf538046fe1",
                30.0,15.0, date2));
        itemsList.add(new Items("Nutella", "a premium Nutella",
                "https://firebasestorage.googleapis.com/v0/b/ustart-2304.appspot.com/o/items%2Fnutella.jpg?alt=media&token=bee5da6f-46e6-49fa-be97-eeecb79096b8",
                60.0,20.0, date1));
        itemsList.add(new Items("Bread", "a premium Bread",
                "https://firebasestorage.googleapis.com/v0/b/ustart-2304.appspot.com/o/items%2Fbread.jpg?alt=media&token=d43f01c5-fdc8-4c59-bec5-a3c45cff1b3c",
                50.0,25.0, date1));

        adapter = new ItemsRecViewAdapter(getActivity());
        adapter.setItemsList(itemsList);

        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recView.setAdapter(adapter);

    }
}