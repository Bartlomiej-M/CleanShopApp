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
import android.os.Handler;
import android.os.Looper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cleanshopproject.adapters.RecyclerViewAdapter;
import com.example.cleanshopproject.adapters.RecyclerViewAdapter3;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Car_interior extends AppCompatActivity {
    TextView main_toolbar_text;
    DrawerLayout drawerLayout;
////////////////////////////////////////////////////////////////////////////////////////////////////START-PRODUCT////////////////////////////////////////////////////////////////////////////
    Button button4;
    List<DataAdapter> ListOfdataAdapter;
    RecyclerView recyclerView;
    String HTTP_JSON_URL = "http://192.168.0.101/products/ImageJSONdata.php";
    String Image_Title_JSON = "Title";
    String Image_Price_JSON = "Price";
    String Image_Rating_JSON = "Rating";
    String Image_URL_JSON = "Image";
    String Image_Category_JSON = "Category";
    String Title, Price, Rating, Image, Category;
    JsonArrayRequest RequestOfJSonArray ;
    RequestQueue requestQueue ;
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
    View view ;
    int RecyclerViewItemPosition ;
    RecyclerView.LayoutManager layoutManagerOfrecyclerView;
    RecyclerView.Adapter recyclerViewadapter;
    ArrayList<String> ImageURLArrayListForClick;
    ArrayList<String> ImageTitleNameArrayListForClick;
    ArrayList<String> ImageTitlePriceArrayListForClick;
    ArrayList<String> ImageTitleDescArrayListForClick;
    ArrayList<String> ImageTitleCategoryArrayListForClick;
    ////////////////////////////////////////////////////////////////////////////////////////////////////END-PRODUCT/////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_interior);

        LoadingDialog loadingDialog = new LoadingDialog(Car_interior.this);

        drawerLayout = findViewById(R.id.drawer_layout);
        main_toolbar_text = findViewById(R.id.main_toolbar_Text);
        main_toolbar_text.setText("Car Interior");
////////////////////////////////////////////////////////////////////////////////////////////////////START-Product////////////////////////////////////////////////////////////////////////////////
        ImageURLArrayListForClick = new ArrayList<>();
        ImageTitleNameArrayListForClick = new ArrayList<>();
        ImageTitlePriceArrayListForClick = new ArrayList<>();
        ImageTitleDescArrayListForClick = new ArrayList<>();
        ImageTitleCategoryArrayListForClick = new ArrayList<>();
        notificationNumber = findViewById(R.id.notificationNumber);
////////////////////////////////////////////////////////////////////////////////////////////////////
        ListOfdataAdapter = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);
        layoutManagerOfrecyclerView = new LinearLayoutManager(Car_interior.this);
        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);
        JSON_HTTP_CALL();
////////////////////////////////////////////////////////////////////////////////////////////////////
        ImageNumerNameArrayListForClick = new ArrayList<>();
        ListOfdataAdapter2 = new ArrayList<>();
        notificationNumber = findViewById(R.id.notificationNumber);
        layoutManagerOfrecyclerView2 = new LinearLayoutManager(Car_interior.this);
        JSON_HTTP_CALL2();
        notificationNumber.setText(String.valueOf(ImageNumerNameArrayListForClick));
////////////////////////////////////////////////////////////////////////////////////////////////////

        // Implementing Click Listener on RecyclerView.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(Car_interior.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
                view = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if(view != null && gestureDetector.onTouchEvent(motionEvent)) {
                    notificationNumber.setText(String.valueOf(ImageNumerNameArrayListForClick));
                    button4 = (Button) findViewById(R.id.button4);
                    button4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AccountRegistration();
                            Counter();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                            loadingDialog.startLoadingDialog();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadingDialog.dismissDialog();
                                }
                            }, 4000);
                            Toast.makeText(Car_interior.this, "The product has been added to the cart", Toast.LENGTH_LONG).show();
                        }
                    });
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(view);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }


        });
    ////////////////////////////////////////////////////////////////////////////////////////////////////END-Product////////////////////////////////////
    }
    ////////////////////////////////////////////////////////////////////Counter-product-in-order-function///////////////////////////////////////////////
    public void Counter(){
        notificationNumber.setText(String.valueOf(ImageNumerNameArrayListForClick));
        ImageNumerNameArrayListForClick = new ArrayList<>();
        ListOfdataAdapter2 = new ArrayList<>();
        layoutManagerOfrecyclerView2 = new LinearLayoutManager(Car_interior.this);
        JSON_HTTP_CALL2();
    }
    ////////////////////////////////////////////////////////////////////Navigation Drawer///////////////////////////////////////////////////////////////
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
////////////////////////////////////////////////////////////////////////////////////////////////////START-Product////////////////////////////////////////
public void JSON_HTTP_CALL(){

    RequestOfJSonArray = new JsonArrayRequest(HTTP_JSON_URL,

            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    ParseJSonResponse(response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

    requestQueue = Volley.newRequestQueue(Car_interior.this);

    requestQueue.add(RequestOfJSonArray);
}

    public void ParseJSonResponse(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            DataAdapter GetDataAdapter2 = new DataAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);


                GetDataAdapter2.setImageTitle(json.getString(Image_Title_JSON));
                GetDataAdapter2.setImagePrice(json.getString(Image_Price_JSON));
                GetDataAdapter2.setImageDesc(json.getString(Image_Rating_JSON));
                GetDataAdapter2.setImageCategory(json.getString(Image_Category_JSON));


                // Adding image title name in array to display on RecyclerView click event.

                ImageTitleNameArrayListForClick.add(json.getString(Image_Title_JSON));
                ImageTitlePriceArrayListForClick.add(json.getString(Image_Price_JSON));
                ImageTitleDescArrayListForClick.add(json.getString(Image_Rating_JSON));
                ImageTitleCategoryArrayListForClick.add(json.getString(Image_Category_JSON));


                GetDataAdapter2.setImageUrl(json.getString(Image_URL_JSON));
                ImageURLArrayListForClick.add(json.getString(Image_URL_JSON));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            ListOfdataAdapter.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapter(ListOfdataAdapter, Car_interior.this);

        recyclerView.setAdapter(recyclerViewadapter);
    }

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

        requestQueue2 = Volley.newRequestQueue(Car_interior.this);

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

        recyclerViewadapter2 = new RecyclerViewAdapter3(ListOfdataAdapter2, Car_interior.this);
        notificationNumber.setText(String.valueOf(ImageNumerNameArrayListForClick));

        ImageNumerNameArrayListForClick = new ArrayList<>();
        ListOfdataAdapter2 = new ArrayList<>();
        layoutManagerOfrecyclerView2 = new LinearLayoutManager(Car_interior.this);
    }



    public void AccountRegistration() {
/*      Title = "ImageTitleNameArrayListForClick.get(RecyclerViewItemPosition)";
        Price = "ImageTitlePriceArrayListForClick.get(RecyclerViewItemPosition)";
        Rating = "ImageTitleDescArrayListForClick.get(RecyclerViewItemPosition)";
        Image = "ImageURLArrayListForClick.get(RecyclerViewItemPosition)";
        Category = "ImageTitleCategoryArrayListForClick.get(RecyclerViewItemPosition)";*/


        Title = ImageTitleNameArrayListForClick.get(RecyclerViewItemPosition);
        Price = ImageTitlePriceArrayListForClick.get(RecyclerViewItemPosition);
        Rating = ImageTitleDescArrayListForClick.get(RecyclerViewItemPosition);
        Image = ImageURLArrayListForClick.get(RecyclerViewItemPosition);
        Category = ImageTitleCategoryArrayListForClick.get(RecyclerViewItemPosition);

        ByteArrayOutputStream byteArrayOutputStreamObject;
        byteArrayOutputStreamObject = new ByteArrayOutputStream();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.101/Loginregister/addproducts.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Your Image Has Been Uploaded.")) {
                            Toast.makeText(Car_interior.this, "Dodawanie powidoło się", Toast.LENGTH_SHORT).show();
                        } else if (response.equals("Not Uploaded")) {
                            Toast.makeText(Car_interior.this, "Dodawanie nie powiodło się", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Car_interior.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("Title", Title);
                        data.put("Price", Price);
                        data.put("Rating", Rating);
                        data.put("Image", Image);
                        data.put("Category", Category);
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });
    }
////////////////////////////////////////////////////////////////////////////////////////////////////END-Product//////////////////////////////////////////
}