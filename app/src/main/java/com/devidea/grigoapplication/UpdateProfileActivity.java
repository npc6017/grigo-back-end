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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {

    ServiceGenerator serviceGenerator;
    RetrofitService retrofitService;
    TokenManager tokenManager;

    EditText et_updateBirth, et_updatePhone;
    Button btn_upProfile;

    UserDataDTO userDataDTO;

    MyPageActivity myPageActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        serviceGenerator = new ServiceGenerator();
        tokenManager = new TokenManager();
        retrofitService = serviceGenerator.createService(RetrofitService.class, tokenManager.get());

        Intent getIntent = getIntent();
        userDataDTO = (UserDataDTO) getIntent.getSerializableExtra("userDataDTO");

        et_updateBirth = findViewById(R.id.et_updateBirth);
        et_updatePhone = findViewById(R.id.et_updatePhone);

        btn_upProfile = findViewById(R.id.btn_upProfile);
        btn_upProfile.setOnClickListener(view -> {
            upDateData(et_updateBirth.getText().toString(), et_updatePhone.getText().toString());
        });
    }

    public void upDateData(String upBirth, String upPhone){

        //변경할 생년월일이 빈 값
        if(upBirth.equals("")){
            upBirth = userDataDTO.getBirth();
        }
        //변경할 폰번호가 빈 값
        else if(upPhone.equals("")){
            upPhone = userDataDTO.getPhone();
        }

        //변경할 생년월일, 폰번호가 모두 빈 값
        else if(upBirth.equals("") && upPhone.equals("")){
            upBirth = userDataDTO.getBirth();
            upPhone = userDataDTO.getPhone();
        }

        JsonObject jsonObjectUpProfile = new JsonObject();

        jsonObjectUpProfile.addProperty("birth", upBirth);
        jsonObjectUpProfile.addProperty("phone", upPhone);

        retrofitService.updateProfile(jsonObjectUpProfile).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                userDataDTO = new Gson().fromJson(response.body(), UserDataDTO.class);
                Intent intent = new Intent(UpdateProfileActivity.this, MyPageActivity.class);
                //userDataDTO를 수정된 값으로 다시 변경
                intent.putExtra("userDataDTO",userDataDTO);
                startActivity(intent);
                Log.d("변경 : ", String.valueOf(response.body()));
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}