package com.example.cleanshopproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cleanshopproject.adapters.RecyclerViewAdapter3;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    List<DataAdapter> ListOfdataAdapter2;
    String HTTP_JSON_URL2 = "http://192.168.0.101/LoginRegister/ViewOrdersCount.php";
    String Image_Number_JSON = "COUNT(id)";
    JsonArrayRequest RequestOfJSonArray2;
    RequestQueue requestQueue2;
    RecyclerView.LayoutManager layoutManagerOfrecyclerView2;
    RecyclerView.Adapter recyclerViewadapter2;
    ArrayList<String> ImageNumerNameArrayListForClick;
    TextView notificationNumber;
////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.drawer_layout);
        View order = (View)findViewById(R.id.card_order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Order.class);
                startActivity(intent);
            }
        });

        ImageNumerNameArrayListForClick = new ArrayList<>();
        ListOfdataAdapter2 = new ArrayList<>();
        notificationNumber = findViewById(R.id.notificationNumber);
        layoutManagerOfrecyclerView2 = new LinearLayoutManager(MainActivity.this);
        JSON_HTTP_CALL2();
        notificationNumber.setText(String.valueOf(ImageNumerNameArrayListForClick));
    }

    ////////////////////////////////////////////////////////////////////Counter-product-in-order-function//////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////START-Navigation Drawer////////////////////////////////////////////////////////////////////
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

    public void Car_interior(View view) {
        Intent intent = new Intent(this, Car_interior.class);
        startActivity(intent);
    }
    public void Interior_maintenance(View view) {
        Intent intent = new Intent(this, Interior_maintenance.class);
        startActivity(intent);
    }
    public void Car_windows(View view) {
        Intent intent = new Intent(this, Car_windows.class);
        startActivity(intent);
    }
    public void Leather_upholster(View view) {
        Intent intent = new Intent(this, Leather_upholster.class);
        startActivity(intent);
    }
    public void Maintenance_car(View view) {
        Intent intent = new Intent(this, Maintenance_car.class);
        startActivity(intent);
    }
    public void Products_promotion(View view) {
        Intent intent = new Intent(this, Products_promotion.class);
        startActivity(intent);
    }
////////////////////////////////////////////////////////////////////END-Navigation Drawer////////////////////////////////////////////////////////////////////
public void JSON_HTTP_CALL2(){

    RequestOfJSonArray2 = new JsonArrayRequest(HTTP_JSON_URL2,

            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    ParseJSonResponse2(response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

    requestQueue2 = Volley.newRequestQueue(MainActivity.this);

    requestQueue2.add(RequestOfJSonArray2);
}

    public void ParseJSonResponse2(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            DataAdapter GetDataAdapter3 = new DataAdapter();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter3.setImageTitle(json.getString(Image_Number_JSON));

                // Adding image title name in array to display on RecyclerView click event.
                ImageNumerNameArrayListForClick.add(json.getString(Image_Number_JSON));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            ListOfdataAdapter2.add(GetDataAdapter3);
        }

        recyclerViewadapter2 = new RecyclerViewAdapter3(ListOfdataAdapter2, MainActivity.this);
        notificationNumber.setText(String.valueOf(ImageNumerNameArrayListForClick));

        ImageNumerNameArrayListForClick = new ArrayList<>();
        ListOfdataAdapter2 = new ArrayList<>();
        layoutManagerOfrecyclerView2 = new LinearLayoutManager(MainActivity.this);
    }
}