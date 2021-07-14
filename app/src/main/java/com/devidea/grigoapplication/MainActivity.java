package com.devidea.grigoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    /*
    Button signUp;
    Button login;
    Button test;

     */
    Toolbar toolbar;
    ServiceGenerator serviceGenerator;
    //TokenManager tokenManager;
    RetrofitService retrofitService;

    //Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            //테스트
            case R.id.menu_search:
                Toast.makeText(this,"검색",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_mypage:
                //Toast.makeText(this,"설정",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MyPageActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_logout:
                Toast.makeText(this,"로그아웃",Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PrefsHelper.init(getApplicationContext());
        serviceGenerator = new ServiceGenerator();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //app 제목 -> 추후에 app 이름 정해지면 수정
        getSupportActionBar().setTitle("Title");

        retrofitService = serviceGenerator.createService(RetrofitService.class);
/*
        signUp = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        test = findViewById(R.id.test);

 */
/*
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


*/

    }


}