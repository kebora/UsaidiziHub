package com.simiyu.usaidizihub;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class GlobalActivity extends AppCompatActivity {
    //Remove the Action Bar
    void removeActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
    }
}
