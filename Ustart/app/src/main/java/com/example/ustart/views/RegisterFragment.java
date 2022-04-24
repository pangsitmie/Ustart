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
import android.widget.TextView;

import com.example.ustart.MainActivity;
import com.example.ustart.R;
import com.example.ustart.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

public class RegisterFragment extends Fragment {


    private TextView dateOfBirthText,singInText;
    private EditText editTextEmail, editTextName, editTextPassword, editTextConfirmPassword;
    private Button btnConfirm;
    private String date, uploadDateOfBirth;
    private ProgressDialog pd;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private AuthViewModel authViewModel;
    private NavController navController;


    //    FIREBASE
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private String UID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(getActivity().getApplication())).get(AuthViewModel.class);
        authViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null){
                    Intent intent  = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextEmail = view.findViewById(R.id.txtEditEmail);
        editTextName = view.findViewById(R.id.txtEditName);
        editTextPassword = view.findViewById(R.id.txtEditPassword);
        editTextConfirmPassword = view.findViewById(R.id.txtEditConfirmPassword);
        dateOfBirthText = view.findViewById(R.id.txtEditDateOfBirth);
        singInText= view.findViewById(R.id.singInText);
        btnConfirm = view.findViewById(R.id.btnConfirm);

        navController = Navigation.findNavController(view);

        singInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_registerFragment_to_signInFragment);
            }
        });

        dateOfBirthText.setOnClickListener(new View.OnClickListener() {
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
                dateOfBirthText.setText(date);
                uploadDateOfBirth = date;
            }
        };

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempUsername = editTextName.getText().toString().trim();
                String tempEmail = editTextEmail.getText().toString().trim();
                String tempDate = dateOfBirthText.getText().toString();
                String tempPassword = editTextPassword.getText().toString().trim();
                String tempConfirmPassword = editTextConfirmPassword.getText().toString().trim();

                //DATA VALIDATION
                if (tempUsername.isEmpty()) {
                    editTextName.setError("Full name is required!");
                    editTextName.requestFocus();
                    return;
                }
                if (tempEmail.isEmpty()) {
                    editTextEmail.setError("Email is required!");
                    editTextEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(tempEmail).matches()) {
                    editTextEmail.setError("Please provide valid email!");
                    editTextEmail.requestFocus();
                    return;
                }
                if (tempPassword.isEmpty()) {
                    editTextPassword.setError("Password is required!");
                    editTextPassword.requestFocus();
                    return;
                }
                if (tempPassword.length() < 6) {
                    editTextPassword.setError("Min password length should be 6 characters!");
                    editTextPassword.requestFocus();
                    return;
                }
                if (!tempConfirmPassword.equals(tempPassword)) {
                    editTextConfirmPassword.setError("Password does not match!");
                    editTextConfirmPassword.requestFocus();
                    return;
                }
                if (tempDate.equals("")) {
                    uploadDateOfBirth = "None";
                }

                authViewModel.register(tempEmail,tempPassword);
            }
        });
    }


}