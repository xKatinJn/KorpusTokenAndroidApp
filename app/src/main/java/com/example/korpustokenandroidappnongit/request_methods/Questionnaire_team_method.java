package com.example.korpustokenandroidappnongit.request_methods;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.korpustokenandroidappnongit.QuestionnaireTeamActivity;
import com.example.korpustokenandroidappnongit.R;
import com.example.korpustokenandroidappnongit.apijsontranslator.Questionnaire_team_resp_get;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Questionnaire_team_method extends AsyncTask<String, Void, String> {
    private String json;
    private String request_method;
    private String URL = new KorpusTokenAPI().QuestionnaireTeam();
    private Questionnaire_team_resp_get resp_get = new Questionnaire_team_resp_get();
    private QuestionnaireTeamActivity activity;
    private static final MediaType JSON = new KorpusTokenAPI().JSON;

    public Questionnaire_team_method(QuestionnaireTeamActivity activity, String request_method) {
        this.activity = activity;
        this.request_method = request_method;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            if (this.request_method.equals("GET")) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(this.URL).build();
                Response response = client.newCall(request).execute();

//                String user_json = new Gson().toJson()
                return response.body().string();
            }else{

            }
        }catch (IOException e){
            Log.e("QUESTIONNAIRE_METHOD", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        if (this.request_method.equals("GET")){
            if (response != null){
                this.resp_get = new Gson().fromJson(response, this.resp_get.getClass());
                if (this.resp_get.message.equals("OK")){
                    LinearLayout linearLayout = (LinearLayout) this.activity.findViewById(R.id.linear_questions);
                    for (String question : this.resp_get.questions){
                        TextView textView = new TextView(this.activity);
                        textView.setText(question);
                        linearLayout.addView(textView);
                    }
                }
            }else{
                Log.d("QST_TEAM", this.resp_get.message);
            }
        }
    }
}
