package com.simiyu.usaidizihub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

