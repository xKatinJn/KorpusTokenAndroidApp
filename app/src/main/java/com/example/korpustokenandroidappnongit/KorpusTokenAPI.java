package com.example.korpustokenandroidappnongit;

public class KorpusTokenAPI {
    private static final String API_TAG = "API_ACTION";
    private static final String BASE_URL = "http://" + "localhost:4093" + "/api/";
    //R.string.localhost

    private static final String USER_PATH = "users/";

    private static final String LOGIN = "login";
    private static final String REGISTER = "register";

    public KorpusTokenAPI() {}

    public String Login() {
        return BASE_URL+USER_PATH+LOGIN;
    }

    public String Register() {
        return BASE_URL+USER_PATH+REGISTER;
    }
}
