package com.mad.iti.onthetable.ui.authentication.singup.presenter;

import com.google.firebase.auth.AuthCredential;

public interface SignUpPresenterInterface {

    void signUp(String email , String pass);

    void signUpWithGoogle(AuthCredential authCredential);
}
