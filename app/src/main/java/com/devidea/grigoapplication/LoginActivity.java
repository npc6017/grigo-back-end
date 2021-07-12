package com.devidea.grigoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login, btn_join;
    private EditText et_id, et_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);

        //회원가입 버튼
        btn_join = findViewById(R.id.btn_join);
        btn_join.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,JoinActivity.class);
            startActivity(intent);
        });

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(view -> {

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}