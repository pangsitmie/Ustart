package com.roundbytes.fooable.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.roundbytes.fooable.model.AppRepository;
import com.roundbytes.fooable.model.AppRepository;
//import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends AndroidViewModel {
    public AuthViewModel(@NonNull Application application) {
        super(application);
    }
//    private AppRepository appRepository;
////    private MutableLiveData<FirebaseUser> userData;
//    private MutableLiveData<Boolean> loggedStatus;
//
//
//    public AuthViewModel(@NonNull Application application) {
//        super(application);
//
//        appRepository = new AppRepository(application);
////        userData = appRepository.getUserMutableLiveData();
//        loggedStatus = appRepository.getUserLoggedMutableLiveData();
//    }
//
//    public void register(String email, String password){
//        appRepository.register(email, password);
//    }
//
//    public void signIn(String email, String password){
//        appRepository.login(email, password);
//    }
//    public void logOut(){
//        appRepository.logOut();
//    }
//
////    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
////        return userData;
////    }
//
//    public MutableLiveData<Boolean> getLoggedStatus() {
//        return loggedStatus;

}
