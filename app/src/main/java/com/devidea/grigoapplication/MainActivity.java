package com.devidea.grigoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Button signUp;
    Button login;
    Button test;
    ServiceGenerator serviceGenerator;
    TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PrefsHelper.init(getApplicationContext());
        serviceGenerator = new ServiceGenerator();
        tokenManager = new TokenManager();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUp = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        test = findViewById(R.id.test);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceGenerator.signup();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceGenerator.login();
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceGenerator.test();
            }
        });


    }
}