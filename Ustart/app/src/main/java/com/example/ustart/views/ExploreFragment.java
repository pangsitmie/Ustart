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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.Items;
import com.example.ustart.adapter.ItemsRecViewAdapter;
import com.example.ustart.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

//        LocalDate date1 = LocalDate.of(2022, 6, 15);
//        LocalDate date2 = LocalDate.of(2022, 7, 6);



        getExploreData();

        adapter = new ItemsRecViewAdapter(getActivity());
        adapter.setItemsList(itemsList);

        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recView.setAdapter(adapter);

    }

    private void getExploreData(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = "http://192.168.1.9/ustart/api/purdData?ipd=";

        for (int i=1;i<=3;i++){
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url+i, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //loadingPB.setVisibility(View.GONE);
                    try {
                        String ipd = response.getString("ipd");
                        String ivender = response.getString("ivender");
                        String nname = response.getString("nname");
                        String qprice = response.getString("qprice");
                        String qquantity = response.getString("qquantity");
                        String itype = response.getString("itype");
                        String iunit = response.getString("iunit");
                        String dindate = response.getString("dindate");
                        String dlinedate = response.getString("dlinedate");
                        String dfinalprice = response.getString("dfinalprice");
                        Log.d("TAG", ipd+nname);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //loadingPB.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Failed to get market data", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        }

    }
}