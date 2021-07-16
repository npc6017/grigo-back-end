package com.devidea.grigoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageActivity extends AppCompatActivity {

    TextView tv_email, tv_name, tv_student_id, tv_sex, tv_phone, tv_birth, tv_tag;

    Button btn_updateProfile, btn_updatePass;

    UserDataDTO userDataDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        tv_email = findViewById(R.id.tv_email);
        tv_name = findViewById(R.id.tv_name);
        tv_student_id = findViewById(R.id.tv_student_id);
        tv_sex = findViewById(R.id.tv_sex);
        tv_phone = findViewById(R.id.tv_phone);
        tv_birth = findViewById(R.id.tv_birth);
        tv_tag = findViewById(R.id.tv_tag);

        btn_updatePass = findViewById(R.id.btn_updatePass);
        btn_updateProfile = findViewById(R.id.btn_updateProfile);

        Intent getIntent = getIntent();
        userDataDTO = (UserDataDTO) getIntent.getSerializableExtra("userDataDTO");

        btn_updateProfile.setOnClickListener(view -> {
            Intent intent = new Intent(MyPageActivity.this, UpdateProfileActivity.class);
            intent.putExtra("userDataDTO", userDataDTO);
            startActivity(intent);
        });

        btn_updatePass.setOnClickListener(view -> {
            Intent intent = new Intent(MyPageActivity.this, UpdatePassActivity.class);
            startActivity(intent);
        });

        getAccount();

    }

    public void getAccount(){

        tv_email.setText(userDataDTO.getEmail());
        tv_name.setText(userDataDTO.getName());
        tv_birth.setText(userDataDTO.getBirth());
        tv_student_id.setText(userDataDTO.getStudent_id()+"");
        tv_sex.setText(userDataDTO.getSex());
        tv_phone.setText(userDataDTO.getPhone());
        //tv_tag.setText(userDataDTO.getTags();
    }
}