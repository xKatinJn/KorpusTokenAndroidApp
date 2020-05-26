package com.example.korpustokenandroidappnongit.request_methods;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.korpustokenandroidappnongit.MainActivity;
import com.example.korpustokenandroidappnongit.QuestionnaireSelfActivity;
import com.example.korpustokenandroidappnongit.QuestionnaireTeamActivity;
import com.example.korpustokenandroidappnongit.R;
import com.example.korpustokenandroidappnongit.apijsontranslator.Get_status_resp_post;
import com.example.korpustokenandroidappnongit.scripts.UsefulScripts;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Get_status_method extends AsyncTask<String, Void, String> {
    private String json;
    private Activity activity;
    private String URL = new KorpusTokenAPI().GetStatus();
    private MediaType JSON = KorpusTokenAPI.JSON;
    private Get_status_resp_post resp_post = new Get_status_resp_post();

    public Get_status_method(String json, Activity activity) {
        this.json = json;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(this.JSON, this.json.getBytes("UTF-8"));
            Request request = new Request.Builder().url(this.URL).method("POST", body).build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            Log.d("GET_STATUS", "ERROR IN doInBackground"+e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        if (response != null) {
            this.resp_post = new Gson().fromJson(response, this.resp_post.getClass());
            if (this.resp_post.message.equals("OK")){
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("ASSESSMENT_IS_OPENED", this.resp_post.assessment_is_opened);
                editor.putBoolean("QUESTIONNAIRE_IS_OPENED", this.resp_post.questionnaire_is_opened);
                editor.putString("ASSESSMENT_MONTH", this.resp_post.assessment_month);
                if (this.activity.getClass() == MainActivity.class){
                    TextView qst_status = this.activity.findViewById(R.id.qst_status);
                    if (this.resp_post.questionnaire_is_opened) {
                        qst_status.setText("Открыто");
                        qst_status.setTextColor(Color.GREEN);
                        Log.d("GET_STATUS", Boolean.toString(sharedPreferences.getBoolean("QUESTIONNAIRE_SELF", true)));
                        if (sharedPreferences.getBoolean("QUESTIONNAIRE_SELF", true)){
                            TextView qst_self = this.activity.findViewById(R.id.qst_self);
                            TextView qst_self_label = this.activity.findViewById(R.id.qst_self_label);
                            qst_self_label.setVisibility(View.VISIBLE);
                            qst_self.setVisibility(View.VISIBLE);
                            qst_self.setText("Не пройдена");
                            qst_self.setTextColor(Color.RED);
                            qst_self.setClickable(true);
                            qst_self.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int questionnaire_self_request = 0;
                                    activity.startActivityForResult(new Intent(activity, QuestionnaireSelfActivity.class), questionnaire_self_request);
                                    Log.d("MAIN_ACTIVITY", "QUESTIONNAIRE_SELF");
                                }
                            });
                        }else{
                            TextView qst_self = this.activity.findViewById(R.id.qst_self);
                            TextView qst_self_label = this.activity.findViewById(R.id.qst_self_label);
                            qst_self_label.setVisibility(View.VISIBLE);
                            qst_self.setVisibility(View.VISIBLE);
                            qst_self.setText("Пройдена");
                            qst_self.setTextColor(Color.GREEN);
                        }
                        if (sharedPreferences.getBoolean("QUESTIONNAIRE_TEAM", true)) {
                            TextView qst_team = this.activity.findViewById(R.id.qst_team);
                            TextView qst_team_label = this.activity.findViewById(R.id.qst_team_label);
                            qst_team_label.setVisibility(View.VISIBLE);
                            qst_team.setVisibility(View.VISIBLE);
                            qst_team.setText("Не пройдена");
                            qst_team.setTextColor(Color.RED);
                            qst_team.setClickable(true);
                            qst_team.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int questionnaire_team_request = 1;
                                    activity.startActivityForResult(new Intent(activity, QuestionnaireTeamActivity.class), questionnaire_team_request);
                                    Log.d("MAIN_ACTIVITY", "QUESTIONNAIRE_TEAM");
                                }
                            });
                        }else{
                            TextView qst_team = this.activity.findViewById(R.id.qst_team);
                            TextView qst_team_label = this.activity.findViewById(R.id.qst_team_label);
                            qst_team_label.setVisibility(View.VISIBLE);
                            qst_team.setVisibility(View.VISIBLE);
                            qst_team.setText("Пройдена");
                            qst_team.setTextColor(Color.GREEN);
                            Log.d("MAIN_ACTIVITY", "QUESTIONNAIRE_TEAM");
                        }
                    }else{
                        qst_status.setText("Закрыто");
                        qst_status.setTextColor(Color.RED);
                    }
                    Log.d("GET_STATUS", response);
                }
            }else{
                UsefulScripts.MakeToastError(this.activity, "Ошибка на серверной части. Пожалуйста, сообщите о проблеме разработчику", "#FF0000", true);
            }
        }
    }
}
