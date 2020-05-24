package com.example.korpustokenandroidappnongit.request_methods;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.korpustokenandroidappnongit.QuestionnaireTeamActivity;
import com.example.korpustokenandroidappnongit.R;
import com.example.korpustokenandroidappnongit.apijsontranslator.Get_teammates_resp_post;
import com.example.korpustokenandroidappnongit.apijsontranslator.Questionnaire_team_resp_get;
import com.example.korpustokenandroidappnongit.scripts.UsefulScripts;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Get_teammates_method extends AsyncTask<String, Void, String> {
    private String json;
    private String URL = new KorpusTokenAPI().GetTeammates();
    private Questionnaire_team_method questionnaire_team_method;
    private Get_teammates_resp_post resp_post = new Get_teammates_resp_post();
    private Activity activity;
    private static final MediaType JSON = new KorpusTokenAPI().JSON;

    public Get_teammates_method(Questionnaire_team_method questionnaire_team_method, Activity activity, String json) {
        this.questionnaire_team_method = questionnaire_team_method;
        this.activity = activity;
        this.json = json;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, this.json.getBytes("UTF-8"));
            Request request = new Request.Builder().url(this.URL).post(body).build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch (IOException e){
            Log.e("QUESTIONNAIRE_METHOD", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        if (response != null){
            this.resp_post = new Gson().fromJson(response, this.resp_post.getClass());
            if (this.resp_post.message.equals("OK")){
                this.questionnaire_team_method.teammates = this.resp_post.teammates;
                Log.d("G_T_M", "TEAMMATES: "+this.resp_post.teammates.toString());
                Log.d("G_T_M", "TEAMMATES: "+this.questionnaire_team_method.teammates);
            }else{
                UsefulScripts.MakeToastError(this.activity, "Ошибка на серверной части. Пожалуйста, сообщите о проблеме разработчику", "#FF0000", true);
                Log.e("G_T_M", this.resp_post.message);
            }
        }else{
            Log.d("G_T_M", this.resp_post.message);
        }
        Log.d("G_T_M", "DONE");
    }
}
