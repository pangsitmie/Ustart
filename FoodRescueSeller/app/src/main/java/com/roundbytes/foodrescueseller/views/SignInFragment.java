package com.roundbytes.foodrescueseller.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.roundbytes.foodrescueseller.MainActivity;
import com.roundbytes.foodrescueseller.R;
import com.roundbytes.foodrescueseller.SaveSharedPreference;
import com.roundbytes.foodrescueseller.WelcomeActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignInFragment extends Fragment {

    private EditText uidET, passwordET;
    private Button btnConfirm;
//    private AuthViewModel authViewModel;
    private NavController navController;

    String url = WelcomeActivity.URL + "login";

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uidET = view.findViewById(R.id.nnameET);
        passwordET = view.findViewById(R.id.quanitityET);
        btnConfirm = view.findViewById(R.id.btnConfirm);

        navController = Navigation.findNavController(view);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempUID = uidET.getText().toString().trim();
                String tempPassword = passwordET.getText().toString().trim();

                //DATA VALIDATION
                if (tempUID.isEmpty()) {
                    uidET.setError("uid is required!");
                    uidET.requestFocus();
                    return;
                }
                if (tempPassword.isEmpty()) {
                    passwordET.setError("Password is required!");
                    passwordET.requestFocus();
                    return;
                }
                signin(tempUID, tempPassword);
            }
        });
    }

    private void signin(String uid, String password){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("anyText", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String rid = jsonObject.getString("rid");
                    String message = jsonObject.getString("message");

                    if(rid.equals("1")){
                        Toast.makeText(getContext(), "Sing In Success", Toast.LENGTH_SHORT).show();
                        SaveSharedPreference.setUID(getContext(), uid);
                        Intent login = new Intent(getContext(), MainActivity.class);
                        startActivity(login);
                    }
                    else{
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Sign in error !1"+e,Toast.LENGTH_LONG).show();
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
                Map<String, String>  params = new HashMap<String, String>();
                params.put("uid", uid);
                params.put("pwd", password);

                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }
}