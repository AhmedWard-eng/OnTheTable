package com.mad.iti.onthetable.ui.authentication.login.view;

import com.google.firebase.auth.FirebaseUser;

public interface LoginViewInterface {
    void onSuccess(FirebaseUser user);
    void OnFailure(String message);

    void onSuccessPass();
}
