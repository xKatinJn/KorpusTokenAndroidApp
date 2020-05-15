package com.example.korpustokenandroidappnongit.apijsontranslator;

import com.example.korpustokenandroidappnongit.QuestionnaireSelfActivity;

import java.util.Map;

public class Questionnaire_self_req_post {
    public Map<String, String> answers;
    public String token;

    public Questionnaire_self_req_post(Map<String, String> answers, String token) {
        this.answers = answers;
        this.token = token;
    }
}
