package com.roundbytes.fooable.model;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.roundbytes.fooable.R;


public class AppRepository {
//    private Application application;
//    private FirebaseAuth mAuth;
//    private MutableLiveData<FirebaseUser> userMutableLiveData;
//    private MutableLiveData<Boolean> userLoggedMutableLiveData;
//
//    public AppRepository(Application application) {
//        this.application = application;
//
//
//        userMutableLiveData = new MutableLiveData<>();
//        userLoggedMutableLiveData = new MutableLiveData<>();
//        mAuth = FirebaseAuth.getInstance();
//
//        if(mAuth.getCurrentUser() != null){
//            userMutableLiveData.postValue(mAuth.getCurrentUser());
//        }
//    }
//
//    public void register(String email, String password){
//        mAuth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()){
//                        userMutableLiveData.postValue(mAuth.getCurrentUser());
//                    } else{
//                        Toast.makeText(application, "Register Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//    }
//
//    public void login(String email, String pass){
//        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful())
//                    userMutableLiveData.postValue((mAuth.getCurrentUser()));
//                else
//                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    public void logOut(){
//        mAuth.signOut();
//        userLoggedMutableLiveData.postValue(true);
//    }
//
//    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
//        return userMutableLiveData;
//    }
//
//    public MutableLiveData<Boolean> getUserLoggedMutableLiveData() {
//        return userLoggedMutableLiveData;
//    }
}
