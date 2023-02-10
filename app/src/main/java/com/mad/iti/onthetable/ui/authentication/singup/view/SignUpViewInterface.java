package com.mad.iti.onthetable.ui.authentication.singup.view;

import com.google.firebase.auth.FirebaseUser;

public interface SignUpViewInterface {
    void onSuccess(FirebaseUser user);
    void OnFailure(String message);
}
