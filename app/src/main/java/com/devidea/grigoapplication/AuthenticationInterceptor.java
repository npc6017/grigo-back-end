package com.devidea.grigoapplication;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationInterceptor implements Interceptor {

    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
        Log.d("intercepter", authToken);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("intercepter", authToken);
        Log.d("intercepter-inner", authToken);
        //.header("Authorization", authToken);

        Request request = builder.build();
        return chain.proceed(request);
    }
}