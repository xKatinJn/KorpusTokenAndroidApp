package com.example.korpustokenandroidappnongit.apijsontranslator;

import java.util.Map;

public class Questionnaire_team_req_post {
    public String token;
    public Map<String, Integer> answers;

    public Questionnaire_team_req_post(String token, Map<String, Integer> answers) {
        this.token = token;
        this.answers = answers;
    }
}
