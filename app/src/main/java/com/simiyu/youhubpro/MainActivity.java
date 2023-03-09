package com.simiyu.youhubpro;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Retrieve the user details from Splash Activity
        Intent intent = getIntent();
        String userName = intent.getStringExtra("user_name");
        String userEmail = intent.getStringExtra("user_email");

        removeActionBar();
        addBottomNavBarActivity();

        if (savedInstanceState == null) {
            getSupportFragmentManager().
                    beginTransaction()
                    .replace(R.id.frame_layout_fs_placeholder, new MainFragment())
                    .commit();
        }
    }

    // Remove the action bar
    void removeActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    //bottom nav bar logic
    void addBottomNavBarActivity() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_navigation_home:
                    replaceNavFragment(MainFragment.class);
                    return true;
                case R.id.menu_search:
                    replaceNavFragment(SearchFragment.class);
                    return true;
                case R.id.menu_sessions:
                    replaceNavFragment(SessionFragment.class);
                    return true;
            }
            return false;
        });
    }
    //replace bottom nav bar matching fragment
    public void replaceNavFragment(Class fragmentClass) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout_fs_placeholder, fragmentClass, null)
                .commit();
    }
}

