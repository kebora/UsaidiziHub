package com.simiyu.youhubpro;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.auth.api.credentials.CredentialsOptions;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.credentials.IdentityProviders;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class OneTapSignInManager {
    private static final String TAG = "OneTapSignInManager";
    private static final int RC_HINT = 1000;
    private GoogleApiClient mGoogleApiClient;
    private CredentialRequest mCredentialRequest;

    public OneTapSignInManager(Activity activity) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addApi(Auth.CREDENTIALS_API, new CredentialsOptions.Builder().forceEnableSaveDialog().build())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleApiClient.connect();
    }

    public void requestOneTapSignIn(Activity activity) {
        HintRequest hintRequest = new HintRequest.Builder()
                .setHintPickerConfig(new CredentialPickerConfig.Builder()
                        .setShowCancelButton(true)
                        .build())
                .setEmailAddressIdentifierSupported(true)
                .build();

        Auth.CredentialsApi.request(mGoogleApiClient, new CredentialRequest.Builder()
                .setPasswordLoginSupported(false)
                .setAccountTypes(IdentityProviders.GOOGLE)
                .setCredentialHintPickerConfig(hintRequest.getHintPickerConfig())
                .build()).setResultCallback(new ResultCallback<CredentialRequestResult>() {
            @Override
            public void onResult(@NonNull CredentialRequestResult credentialRequestResult) {
                if (credentialRequestResult.getStatus().isSuccess()) {
                    Credential credential = credentialRequestResult.getCredential();
                    signInWithCredential(activity, credential);
                } else {
                    Log.d(TAG, "One-tap sign-in request failed.");
                    Toast.makeText(activity, "One-tap sign-in request failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signInWithCredential(Activity activity, Credential credential) {
        GoogleSignIn.getClient(activity, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build())
                .silentSignIn()
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        GoogleSignInAccount account = task.getResult();
                        // Use the account information to sign in the user
                    } else {
                        Log.d(TAG, "Failed to sign in with credential.");
                        Toast.makeText(activity, "Failed to sign in with credential.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_HINT) {
            if (resultCode == Activity.RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                signInWithCredential((Activity) data.getParcelableExtra("activity"), credential);
            } else {
                Log.d(TAG, "One-tap sign-in request cancelled.");
                Toast.makeText((Activity) data.getParcelableExtra("activity"), "One-tap sign-in request cancelled.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveCredential(Credential credential) {
        Auth.CredentialsApi.save(mGoogleApiClient, credential).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    Log.d(TAG, "Credential saved.");
                } else {
                    Log.d(TAG, "Failed to save credential.");
                }
            }
        });
    }
}

