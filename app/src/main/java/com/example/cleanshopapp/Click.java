package com.example.cleanshopapp;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class Click extends AppCompatActivity {
    DrawerLayout drawerLayout;

    public void ClickOrder(View view){
        MainActivity.redirectActivity(this, Order.class);
    }
}
