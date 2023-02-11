package com.mad.iti.onthetable.model.repositories.authRepo;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.SignInDelegate;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.SignUpDelegate;
import com.mad.iti.onthetable.remoteSource.remoteFireBase.SignUpWithGoogleDelegate;

public interface AuthenticationRepo {
    void signIn(String email, String pass, SignInDelegate signInDelegate);

    void signUp(String email, String pass, SignUpDelegate signUpDelegate);
    void logout();
    FirebaseUser getUser();
   FirebaseAuth getFirebaseAuth();

   void signUpWithGoogle(AuthCredential authCredential,SignUpWithGoogleDelegate signUpWithGoogleDelegate);
}
