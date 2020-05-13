package com.example.korpustokenandroidappnongit.apijsontranslator;

import java.util.List;

public class Get_user_req_post {
    public List<String> params;
    public String token;
    public String message;

    public Get_user_req_post(List<String> params, String token){
        this.params = params;
        this.token = token;
    }
}
