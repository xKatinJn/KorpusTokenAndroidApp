package com.example.korpustokenandroidappnongit.apijsontranslator;

public class Get_teammates_req_post {
    public String token;
    public Integer team_id;

    public Get_teammates_req_post(String token, Integer team_id) {
        this.token = token;
        this.team_id = team_id;
    }
}
