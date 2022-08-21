package com.roundbytes.foodrescueseller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity1 extends AppCompatActivity{
    private EditText nnameET, qpriceET, finalPriceET, quanitityET;
    private Spinner itypeSpinner, iunitSpinner;
    private TextView dindateTV, dlinedateTV;
    private String date, UID;
    private Button nextBtn;
    private String selectedUnitPosition, selectedTypePosition;

    private ArrayList<Type> typeArrayList = new ArrayList<>();
    private ArrayList<Unit> unitArrayList = new ArrayList<>();
    ArrayAdapter<Type> typeArrayAdapter;
    ArrayAdapter<Unit> unitArrayAdapter;

//    private String[] types = {};
//    private String[] units = {};

    private DatePickerDialog.OnDateSetListener mDateSetListener, mDateSetListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload1);

        UID = SaveSharedPreference.getUID(getApplicationContext());


        nnameET = findViewById(R.id.venderNameET);
        qpriceET = findViewById(R.id.picNameET);
        finalPriceET = findViewById(R.id.addressET);
        quanitityET = findViewById(R.id.quanitityET);
        dindateTV = findViewById(R.id.dindateTV);
        dlinedateTV = findViewById(R.id.dlinedateTV);
        itypeSpinner = (Spinner) findViewById(R.id.itypeSpinner);
        iunitSpinner = (Spinner) findViewById(R.id.iunitSpinner);
        nextBtn = findViewById(R.id.btnNext);

        dindateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datepickerdialog = new DatePickerDialog(view.getContext(),
                        AlertDialog.THEME_DEVICE_DEFAULT_DARK, mDateSetListener, year, month, day);

                //SET THE DIALOG VIEW TO 90%
                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85);
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                datepickerdialog.getWindow().setLayout(width, height);

                datepickerdialog.show();
                datepickerdialog.getWindow().setLayout(width, height);
            }
        });

        dlinedateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datepickerdialog = new DatePickerDialog(view.getContext(),
                        AlertDialog.THEME_DEVICE_DEFAULT_DARK, mDateSetListener2, year, month, day);

                //SET THE DIALOG VIEW TO 90%
                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85);
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                datepickerdialog.getWindow().setLayout(width, height);

                datepickerdialog.show();
                datepickerdialog.getWindow().setLayout(width, height);
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            //january is 0 december = 11
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                date = year + "-" + month + "-" + day;
                Log.d("DATE CALL", "onDateSet: mm/dd/yyyy" + date);
                dindateTV.setText(date);
            }
        };

        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            //january is 0 december = 11
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                date = year + "-" + month + "-" + day;
                Log.d("DATE CALL", "onDateSet: mm/dd/yyyy" + date);
                dlinedateTV.setText(date);
            }
        };

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nname = nnameET.getText().toString();
                String qprice = qpriceET.getText().toString();
                String finalPrice = finalPriceET.getText().toString();
                String qquanitity = quanitityET.getText().toString();
//                String uploadType = String.valueOf(selectedTypePosition);
//                String uploadUnit = String.valueOf(selectedUnitPosition);
                String dindate = dindateTV.getText().toString();
                String dlinedateStr = dlinedateTV.getText().toString();
                //Toast.makeText(getApplicationContext(), selectedTypePosition+" "+selectedUnitPosition, Toast.LENGTH_SHORT).show();

                Log.d("NEXT", UID + nname + qprice + qquanitity + selectedTypePosition + selectedUnitPosition + dindate + dlinedateStr + finalPrice);
                postPurdData(nname, qprice, qquanitity, selectedTypePosition, selectedUnitPosition, dindate, dlinedateStr, finalPrice);
            }
        });


        //get TYPE AND UNIT
        getPurdType();
        getPurdUnit();

        typeArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, typeArrayList);
        typeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itypeSpinner.setAdapter(typeArrayAdapter);
        itypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //selectedType =  typeArrayList.get(position).getNname();
                selectedTypePosition = typeArrayList.get(position).getItype();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        unitArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, unitArrayList);
        unitArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        iunitSpinner.setAdapter(unitArrayAdapter);
        iunitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUnitPosition = unitArrayList.get(position).getIunit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("TYPE ARRAY", typeArrayList.toString());
            }
        }, 1000);



    }

    private void postPurdData(String nname, String qprice, String qquantity, String itype, String iunit, String dindate, String dlinedate, String dfinalprice) {
        String url = MainActivity.URL + "purdData";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String rid = jsonObject.getString("rid");
                    String message = jsonObject.getString("message");
                    if (rid.equalsIgnoreCase("1")) {
                        getProductIpd(nname, qprice, qquantity, itype, iunit, dindate, dlinedate, dfinalprice);
                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ivender", UID);
                params.put("nname", nname);
                params.put("qprice", qprice);
                params.put("qquantity", qquantity);
                params.put("itype", itype);
                params.put("iunit", iunit);
                params.put("dindate", dindate);
                params.put("dlinedate", dlinedate);
                params.put("dfinalprice", dfinalprice);

                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    private void getProductIpd(String nname, String qprice, String qquantity, String itype, String iunit, String dindate, String dlinedate, String dfinalprice) {
        String url = MainActivity.URL + "purdSearch";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String ipd = jsonObject1.getString("ipd");
                        Log.d("IPD", ipd);
                        Toast.makeText(getApplicationContext(), "Continue upload", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), UploadActivity2.class);
                        intent.putExtra("ipd", ipd);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ivender", UID);
                params.put("nname", nname);
                params.put("qprice", qprice);
                params.put("qquantity", qquantity);
                params.put("itype", itype);
                params.put("iunit", iunit);
                params.put("dindate", dindate);
                params.put("dlinedate", dlinedate);
                params.put("dfinalprice", dfinalprice);

                return params;
            }
        };
        requestQueue.add(postRequest);
    }

//    private void getPurdType() {
//        String url = MainActivity.URL + "purdType";
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONArray jsonArray = new JSONArray(response);
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        Log.d("TYPE", jsonObject.toString());
//                        //String ipd = jsonObject.getString("ipd");
//                        //String itype = jsonObject.getString("itype");
////                        String nname = jsonObject.getString("nname");
////                        String ememo = jsonObject.getString("ememo");
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                return params;
//            }
//        };
//        requestQueue.add(postRequest);
//    }

    private void getPurdType() {
        String url = MainActivity.URL + "purdType";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d("TYPE", jsonObject.toString());
                        String itype = jsonObject.getString("itype");
                        String nname = jsonObject.getString("nname");
                        String ememo = jsonObject.getString("ememo");

                        typeArrayList.add(new Type(itype, nname, ememo));
//                        typeArrayList.add(nname);
                    }
                    typeArrayAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }

    private void getPurdUnit() {
        String url = MainActivity.URL + "purdUnit";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String iunit = jsonObject.getString("iunit");
                        String nname = jsonObject.getString("nname");
                        String ememo = jsonObject.getString("ememo");

                        unitArrayList.add(new Unit(iunit,nname,ememo));
                    }
                    unitArrayAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }


}