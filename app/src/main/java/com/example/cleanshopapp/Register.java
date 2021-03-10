package com.example.cleanshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    TextInputEditText textInputEditTextFullname, textInputEditTextRepeatPassword, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail;
    Button buttonSignUp;
    TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textInputEditTextFullname = findViewById(R.id.fullname);
        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        textInputEditTextRepeatPassword = findViewById(R.id.repeatPassword);
        textInputEditTextEmail = findViewById(R.id.email);

        buttonSignUp = findViewById(R.id.buttonSignUp);

        textViewLogin = findViewById(R.id.loginText);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
        //End Change Login-Registation

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountRegistration();
                }
        });
    }

    public void AccountRegistration (){
        String fullname, email, username, password, repeatPassword;
        username = String.valueOf(textInputEditTextUsername.getText());
        password = String.valueOf(textInputEditTextPassword.getText());
        repeatPassword = String.valueOf(textInputEditTextRepeatPassword.getText());
        fullname = String.valueOf(textInputEditTextFullname.getText());
        email = String.valueOf(textInputEditTextEmail.getText());

        if (password.length() >= 8 && !fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("")) {
            if(repeatPassword.equals(password)) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.101/LoginRegister/signup.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("Sign Up Success")) {
                                    Toast.makeText(Register.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Register.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else if (response.equals("Sign up Failed")) {
                                    Toast.makeText(Register.this, "Invalid login/password/mail/fullname", Toast.LENGTH_SHORT).show();
                                } else if (response.equals("Error: Database connection")) {
                                    Toast.makeText(Register.this, "Error: Database connection", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Register.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> data = new HashMap<>();
                                data.put("fullname", fullname);
                                data.put("email", email);
                                data.put("username", username);
                                data.put("password", password);
                                return data;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(stringRequest);
                    }
                });//end of handler
            }else {
                Toast.makeText(getApplicationContext(), "Hasła nie sa takie same", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
        }//end all method
    }
}
