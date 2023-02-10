package com.mad.iti.onthetable.remoteSource.remoteFireBase;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FireBaseAuthWrapper {
    private FirebaseAuth auth;
    private static FireBaseAuthWrapper fireBaseAuthWrapper;
    public static synchronized FireBaseAuthWrapper getInstance(){
        if(fireBaseAuthWrapper == null){
            fireBaseAuthWrapper = new FireBaseAuthWrapper();
        }
        return fireBaseAuthWrapper;
    }

    public FireBaseAuthWrapper() {
        this.auth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getAuth() {
        return auth;
    }
    public FirebaseUser getCurrentUser(){
        return auth.getCurrentUser();
    }

    public void logout(){
        auth.signOut();
    }


}
