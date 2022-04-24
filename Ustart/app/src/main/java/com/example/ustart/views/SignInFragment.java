package com.example.ustart.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ustart.MainActivity;
import com.example.ustart.R;
import com.example.ustart.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class SignInFragment extends Fragment {

    private EditText editTextEmail, editTextPassword;
    private Button btnConfirm;
    private AuthViewModel authViewModel;
    private NavController navController;
    //    FIREBASE
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private String UID;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);
        authViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null){
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                    //navController.navigate(R.id.action_signInFragment_to_registerFragment);
                }
            }
        });
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

        editTextEmail = view.findViewById(R.id.txtEditEmail);
        editTextPassword = view.findViewById(R.id.txtEditPassword);
        btnConfirm = view.findViewById(R.id.btnConfirm);

        navController = Navigation.findNavController(view);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempEmail = editTextEmail.getText().toString().trim();
                String tempPassword = editTextPassword.getText().toString().trim();

                //DATA VALIDATION
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
                authViewModel.signIn(tempEmail,tempPassword);
            }
        });
    }
}