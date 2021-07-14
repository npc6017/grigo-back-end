package com.devidea.grigoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JoinActivity extends AppCompatActivity {

    private EditText et_email, et_password, et_name, et_student_id, et_phone, et_birth;
    private Spinner sp_sex;
    private Button btn_check_Id, btn_register;

    ServiceGenerator serviceGenerator;

    //서버 통신을 위함
    private RetrofitService joinRetrofit;
    //private RetrofitService joinService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //변수 지정
        et_email = findViewById(R.id.et_id);
        et_password = findViewById(R.id.et_pass);
        et_name = findViewById(R.id.et_name);
        et_student_id = findViewById(R.id.et_studentId);
        et_phone = findViewById(R.id.et_phone);
        et_birth = findViewById(R.id.et_birth);

        sp_sex = findViewById(R.id.sp_sex);

        serviceGenerator = new ServiceGenerator();
        joinRetrofit = serviceGenerator.retrofitService;
        //joinService = serviceGenerator.createService(RetrofitService.class);

        //중복확인 버튼
        btn_check_Id = findViewById(R.id.btn_checkID);
        btn_check_Id.setOnClickListener(view -> {
            //테스트
            //Toast.makeText(getApplicationContext(), "중복된 아이디입니다.",Toast.LENGTH_SHORT).show();
        });

        //회원등록 버튼
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        //EditText에 입력된 값을 가져옴
        final String email = et_email.getText().toString();
        final String password = et_password.getText().toString();
        final String name = et_name.getText().toString();
        final String sex = sp_sex.getSelectedItem().toString();
        //int형변환
        int student_id = Integer.parseInt(et_student_id.getText().toString());
        final String phone = et_phone.getText().toString();
        final String birth = et_birth.getText().toString();

        /*제대로 입력받는지 테스트
        Log.d("email", email);
        Log.d("password", password);
        Log.d("name", name);
        Log.d("birth", birth);
        Log.d("studentId", student_id+"");
        Log.d("sex", sex);
        Log.d("phone", phone);
        */

        //Json 객체 생성
        JsonObject jsonObject = new JsonObject();

        //회원가입 정보에 입력되는 값을 json으로 변환
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("birth", birth);
        jsonObject.addProperty("student_id", student_id);
        jsonObject.addProperty("sex", sex);
        jsonObject.addProperty("phone", phone);

        //api 호출
        joinRetrofit.signup(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    //성공
                    Log.d("성공 : ", String.valueOf(response.body()));
                }
                else{
                   //실패 : 이메일 형식 x,중복, 비밀번호 8자리 이하...
                    Log.d("실패 : ", String.valueOf(response.body()));
                }
            }

            //시스템 오류로 인한 실패
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("실패 : ", t.getMessage());
            }
        });
    }
}
