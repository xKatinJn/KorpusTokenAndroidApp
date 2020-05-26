package com.example.korpustokenandroidappnongit.request_methods;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.korpustokenandroidappnongit.QuestionnaireTeamActivity;
import com.example.korpustokenandroidappnongit.R;
import com.example.korpustokenandroidappnongit.apijsontranslator.Get_teammates_req_post;
import com.example.korpustokenandroidappnongit.apijsontranslator.Questionnaire_team_resp_get;
import com.example.korpustokenandroidappnongit.apijsontranslator.Questionnaire_team_resp_post;
import com.example.korpustokenandroidappnongit.scripts.UsefulScripts;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Questionnaire_team_method extends AsyncTask<String, Void, String> {
    private String json;
    private String request_method;
    public static Map<String, Integer> teammates;
    private Map<TextView, RadioGroup> questions_container;
    private String URL = new KorpusTokenAPI().QuestionnaireTeam();
    private Questionnaire_team_resp_get resp_get = new Questionnaire_team_resp_get();
    private Questionnaire_team_resp_post resp_post = new Questionnaire_team_resp_post();
    private QuestionnaireTeamActivity activity;
    private static final MediaType JSON = new KorpusTokenAPI().JSON;

    public Questionnaire_team_method(QuestionnaireTeamActivity activity,
                                     Map<TextView, RadioGroup> questions_container, String request_method) {
        this.activity = activity;
        this.questions_container = questions_container;
        this.request_method = request_method;
    }

    public Questionnaire_team_method(String json, String request_method) {
        this.json = json;
        this.request_method = request_method;
    }

    @Override
    protected void onPreExecute() {
        if (this.request_method.equals("GET")) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
            String json = new Gson().toJson(new Get_teammates_req_post(sharedPreferences.getString("TOKEN", "EMPTY"), sharedPreferences.getInt("TEAMS", 0)));
            Get_teammates_method get_teammates_method = new Get_teammates_method(this, this.activity, json);
            get_teammates_method.execute();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            if (this.request_method.equals("GET")) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(this.URL).build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            }else{
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(JSON, this.json.getBytes("UTF-8"));
                Request request = new Request.Builder().url(this.URL).post(body).build();
                Response response = client.newCall(request).execute();
                return response.body().string();
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
                    Log.d("QST_TEAM", "TEAMMATES GETTING");
                    Log.d("QST_TEAM", "TEAMMATES: "+this.teammates);
                    for (String question : this.resp_get.questions){
                        TextView textView = new TextView(this.activity);
                        textView.setTextAppearance(this.activity, R.style.Text_Main);
                        textView.setText(question);

                        RadioGroup radioGroup = (RadioGroup) this.activity.getLayoutInflater().inflate(R.layout.activity_questionnaire_team_radiogroup, null);
                        int dp = UsefulScripts.ConvertDpToPx(5, this.activity.getResources().getDisplayMetrics());
                        radioGroup.setPadding(dp, dp, dp, dp);
                        for (String teammate : this.teammates.keySet()){
                            RadioButton radioButton = (RadioButton) this.activity.getLayoutInflater().inflate(R.layout.activity_questionnaire_team_radiobutton, null);
                            radioButton.setText(teammate);
                            radioButton.setId(this.teammates.get(teammate));
                            radioGroup.addView(radioButton);
                        }
                        textView.setLabelFor(radioGroup.getId());

                        View divider_1 = new View(new ContextThemeWrapper(this.activity, R.style.DividingView));
                        divider_1.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UsefulScripts.ConvertDpToPx(15, this.activity.getResources().getDisplayMetrics())));

                        linearLayout.addView(textView);
                        linearLayout.addView(radioGroup);
                        linearLayout.addView(divider_1);

                        this.questions_container.put(textView, radioGroup);
                        Log.d("QST_TEAM", "ALL_ADDED");
                    }
                    this.activity.findViewById(R.id.questionnaire_confirm_button).setVisibility(View.VISIBLE);
                }
            }else{
                Log.d("QST_TEAM", this.resp_get.message);
            }
        }else{
            if (response != null){
                this.resp_post = new Gson().fromJson(response, this.resp_post.getClass());
            }
        }
    }
}
