package com.devidea.grigoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.devidea.grigoapplication.Request.JoinRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinActivity extends AppCompatActivity {

    private EditText et_email, et_password, et_name, et_student_id, et_phone, et_birth;
    private Spinner sp_sex;
    private Button btn_check_Id, btn_register;

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

        //중복확인 버튼
        btn_check_Id = findViewById(R.id.btn_checkID);
        btn_check_Id.setOnClickListener(view -> {
            //테스트
            Toast.makeText(getApplicationContext(), "중복된 아이디입니다.",Toast.LENGTH_SHORT).show();
        });

        //회원등록 버튼
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(view -> {
            //EditText에 입력된 값을 가져옴
            String email = et_email.getText().toString();
            String password = et_password.getText().toString();
            String name = et_name.getText().toString();
            String sex = sp_sex.getSelectedItem().toString();
            //int형변환
            int student_id = Integer.parseInt(et_student_id.getText().toString());
            String phone = et_phone.getText().toString();
            String birth = et_birth.getText().toString();

            /*제대로 입력받는지 테스트
            Log.v("email", email);
            Log.v("password", password);
            Log.v("name", name);
            Log.v("birth", birth);
            Log.d("studentId", student_id+"");
            Log.v("sex", sex);
            Log.v("phone", phone);*/

            Response.Listener<String> responseListener = response -> {
                try { // 회원가입에 성공
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        Toast.makeText(getApplicationContext(), "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else{ // 회원가입에 실패
                        Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            };

            JoinRequest joinRequest = new JoinRequest(email, password, name, birth, student_id, sex, phone, responseListener);
            RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
            queue.add(joinRequest);
        });
    }
}
