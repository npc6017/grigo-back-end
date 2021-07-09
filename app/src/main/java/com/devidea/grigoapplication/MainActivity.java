package com.devidea.grigoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PrefsHelper.init(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tokenManager = new TokenManager();

        //if(자동로그인 분기 만들기)


    }
}