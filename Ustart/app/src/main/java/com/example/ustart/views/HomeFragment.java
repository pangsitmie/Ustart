package com.example.ustart.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ustart.R;
import com.example.ustart.adapter.SwipeAdapter;
import com.yalantis.library.Koloda;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private SwipeAdapter adapter;
    private List<Integer> list;
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

        list = new ArrayList<>();
        adapter = new SwipeAdapter(getActivity(), list);
        koloda.setAdapter(adapter);
    }
}