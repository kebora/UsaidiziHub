package com.simiyu.youhubpro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

public class SplashActivity extends AppCompatActivity {
    private ImageView imageViewBackground;
    private TextView textViewMainText;
    private Button emailAndPassword;
    private Button continueWithGoogleBtn;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        removeActionBar();
        textViewMainText = findViewById(R.id.textview_razor_studios);
        animateText(textViewMainText, "Razor Studios");

        // Check if the user is already signed in
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            // User is already signed in, move to HomeActivity
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra("user_name", account.getDisplayName());
            intent.putExtra("user_email", account.getEmail());
            startActivity(intent);
            finish();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //When the user selects email and password
        //
        emailAndPassword = findViewById(R.id.button_email_and_password);
        emailAndPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Continue with Google Button Click
        continueWithGoogleBtn = findViewById(R.id.button_continue_with_google);
        continueWithGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });

        //Add the main-text animation
        TextView myTextView = findViewById(R.id.main_text);
        Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_text_animation);
        myTextView.startAnimation(myAnimation);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            // You can access the user's email address using account.getEmail() and other account information.
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra("user_name", account.getDisplayName());
            intent.putExtra("user_email", account.getEmail());
            startActivity(intent);
            finish();

        } catch (ApiException e) {
            // The ApiException status code indicates the sign-in attempt was not successful.
            // You can display a message to the user indicating the error code and description using e.getStatusCode() and e.getMessage().
            // Get the root view of the current activity
            View rootView = findViewById(android.R.id.content);
            // Create a Snackbar with a message and duration
            Snackbar snackbar = Snackbar.make(rootView, e.toString(), Snackbar.LENGTH_SHORT);
            // Show the Snackbar
            snackbar.show();
        }
    }


    // Remove the action bar
    void removeActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    // Add the typing effect for the Razor Text
    private void animateText(final TextView textView, final String text) {
        final Handler handler = new Handler();
        final int delay = 75; //milliseconds

        final Runnable runnable = new Runnable() {
            int i = 0;

            @Override
            public void run() {
                textView.setText(text.subSequence(0, i++));
                if (i <= text.length()) {
                    handler.postDelayed(this, delay);
                }
            }
        };
        handler.postDelayed(runnable, delay);
    }

}