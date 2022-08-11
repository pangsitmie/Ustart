package com.example.ustart.views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ustart.MainActivity;
import com.example.ustart.R;
import com.example.ustart.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {


    private TextView dateTV,signInTV;
    private EditText uidET, nameET, emailET, passwordET, passwordConfirmET, phoneET;
    private RadioGroup radioGroup;

    private Button btnConfirm;
    private String date, uploadDateOfBirth;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private NavController navController;

    public static String URL;
    private String tempSex ="0";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        URL = getString(R.string.API_URL) + "signup";

        uidET = view.findViewById(R.id.uidET);
        nameET = view.findViewById(R.id.nameET);
        emailET = view.findViewById(R.id.emailET);
        passwordET = view.findViewById(R.id.passwordET);
        passwordConfirmET = view.findViewById(R.id.passwordConfirmET);
        dateTV = view.findViewById(R.id.dateTV);
        phoneET = view.findViewById(R.id.phoneET);

        radioGroup = view.findViewById(R.id.radioGroup);
        // get selected radio button from radioGroup
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch(checkedId) {
                    case R.id.radioButton1:
                        tempSex = "0";
                        break;
                    case R.id.radioButton2:
                        tempSex = "1";
                        break;
                }
            }
        });


        signInTV= view.findViewById(R.id.signInTV);
        btnConfirm = view.findViewById(R.id.btnConfirm);

        navController = Navigation.findNavController(view);

        signInTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_registerFragment_to_signInFragment);
            }
        });

        dateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datepickerdialog = new DatePickerDialog(view.getContext(),
                        AlertDialog.THEME_DEVICE_DEFAULT_DARK,mDateSetListener,year,month,day);

                //SET THE DIALOG VIEW TO 90%
                int width = (int)(getResources().getDisplayMetrics().widthPixels*0.85);
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                datepickerdialog.getWindow().setLayout(width,height);

                datepickerdialog.show();
                datepickerdialog.getWindow().setLayout(width,height);
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            //january is 0 december = 11
            public void onDateSet(DatePicker datePicker, int year, int month , int day){
                month = month+1;
                date = day + "/" + month +"/" + year;
                Log.d("DATE CALL", "onDateSet: mm/dd/yyyy" + date);
                dateTV.setText(date);
                uploadDateOfBirth = date;
            }
        };

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tempUID = uidET.getText().toString().trim();
                String tempName = nameET.getText().toString().trim();
                String tempEmail = emailET.getText().toString().trim();
                String tempPassword = passwordET.getText().toString().trim();
                String tempConfirmPassword = passwordConfirmET.getText().toString().trim();
                String tempDate = dateTV.getText().toString();
                String tempPhone = phoneET.getText().toString();

                //DATA VALIDATION
                if (tempUID.isEmpty()) {
                    uidET.setError("Create a valid username");
                    uidET.requestFocus();
                    return;
                }
                if (tempName.isEmpty()) {
                    nameET.setError("Full name is required!");
                    nameET.requestFocus();
                    return;
                }
                if (tempEmail.isEmpty()) {
                    emailET.setError("Email is required!");
                    emailET.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(tempEmail).matches()) {
                    emailET.setError("Please provide valid email!");
                    emailET.requestFocus();
                    return;
                }
                if (tempPassword.isEmpty()) {
                    passwordET.setError("Password is required!");
                    passwordET.requestFocus();
                    return;
                }
                if (tempPassword.length() < 6) {
                    passwordET.setError("Min password length should be 6 characters!");
                    passwordET.requestFocus();
                    return;
                }
                if (!tempConfirmPassword.equals(tempPassword)) {
                    passwordConfirmET.setError("Password does not match!");
                    passwordConfirmET.requestFocus();
                    return;
                }
                if (tempDate.equals("")) {
                    uploadDateOfBirth = "None";
                }
                if (tempPhone.isEmpty()) {
                    phoneET.setError("Phone number is required!");
                    phoneET.requestFocus();
                    return;
                }
                if (!Patterns.PHONE.matcher(tempPhone).matches()) {
                    phoneET.setError("Please provide valid number!");
                    phoneET.requestFocus();
                }

                //send signup API request
                signup(tempUID, tempName, tempEmail, tempPassword, uploadDateOfBirth, tempPhone,tempSex);
            }
        });
    }
    //signup API request
    private void signup(String uid, String name, String email, String pwd, String date, String phone, String sex){

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("anyText", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String rid = jsonObject.getString("rid");
                    String message = jsonObject.getString("message");

                    if(rid.equals("1")){
                        Toast.makeText(getContext(), "Sign up success", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(getContext(), MainActivity.class);
                        startActivity(login);
                    }
                    else{
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Sign up error !1"+e,Toast.LENGTH_LONG).show();
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
                params.put("name", name);
                params.put("email", email);
                params.put("pwd", pwd);
                params.put("date", date);
                params.put("phone", phone);
                params.put("sex", sex);

                params.put("uid", uid);
                params.put("icode", pwd);
                params.put("nname", name);
                params.put("isex", sex);
                params.put("birthday", date);
                params.put("ephone", phone);
                params.put("eemail", email);

                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);

        Toast.makeText(getContext(), "Sign up success!", Toast.LENGTH_SHORT).show();
    }
}