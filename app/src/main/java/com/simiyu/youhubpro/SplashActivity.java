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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                String userId = account.getId();
                String userEmail = account.getEmail();
                String userName = account.getDisplayName();

                // Get a reference to the Firebase database
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

                // Create a new user object with the user's information
                User user = new User(userId, userName, userEmail);

                // Write the user object to the database
                databaseRef.child("users").child(userId).setValue(user);

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);

                // Create a new instance of the fragment and set its arguments
//                MainFragment homeFragment = new MainFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("user_name", userName);
//                bundle.putString("user_email", userEmail);
//                homeFragment.setArguments(bundle);

                // Add a delay to ensure that the sign-in process is complete
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Add extras to the intent
                        intent.putExtra("user_name", userName);
                        intent.putExtra("user_email", userEmail);

                        // Start the MainActivity
                        startActivity(intent);
                        finish();
                    }
                }, 1000); // Delay for 1 second
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