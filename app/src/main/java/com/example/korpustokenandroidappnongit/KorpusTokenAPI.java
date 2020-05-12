package com.example.korpustokenandroidappnongit;

import okhttp3.MediaType;

public class KorpusTokenAPI {
    private static final String API_TAG = "API_ACTION";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final String BASE_URL = "http://" + "lk.korpus.io" + "/api/";
    //R.string.localhostlocalhost:4093

    private static final String USER_PATH = "users/";

    private static final String LOGIN = "login";
    private static final String REGISTER = "register";
    private static final String USER = "user";

    public KorpusTokenAPI() {}

    public String Login() {
        return BASE_URL+USER_PATH+LOGIN;
    }

    public String Register() {
        return BASE_URL+USER_PATH+REGISTER;
    }

    public String User() {
        return BASE_URL+USER_PATH+USER;
    }
}
