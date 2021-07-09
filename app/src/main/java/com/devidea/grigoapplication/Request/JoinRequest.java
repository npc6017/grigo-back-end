package com.devidea.grigoapplication.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class JoinRequest extends StringRequest {

    //서버 URL을 설정
    final static private String URL = "http://solac.iptime.org:1234/account/join";
    private Map<String, String> map;

    public JoinRequest(String email, String password, String name, String birth, int student_id, String sex, String phone, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        map.put("name", name);
        map.put("birth", birth);
        map.put("student_id", student_id + "");
        map.put("sex", sex);
        map.put("phone", phone);

    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
