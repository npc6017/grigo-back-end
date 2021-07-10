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
    ServiceGenerator serviceGenerator;
    TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PrefsHelper.init(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceGenerator = new ServiceGenerator();
        serviceGenerator.login();
        tokenManager = new TokenManager();
        tokenManager.set("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzb2xjaGFuQGdtYWlsLmNvbSIsInJvbGUiOiJ1c2VyIiwiaWF0IjoxNjI1OTQ1MDg2LCJleHAiOjE2MjU5NDg2ODZ9.uzw29Eiauodw6PQgQAYruWMR37q8rGaaJpY2o6Mf5Sygx9lnU7UJrThjhia_efVedHG2ppzcLmI8zl3eqnvMEQ");
        Log.d("token", tokenManager.get());

        serviceGenerator.test();

        /*
        signUp.findViewById(R.id.signup);
        login.findViewById(R.id.login);

         */




    }
}