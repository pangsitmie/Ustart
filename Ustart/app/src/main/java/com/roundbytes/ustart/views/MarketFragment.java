package com.roundbytes.ustart.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roundbytes.ustart.Markets;
import com.roundbytes.ustart.adapter.MarketsRecViewAdapter;
import com.roundbytes.ustart.R;
import java.util.ArrayList;

public class MarketFragment extends Fragment {
    private RecyclerView recView;
    private MarketsRecViewAdapter adapter;
    private ArrayList<Markets> marketsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_market, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recView = view.findViewById(R.id.marketRecView);//


        marketsList.add(new Markets("Carrefour", "https://firebasestorage.googleapis.com/v0/b/ustart-2304.appspot.com/o/carrefour.png?alt=media&token=4a48ee8f-e255-4ce4-a447-37c936ea31ee"));
        marketsList.add(new Markets("RT Mart", "https://firebasestorage.googleapis.com/v0/b/ustart-2304.appspot.com/o/rt_mart.png?alt=media&token=2b3b79d0-ad71-4c14-a3b8-03b12052f736"));
        marketsList.add(new Markets("PX Mart", "https://firebasestorage.googleapis.com/v0/b/ustart-2304.appspot.com/o/px_mart.jpeg?alt=media&token=42ad9709-e7ed-487c-8c20-c0f3d6f2f866"));
        marketsList.add(new Markets("A Mart", "https://firebasestorage.googleapis.com/v0/b/ustart-2304.appspot.com/o/a_mart.png?alt=media&token=5cad2c71-6875-4714-adbf-575f6e2f09db"));

        adapter = new MarketsRecViewAdapter(getActivity());
        recView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recView.setAdapter(adapter);

        adapter.setMarketsList(marketsList);

    }
}