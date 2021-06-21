package com.example.cleanshopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Register extends AppCompatActivity {
    TextInputEditText textInputEditTextFullname, textInputEditTextRepeatPassword, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail;
    Button buttonSignUp;
    TextView textViewLogin;
    boolean check = true;
    Bitmap bitmap;
    String ServerUploadPath = "http://192.168.0.101/UploadImage-YouTube/upload.php";
    ImageView imageView;
    String ImagePath = "image_path";
    Button SelectImageGallery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imageView = (ImageView) findViewById(R.id.imageView);
        SelectImageGallery = (Button) findViewById(R.id.buttonSelect);
        SelectImageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
            }
        });


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
        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountRegistration();
            }
        });
    }
    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);
        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {
            Uri uri = I.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void AccountRegistration (){
        String fullname, email, username, password, repeatPassword, ConvertImage;
        username = String.valueOf(textInputEditTextUsername.getText());
        password = String.valueOf(textInputEditTextPassword.getText());
        repeatPassword = String.valueOf(textInputEditTextRepeatPassword.getText());
        fullname = String.valueOf(textInputEditTextFullname.getText());
        email = String.valueOf(textInputEditTextEmail.getText());

        ByteArrayOutputStream byteArrayOutputStreamObject;
        byteArrayOutputStreamObject = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();
        ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        if (password.length() >= 8 && !fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("") && !ConvertImage.isEmpty()) {
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
                                    Intent intent = new Intent(Register.this, Login.class);
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
                                ImageProcessClass imageProcessClass = new ImageProcessClass();
                                Map<String, String> data = new HashMap<>();
                                data.put("fullname", fullname);
                                data.put("email", email);
                                data.put("username", username);
                                data.put("password", password);
                                data.put(ImagePath, ConvertImage);
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




    public class ImageProcessClass {

        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url;
                HttpURLConnection httpURLConnectionObject;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject;
                BufferedReader bufferedReaderObject;
                int RC;
                url = new URL(requestURL);
                httpURLConnectionObject = (HttpURLConnection) url.openConnection();
                httpURLConnectionObject.setReadTimeout(19000);
                httpURLConnectionObject.setConnectTimeout(19000);
                httpURLConnectionObject.setRequestMethod("POST");
                httpURLConnectionObject.setDoInput(true);
                httpURLConnectionObject.setDoOutput(true);
                OutPutStream = httpURLConnectionObject.getOutputStream();
                bufferedWriterObject = new BufferedWriter(
                        new OutputStreamWriter(OutPutStream, "UTF-8"));
                bufferedWriterObject.write(bufferedWriterDataFN(PData));
                bufferedWriterObject.flush();
                bufferedWriterObject.close();
                OutPutStream.close();
                RC = httpURLConnectionObject.getResponseCode();
                if (RC == HttpsURLConnection.HTTP_OK) {
                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));
                    stringBuilder = new StringBuilder();
                    String RC2;
                    while ((RC2 = bufferedReaderObject.readLine()) != null) {
                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {
            StringBuilder stringBuilderObject;
            stringBuilderObject = new StringBuilder();
            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilderObject.append("&");
                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));
                stringBuilderObject.append("=");
                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }
            return stringBuilderObject.toString();
        }
    }

}