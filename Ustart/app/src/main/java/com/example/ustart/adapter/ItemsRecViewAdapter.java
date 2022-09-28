package com.example.ustart.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.ustart.Items;
import com.example.ustart.MainActivity;
import com.example.ustart.R;
import com.example.ustart.database.AppDatabase;
import com.example.ustart.database.entity.CartEntity;
import com.example.ustart.model.SaveSharedPreference;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ItemsRecViewAdapter extends RecyclerView.Adapter<ItemsRecViewAdapter.ViewHolder> {

    private static final String TAG = "ItemsRecViewAdapter";
    private Context mContext;
    private ArrayList<Items> itemsList = new ArrayList<>();
    private LineDataSet lineDataSet;
    private int itemSelected;
    int itemSelectedAmount = 1;
    private static final StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();

    AppDatabase db;


    //constructor
    public ItemsRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ItemsRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        db = AppDatabase.getAppDatabase(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_card, parent, false);
        return new ItemsRecViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsRecViewAdapter.ViewHolder holder, int position) {
        String imgUrl = itemsList.get(position).getImgURL();
        //String vender = itemsList.get(position).getiVender();
        String itemTitle = itemsList.get(position).getnName();
        Double originalPrice = itemsList.get(position).getqPrice();
        Double currentPrice = itemsList.get(position).getdFinalPrice();
//        String desc = itemsList.get(position).getDesc();
        LocalDate expDate = itemsList.get(position).getdLineDate();
        LocalDate inDate = itemsList.get(position).getdInDate();
        int quantity = itemsList.get(position).getqQuantity();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = expDate.toString();

        Glide.with(mContext).load(imgUrl).into(holder.itemImg);
        holder.itemTitle.setText(itemTitle);
        holder.desc.setText(itemsList.get(position).getDesc());
        holder.currentPrice.setText(currentPrice + " NT");
        holder.expDate.setText(strDate);


        holder.parent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                itemSelected = holder.getAdapterPosition();
                Toast.makeText(mContext, itemsList.get(itemSelected).getnName() + " Selected", Toast.LENGTH_SHORT).show();
                showDialog(itemSelected);
                Log.d("TTT", String.valueOf(expDate));
                Log.d("TTT", String.valueOf(inDate));

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void setItemsList(ArrayList<Items> itemsList) {
        this.itemsList = itemsList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView parent;
        ImageView itemImg;
        TextView itemTitle, currentPrice, expDate, desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            itemImg = itemView.findViewById(R.id.img);
            itemTitle = itemView.findViewById(R.id.nName);
            desc = itemView.findViewById(R.id.desc);
            expDate = itemView.findViewById(R.id.expDate);
            currentPrice = itemView.findViewById(R.id.currentPrice);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showDialog(int itemSelected) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        ImageView img = dialog.findViewById(R.id.img);
        TextView title = dialog.findViewById(R.id.title);
        TextView desc = dialog.findViewById(R.id.desc);
        TextView ivender = dialog.findViewById(R.id.ivender);
        TextView expDate = dialog.findViewById(R.id.expDate);
        TextView originalPrice = dialog.findViewById(R.id.qPrice);
        TextView currentPrice = dialog.findViewById(R.id.currentPrice);
        Button btnMinTransaction = dialog.findViewById(R.id.btnMinTransaction);
        TextView amount = dialog.findViewById(R.id.amount);
        Button btnAddTransaction = dialog.findViewById(R.id.btnAddTransaction);
        Button btnAddToCart = dialog.findViewById(R.id.btnAddtoCart);
        LineChart lineChart = dialog.findViewById(R.id.lineChart);

        lineDataSet = new LineDataSet(datavalues(itemSelected), "Data Set 1");//data set 1 contains price dataset

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();// arraylist of line dataset ex(dataset1, dataset2)
        dataSets.add(lineDataSet);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate();

// animation entry




        //set
        itemSelectedAmount = 1;
        title.setText(itemsList.get(itemSelected).getnName());
        desc.setText(itemsList.get(itemSelected).getDesc());
        expDate.setText(itemsList.get(itemSelected).getdLineDate().toString());
        ivender.setText(itemsList.get(itemSelected).getiVender());
        //        originalPrice.setText( );
        String oPrice = String.valueOf(itemsList.get(itemSelected).getqPrice());
        originalPrice.setText((oPrice), TextView.BufferType.SPANNABLE);
        Spannable spannable = (Spannable) originalPrice.getText();
        spannable.setSpan(STRIKE_THROUGH_SPAN, 0, oPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        currentPrice.setText(String.valueOf(itemsList.get(itemSelected).

                getdFinalPrice()));
        amount.setText(String.valueOf(itemSelectedAmount));
        String imgUrl = itemsList.get(itemSelected).getImgURL();

        Glide.with(mContext).

                load(imgUrl).

                into(img);


        btnMinTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSelectedAmount--;
                amount.setText(String.valueOf(itemSelectedAmount));
            }
        });

        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSelectedAmount++;
                amount.setText(String.valueOf(itemSelectedAmount));
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //add to Foodable database
                String iuid = SaveSharedPreference.getUID(mContext);
                String ipd = String.valueOf(itemsList.get(itemSelected).getIpd());
                String qquantity = String.valueOf(itemSelectedAmount);
                Log.d(TAG, iuid + "," + ipd + "<" + qquantity);
                postCart(iuid, ipd, qquantity);
                dialog.dismiss();

                /*BELOW THIS IS ROOM DB FUNCTION FOR STORING CART IN LOCAL DATABASE*/
//                CartEntity cartEntity = new CartEntity();
////                cartEntity.setId(idx);
//                cartEntity.setIpd(itemsList.get(itemSelected).getIpd());
//                cartEntity.setPrice(itemsList.get(itemSelected).getqPrice());
//                cartEntity.setQquantity(itemSelectedAmount);
//                cartEntity.setIvender(itemsList.get(itemSelected).getiVender());
//                cartEntity.setName(itemsList.get(itemSelected).getnName());
//                cartEntity.setExpdate(String.valueOf(itemsList.get(itemSelected).getdLineDate()));
//                cartEntity.setImgURL(itemsList.get(itemSelected).getImgURL());
//                new AddCartTask().execute(cartEntity);

                itemsList.get(itemSelected).setqQuantity(itemSelectedAmount);
                MainActivity.cartList.add(itemsList.get(itemSelected));//buat ngakalin kalau habis add item lgsg muncul di MainActivity.cartlist

                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.getWindow().

                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().

                setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().

                getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().

                setGravity(Gravity.BOTTOM);

    }
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//        super.onPointerCaptureChanged(hasCapture);
//    }

    private class AddCartTask extends AsyncTask<CartEntity, Void, Void> {
        @Override
        protected Void doInBackground(CartEntity... cartEntities) {
            db.cartDAO().insert(cartEntities[0]); //ini ambil data yang ke 0
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            //reset();
            Toast.makeText(mContext, "Data berhasil diInsert", Toast.LENGTH_LONG).show();
        }
    }

    public interface OnIntentInteractionListener {
        void onIntentInteractionListener(CartEntity cart);

    }

    private void postCart(String iuid, String ipd, String qquantity) {
        String url = mContext.getString(R.string.API_URL) + "purdCar";
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String rid = jsonObject.getString("rid");
                    String message = jsonObject.getString("message");

                    if (rid.equalsIgnoreCase("1")) {
                        Toast.makeText(mContext, "item added to cart", Toast.LENGTH_SHORT).show();
                        MainActivity.checkCartCount();
                    } else
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

//                    cartList.add(new Items(id, "ivender", nname, iTypeList, iUnitList, qprice, uqquantity, qprice, date2, date2, "img url", "desc"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("iuid", iuid);
                params.put("ipd", ipd);
                params.put("qquantity", qquantity);

                return params;
            }
        };

        postRequest.setRetryPolicy(new

                DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<Entry> datavalues(int idx) {


        double initPrice = itemsList.get(idx).getqPrice();
        int daysTemp = 4;

        double finalPrice = itemsList.get(idx).getdFinalPrice();
        double rangePrice = initPrice - finalPrice;
        double perDayDiscount = rangePrice / daysTemp;
        Log.d("INITS", initPrice + ", " + finalPrice + ", " + rangePrice + "");

        ArrayList<Entry> dataVals = new ArrayList<>();
        for (int i = 0; i < daysTemp; i++) {
            float val = (float) (initPrice - (perDayDiscount * i));
            dataVals.add(new Entry(i, val));

            Log.d("ENTRIES", i + ", " + val);
        }
        return dataVals;
    }
}
