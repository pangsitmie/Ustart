package com.example.ustart.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ustart.Items;
import com.example.ustart.MainActivity;
import com.example.ustart.R;
import com.example.ustart.adapter.SwipeAdapter;
import com.yalantis.library.Koloda;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private SwipeAdapter adapter;
    public ArrayList<Items> itemsList = new ArrayList<Items>();
    Koloda koloda;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        koloda = view.findViewById(R.id.koloda);
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = new SwipeAdapter(getActivity(), MainActivity.itemsList);
                koloda.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }, 2000);

        koloda.onClickLeft();




    }
}