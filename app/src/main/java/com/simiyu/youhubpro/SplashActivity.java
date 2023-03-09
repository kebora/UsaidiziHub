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

public class SplashActivity extends AppCompatActivity {
    private ImageView imageViewBackground;
    private TextView textViewMainText;
    private Button emailAndPassword;
    private Button continueWithGoogleBtn;

    private GoogleSignInHelper mGoogleSignInHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        removeActionBar();
        textViewMainText = findViewById(R.id.textview_razor_studios);
        animateText(textViewMainText, "Razor Studios");

        mGoogleSignInHelper = new GoogleSignInHelper(this, new GoogleSignInHelper.OnSignInListener() {
            @Override
            public void onSignIn(GoogleSignInAccount account) {
                // Handle successful sign-in
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                intent.putExtra("user_name", account.getDisplayName());
                intent.putExtra("user_email", account.getEmail());
                startActivity(intent);
                finish();
            }

            @Override
            public void onSignOut() {
                // Handle sign-out
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error
            }
        });

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
                mGoogleSignInHelper.signIn();
            }
        });

        //Add the main-text animation
        TextView myTextView = findViewById(R.id.main_text);
        Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_text_animation);
        myTextView.startAnimation(myAnimation);

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