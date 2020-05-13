package com.example.korpustokenandroidappnongit.request_methods;

import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;

public class KorpusTokenAPI {
    private static final String API_TAG = "API_ACTION";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final String BASE_URL = "http://" + "lk.korpus.io" + "/api/";

    private static final String USER_PATH = "users/";

    private static final String LOGIN = "login";
    private static final String REGISTER = "register";
    private static final String GET_USER = "get_user";

    public static final List<String> PARAMS = Arrays.asList(
            "EMAIL", "LOGIN", "TG_NICKNAME",
            "COURSES", "BIRTHDAY", "EDUCATION",
            "WORK_EXP", "SEX", "NAME",
            "SURNAME"
            );

    public KorpusTokenAPI() {}

    public String Login() {
        return BASE_URL+USER_PATH+LOGIN;
    }

    public String Register() {
        return BASE_URL+USER_PATH+REGISTER;
    }

    public String User() {
        return BASE_URL+USER_PATH+GET_USER;
    }
}
