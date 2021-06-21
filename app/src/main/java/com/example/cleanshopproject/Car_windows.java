package com.example.cleanshopproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Car_windows extends AppCompatActivity {
    TextView main_toolbar_text;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_windows);
        drawerLayout = findViewById(R.id.drawer_layout);
        main_toolbar_text = findViewById(R.id.main_toolbar_Text);
        main_toolbar_text.setText("Car Windows...");
    }
    ////////////////////////////////////////////////////////////////////Navigation Drawer////////////////////////////////////////////////////////////////////

    public void ClickMenu(View view){
        MainActivity.openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        MainActivity.closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        MainActivity.redirectActivity(this, MainActivity.class);
    }

    public void ClickOrder(View view){
        MainActivity.redirectActivity(this, Order.class);
    }

    public void ClickNotifications(View view){
        MainActivity.redirectActivity(this, Notifications.class);
    }

    public void ClickWishList(View view){
        MainActivity.redirectActivity(this, WishList.class);
    }

    public void ClickAccount(View view){
        MainActivity.redirectActivity(this, Account.class);
    }

    public void ClickDashboard(View view){
        MainActivity.redirectActivity(this, Dashboard.class);
    }

    public void ClickSettings(View view){
        MainActivity.redirectActivity(this, Settings.class);
    }

    public void ClickAboutUs(View view){
        MainActivity.redirectActivity(this, AboutUs.class);
    }

    public void ClickLogout(View view){
        logout(this);
    }

    private static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure want to logout ? ");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(activity, "You have successfully logged out", Toast.LENGTH_SHORT).show();
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    static void redirectActivity(Activity activity, Class aClass){
        Intent intent = new Intent(activity, aClass);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);
    }
    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
////////////////////////////////////////////////////////////////////Navigation Drawer////////////////////////////////////////////////////////////////////
}