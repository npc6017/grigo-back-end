package com.devidea.grigoapplication;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonObject;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public JsonObject jsonObject;
    public RetrofitService retrofitService = retrofit.create(RetrofitService.class);
    public static final String BASE_URL = "http://solac.iptime.org:1234/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor("Bearer " + authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }

    public void signup() {
        jsonObject.addProperty("email", "test@naver.com");
        jsonObject.addProperty("password", "12345678a");
        jsonObject.addProperty("name", "jun");
        jsonObject.addProperty("birth", "980123");
        jsonObject.addProperty("student_id", "60172213");
        jsonObject.addProperty("sex", "man");
        jsonObject.addProperty("phone", "01012345678");

        retrofitService.setPostBody(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("서버", String.valueOf(response.body()));

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("서버", "실패");
            }
        });
    }

    public void login() {
        jsonObject.addProperty("email", "test@naver.com");
        jsonObject.addProperty("password", "12345678a");

        retrofitService.setPostBody(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Headers headers = response.headers();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("서버", "실패");
            }
        });

    }

}