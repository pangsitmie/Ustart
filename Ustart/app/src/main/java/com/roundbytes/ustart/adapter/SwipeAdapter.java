package com.roundbytes.ustart.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.roundbytes.ustart.Items;
import com.roundbytes.ustart.MainActivity;
import com.roundbytes.ustart.R;
import com.roundbytes.ustart.model.SaveSharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SwipeAdapter extends BaseAdapter {


    private Context context;
    private List<Items> list;

    public SwipeAdapter(Context context, List<Items> list) {
        this.context = context;
        this.list = list;

        Log.d("RSWQ", list.size()+"");
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        if(convertView == null)
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_koloda, viewGroup, false);
            ImageView imageView = view.findViewById(R.id.image);
            ImageView no = view.findViewById(R.id.no);
            ImageView yes = view.findViewById(R.id.yes);
            Glide.with(context).load(list.get(i).getImgURL()).into(imageView);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String iuid = SaveSharedPreference.getUID(context);
                    String ipd = String.valueOf(list.get(i).getIpd());
                    String qquantity = String.valueOf(1);
                    postCart(iuid, ipd, qquantity);
                    list.get(i).setqQuantity(1);
                    list.get(i).setdFinalPrice(list.get(i).getqPrice());
                    Log.d("HGHG", list.get(i).getdFinalPrice() +"");
                    Log.d("HGHG", list.get(i).getqPrice() +"");
                    Log.d("HGHG", list.get(i).getqQuantity() +"");
                    MainActivity.cartList.add(list.get(i));
                }
            });
        }
        else
        {
            view = convertView;
        }
        return  view;
    }
    private void postCart(String iuid, String ipd, String qquantity) {
        String url = context.getString(R.string.API_URL) + "purdCar";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

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
                        Toast.makeText(context, "item added to cart", Toast.LENGTH_SHORT).show();
                        MainActivity.checkCartCount();
                    } else
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

//                    cartList.add(new Items(id, "ivender", nname, iTypeList, iUnitList, qprice, uqquantity, qprice, date2, date2, "img url", "desc"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
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
}
