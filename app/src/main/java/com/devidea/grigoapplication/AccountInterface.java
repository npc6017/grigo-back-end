package com.devidea.grigoapplication;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccountInterface {
    String URL = "http://solac.iptime.org:1234/";

    //회원가입
    @Headers("Content-Type: application/json")
    @POST("account/join")
    Call<JsonObject> getAccountJoin(@Body JsonObject jsonObject);

}
