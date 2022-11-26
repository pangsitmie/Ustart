package com.roundbytes.fooable.views;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.roundbytes.fooable.database.entity.CartEntity;
import com.roundbytes.fooable.Items;
import com.roundbytes.fooable.MainActivity;
import com.roundbytes.fooable.adapter.ItemsRecViewAdapter;
import com.roundbytes.fooable.R;
//import com.roundbytes.fooable.database.AppDatabase;
import com.roundbytes.fooable.database.entity.CartEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExploreFragment extends Fragment {
    private RecyclerView recView;
    private ItemsRecViewAdapter adapter;
    public static ArrayList<Items> items = new ArrayList<Items>();

//    AppDatabase db;
    Button nextBtn, prevBtn;
    int idx =1;
    int initPage=1;
    String URL;


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

        URL = getString(R.string.API_URL);
        items = MainActivity.itemsList;


        nextBtn = (Button) view.findViewById(R.id.nextBtn);
        prevBtn = (Button) view.findViewById(R.id.prevBtn);

//        db = AppDatabase.getAppDatabase(getActivity());
        recView = view.findViewById(R.id.itemsRecView);

        adapter = new ItemsRecViewAdapter(getActivity());
        //uses itemlist from main activity
        adapter.setItemsList(MainActivity.itemsList);


        for (int i=0;i< MainActivity.itemsList.size();i++)
        {
            Log.d("explore", items.get(i).getImgURL());
        }


        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recView.setAdapter(adapter);

//        addCartBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CartEntity cartEntity = new CartEntity();
////                cartEntity.setId(idx);
//                cartEntity.setIpd(idx);
//                cartEntity.setPrice(idx);
//                cartEntity.setQquantity(idx);
//                new AddCartTask().execute(cartEntity);
//            }
//        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(initPage==MainActivity.totalPage){
                    initPage=1;
                }
                else{
                    initPage++;
                }
                Log.d("page", initPage+"");
                getDataByType("1", String.valueOf(MainActivity.showPage), String.valueOf(initPage));
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadItemImg();
                        loadDesc();
                    }
                }, 500);

            }
        });
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(initPage==1){
                    initPage=MainActivity.totalPage;
                }
                else{
                    initPage--;
                }
                Log.d("page", initPage+"");
                getDataByType("1", String.valueOf(MainActivity.showPage), String.valueOf(initPage));
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadItemImg();
                        loadDesc();
                    }
                }, 500);
            }
        });

        MainActivity.checkCartCount();
        Log.d("TAG", "MainActivity ccheck caart count"+ MainActivity.cartList.size());
    }
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//        super.onPointerCaptureChanged(hasCapture);
//    }

    private class AddCartTask extends AsyncTask<CartEntity,Void,Void>
    {
        @Override
        protected Void doInBackground(CartEntity... cartEntities) {
//            db.cartDAO().insert(cartEntities[0]); //ini ambil data yang ke 0
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

    private void getDataByType(String iTypeIdx, String showPage, String page) {
        items.clear();
        String url = URL + "purdSearch";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int totalPage =Integer.parseInt(jsonObject.getString("totalPage"));
                    int dataCount =Integer.parseInt(jsonObject.getString("dataCount"));
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                    for(int i=0;i< jsonArray.length();i++){
                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            int ipd = Integer.parseInt(jsonObject1.getString("ipd")) ;
                            String ivender = jsonObject1.getString("ivender");
                            String nname = jsonObject1.getString("nname");
                            double qoriginalprice = Double.parseDouble(jsonObject1.getString("qoriginalprice")); //current price that will always be updated
                            double qprice = Double.parseDouble(jsonObject1.getString("qprice"));
                            int qquantity = Integer.parseInt(jsonObject1.getString("qquantity"));
                            String itype = jsonObject1.getString("itype");
                            String iunit = jsonObject1.getString("iunit");
//                            LocalDate dindate = LocalDate.parse(jsonObject.getString("dindate"));
//                            LocalDate dlinedate = LocalDate.parse(jsonObject.getString("dlinedate"));
                            double dfinalprice = Double.parseDouble(jsonObject1.getString("dfinalprice"));

                            ArrayList<String> typeList = new ArrayList<>(Arrays.asList(itype.split(",")));
                            ArrayList<String> unitList = new ArrayList<>(Arrays.asList(iunit.split(",")));


                            LocalDate date2 = LocalDate.of(2022, 7, 6);
                            String dateStr = date2.toString();

//                            ItemEntity itemEntity = new ItemEntity(ipd, ivender, nname, qprice, qquantity, dfinalprice, dateStr, dateStr, "", "");
//                            database.fooadableDao().insertItemEntity(itemEntity);
                            items.add(new Items(ipd, ivender, nname, typeList, unitList,qoriginalprice, qprice, qquantity, dfinalprice, date2, date2, "", ""));

                            Log.d("TAG", "onResponse: "+ipd+nname);
//                            adapter.notifyDataSetChanged();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("itype", iTypeIdx);
                params.put("showPage", showPage);
                params.put("page", page);

                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    private void loadItemImg() {
        for (int idx = 0; idx < items.size(); idx++) {
            int ipd = items.get(idx).getIpd();

            int ii = idx;
            String url = URL + "purdPic?ipd=" + ipd;
            Log.d("IMG", url);

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                    Log.d("IMG", response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
//                        Log.d(TAG, jsonArray.toString());
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String eurl = jsonObject.getString("eurl");
                            Log.d("IMG EURL", eurl);
                            items.get(ii).setImgURL(eurl);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        }

    }

    private void loadDesc() {
        for (int idx = 0; idx < items.size(); idx++) {
            int ipd = items.get(idx).getIpd();

            int ii = idx;
            String url = URL + "purdCaption?ipd=" + ipd;
            Log.d("IMG", url);

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                    Log.d("IMG", response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
//                        Log.d(TAG, jsonArray.toString());
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String ememo = jsonObject.getString("ememo");
                            Log.d("ememo", ememo);
                            items.get(ii).setDesc(ememo);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        }

    }
}