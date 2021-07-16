package com.devidea.grigoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login, btn_join;
    private EditText et_id, et_pw;

    ServiceGenerator serviceGenerator;
    TokenManager tokenManager;
    RetrofitService retrofitService;
    UserDataDTO userDataDTO;
    MyPageActivity myPageActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PrefsHelper.init(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        serviceGenerator = new ServiceGenerator();
        retrofitService = serviceGenerator.createService(RetrofitService.class);
        tokenManager = new TokenManager();

        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);

        //회원가입 버튼
        btn_join = findViewById(R.id.btn_join);
        btn_join.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,JoinActivity.class);
            startActivity(intent);
        });

        //로그인 시도
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(view -> {

            /*테스트
            PrefsHelper.write("email",  "hb1234@naver.com");
            PrefsHelper.write("name",  "hyeon");
            PrefsHelper.write("student_id",  "60172171");
            PrefsHelper.write("phone",  "userDataDTO");
            PrefsHelper.write("birth",  "123123123");
            PrefsHelper.write("sex",  "male");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
             */
            login(et_id.getText().toString(), et_pw.getText().toString());

        });
    }

    public void login(String user_id, String pw) {
        JsonObject jsonObjectLogin = new JsonObject();

        jsonObjectLogin.addProperty("email", user_id);
        jsonObjectLogin.addProperty("password", pw);

        retrofitService.login(jsonObjectLogin).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Headers headers = response.headers();
                String token = headers.get("Authorization");

                System.out.println(response.headers());

                tokenManager.set(token);

                Log.d("token", tokenManager.get());

                Log.d("성공 : ", String.valueOf(response.body()));
                userDataDTO = new Gson().fromJson(response.body(), UserDataDTO.class);
                //userDataDTO에 Mapping된 변수들을 prefs에 저장
                PrefsHelper.write("email",  userDataDTO.getEmail());
                PrefsHelper.write("name",  userDataDTO.getName());
                PrefsHelper.write("student_id",  userDataDTO.getStudent_id());
                PrefsHelper.write("phone",  userDataDTO.getPhone());
                PrefsHelper.write("birth",  userDataDTO.getBirth());
                PrefsHelper.write("sex",  userDataDTO.getSex());
                //PrefsHelper.write("tags",  userDataDTO.getEmail());

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });


    }

}