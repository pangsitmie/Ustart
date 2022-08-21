package com.roundbytes.foodrescueseller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterVenderActivity extends AppCompatActivity {
    private String ivender, eemail, ephone;

    private EditText venderNameET, picNameET, addressET;
    private Button btnConfirm;

    private String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vender);
        URL = getString(R.string.API_URL) + "vender";

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            ivender = bundle.getString("ivender");
            eemail = bundle.getString("eemail");
            ephone = bundle.getString("ephone");
        }

        venderNameET = (EditText) findViewById(R.id.venderNameET);
        picNameET = (EditText) findViewById(R.id.picNameET);
        addressET = (EditText) findViewById(R.id.addressET);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String venderName = venderNameET.getText().toString();
                String picName = picNameET.getText().toString();
                String address = addressET.getText().toString();

                registerVender(ivender, venderName, picName, eemail, ephone, address);
            }
        });
    }
    private void registerVender(String ivender, String nname, String nman, String eemail, String ephone, String eaddress){

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("anyText", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String rid = jsonObject.getString("rid");
                    String message = jsonObject.getString("message");

                    if(rid.equals("1")){
                        Toast.makeText(getApplicationContext(), "Sign up success", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(login);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Sign up error !"+e,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("ivender", ivender);
                params.put("nname", nname);
                params.put("nman", nman);
                params.put("eemail", eemail);
                params.put("ephone", ephone);
                params.put("eaddress", eaddress);
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);

        Toast.makeText(getApplicationContext(), "Sign up success!", Toast.LENGTH_SHORT).show();
    }
}