package com.devidea.grigoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.devidea.grigoapplication.LoginActivity.retrofitService;

public class TagInputActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    TextInputLayout inputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_input);

        inputLayout = findViewById(R.id.input_layout);
        EditText editText = inputLayout.getEditText();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().contains(" ")) {
                    inputLayout.setError("공백없이 입력해주세요");
                } else {
                    inputLayout.setError(null); // null은 에러 메시지를 지워주는 기능
                }
            }
        });

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] tag = editText.getText().toString().split("#");

                tagSend(tag);

            }

        });


    }

    private void tagSend(String[] tag){
        JsonObject jsonObject = new JsonObject();
        //jsonObject.add("tag",tag);
        JsonArray jsonArray = new JsonArray();
        for (int i = 1; i < tag.length; i++) {
            jsonArray.add(tag[i]);
        }
        jsonObject.add("tag", jsonArray);
        Log.d("tag", String.valueOf(jsonObject));

        retrofitService.tagPost(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}