package com.devidea.grigoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.JsonObject;

public class MyPageActivity extends AppCompatActivity {

    TextView tv_email, tv_password, tv_name, tv_student_id, tv_sex, tv_phone, tv_birth;

    ServiceGenerator serviceGenerator;

    RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        tv_email = findViewById(R.id.tv_email);
        tv_password = findViewById(R.id.tv_password);
        tv_name = findViewById(R.id.tv_name);
        tv_student_id = findViewById(R.id.tv_student_id);
        tv_sex = findViewById(R.id.tv_sex);
        tv_phone = findViewById(R.id.tv_phone);
        tv_birth = findViewById(R.id.tv_birth);

        retrofitService = serviceGenerator.createService(RetrofitService.class);

        getAccount();

    }

    private void getAccount(){
        JsonObject jsonObject = new JsonObject();

    }
}