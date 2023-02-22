package com.simiyu.usaidizihub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontal_counselors_row);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);

        BottomNavigationView buttonNavigationView = findViewById(R.id.bottom_nav);
        buttonNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.menu_navigation_home:
                case R.id.menu_search:
                case R.id.menu_sessions:
                    //do something here
                    return true;
            }
            return false;
        });
    }
    void removeActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
    }
}