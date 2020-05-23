package com.example.korpustokenandroidappnongit.apijsontranslator;

import androidx.annotation.Nullable;

import java.util.List;

public class Get_user_req_post {
    public List<String> params;
    public String token;
    public String message;
    public Integer team_id;

    public Get_user_req_post(List<String> params, String token, @Nullable Integer team_id){
        this.params = params;
        this.token = token;
        this.team_id = team_id;
    }
}
