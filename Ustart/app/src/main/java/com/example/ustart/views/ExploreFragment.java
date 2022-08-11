package com.example.ustart.views;

import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.Toast;

import com.example.ustart.Items;
import com.example.ustart.MainActivity;
import com.example.ustart.adapter.ItemsRecViewAdapter;
import com.example.ustart.R;
import com.example.ustart.database.AppDatabase;
import com.example.ustart.database.entity.CartEntity;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {
    private RecyclerView recView;
    private ItemsRecViewAdapter adapter;
    public static ArrayList<Items> items = new ArrayList<Items>();

    AppDatabase db;
    Button addCartBtn;
    int idx =1;


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

        addCartBtn = (Button) view.findViewById(R.id.addCart);
        db = AppDatabase.getAppDatabase(getActivity());
        recView = view.findViewById(R.id.itemsRecView);

        adapter = new ItemsRecViewAdapter(getActivity());
        //uses itemlist from main activity
        adapter.setItemsList(MainActivity.itemsList);


        for (int i=0;i< MainActivity.itemsList.size();i++)
        {
            Log.d("explore", MainActivity.itemsList.get(i).getImgURL());
        }


        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recView.setAdapter(adapter);

        addCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartEntity cartEntity = new CartEntity();
//                cartEntity.setId(idx);
                cartEntity.setIpd(idx);
                cartEntity.setPrice(idx);
                cartEntity.setQquantity(idx);
                new AddCartTask().execute(cartEntity);
            }
        });
    }
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//        super.onPointerCaptureChanged(hasCapture);
//    }

    private class AddCartTask extends AsyncTask<CartEntity,Void,Void>
    {
        @Override
        protected Void doInBackground(CartEntity... cartEntities) {
            db.cartDAO().insert(cartEntities[0]); //ini ambil data yang ke 0
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            //reset();
            Toast.makeText(getActivity(),"Data berhasil diInsert",Toast.LENGTH_LONG).show();
        }
    }

    public interface OnIntentInteractionListener {
        void onIntentInteractionListener(CartEntity cart);
    }
}