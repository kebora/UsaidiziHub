package com.simiyu.youhubpro;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

public class GoogleSignInHelper {

    private Activity mActivity;
    private GoogleSignInClient mGoogleSignInClient;
    private OnSignInListener mListener;

    public GoogleSignInHelper(Activity activity, OnSignInListener listener) {
        mActivity = activity;
        mListener = listener;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mActivity.startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }

    public void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mListener.onSignOut();
                    } else {
                        mListener.onError("Failed to sign out");
                    }
                });
    }

    public void handleSignInResult(Intent resultIntent) {
        try {
            GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(resultIntent).getResult(ApiException.class);
            mListener.onSignIn(account);
        } catch (ApiException e) {
            mListener.onError("Failed to sign in: " + e.getMessage());
        }
    }

    public interface OnSignInListener {
        void onSignIn(GoogleSignInAccount account);

        void onSignOut();

        void onError(String errorMessage);
    }
}