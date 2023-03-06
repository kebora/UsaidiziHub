package com.simiyu.usaidizihub;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private ImageView imageViewBackground;
    private Button emailAndPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        removeActionBar();

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

        //Add the main-text animation
        TextView myTextView = findViewById(R.id.textview_razor_studios);
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
}