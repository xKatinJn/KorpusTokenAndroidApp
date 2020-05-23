package com.example.korpustokenandroidappnongit.request_methods;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.korpustokenandroidappnongit.MainActivity;
import com.example.korpustokenandroidappnongit.QuestionnaireSelfActivity;
import com.example.korpustokenandroidappnongit.R;
import com.example.korpustokenandroidappnongit.apijsontranslator.Get_user_req_post;
import com.example.korpustokenandroidappnongit.apijsontranslator.Questionnaire_self_resp_get;
import com.example.korpustokenandroidappnongit.apijsontranslator.Questionnaire_self_resp_post;
import com.example.korpustokenandroidappnongit.scripts.UsefulScripts;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Questionnaire_self_method extends AsyncTask<String, Void, String> {
    private String json;
    public Map<String, EditText> questions_fields;
    private String request_method;
    private String URL = new KorpusTokenAPI().QuestionnaireSelf();;
    private Questionnaire_self_resp_post resp_post;
    private Questionnaire_self_resp_get resp_get = new Questionnaire_self_resp_get();
    private QuestionnaireSelfActivity activity;
    public static final MediaType JSON = new KorpusTokenAPI().JSON;


    public Questionnaire_self_method(QuestionnaireSelfActivity activity, Map<String, EditText> questions_fields, String request_method) {
        this.activity = activity;
        this.questions_fields = questions_fields;
        this.request_method = request_method;
    }

    public Questionnaire_self_method(QuestionnaireSelfActivity activity, String json, String request_method) {
        this.json = json;
        this.activity = activity;
        this.request_method = request_method;
    }

    @Override
    protected String doInBackground(String... strings) {
        try{
            if (this.request_method.equals("GET")){
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
        if (this.request_method.equals("GET")) {
            if (response != null) {
                try {
                    this.resp_get = new Gson().fromJson(response, this.resp_get.getClass());
                    if (this.resp_get.message.equals("OK")) {
                        LinearLayout question_container = (LinearLayout) this.activity.findViewById(R.id.relative_questionnaire_self_questions);
                        for (String question : this.resp_get.questions) {
                            TextView textView = new TextView(this.activity);
                            textView.setTextAppearance(this.activity, R.style.Text_Main);
                            textView.setText(question);

                            EditText editText = (EditText) activity.getLayoutInflater().inflate(R.layout.activity_questionnaire_self_edittext, null);
                            textView.setLabelFor(editText.getId());


                            View divider_1 = new View(new ContextThemeWrapper(this.activity, R.style.DividingView));
                            divider_1.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UsefulScripts.ConvertDpToPx(15, this.activity.getResources().getDisplayMetrics())));
                            questions_fields.put(question, editText);

                            question_container.addView(textView);
                            View divider_2 = new View(new ContextThemeWrapper(this.activity, R.style.DividingView));
                            divider_2.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UsefulScripts.ConvertDpToPx(10, this.activity.getResources().getDisplayMetrics())));
                            question_container.addView(divider_2);
                            question_container.addView(editText);
                            question_container.addView(divider_1);
                        }
                    }
                } catch (Exception e) {
                    Log.d("QSM", " GET ERR: " + response);

                }
            }
        }else{
            try{
                if (response != null) {
                    this.resp_post = new Gson().fromJson(response, Questionnaire_self_resp_post.class);
                }
                //UsefulScripts.ReloadActivity(this.main_activity);
//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
//                String new_json = new Gson().toJson(new Get_user_req_post(Arrays.asList("QUESTIONNAIRE_SELF"), (String) sharedPreferences.getString("TOKEN", "EMPTY")));
//                new Get_user_method(new_json, Arrays.asList("QUESTIONNAIRE_SELF"), this.activity).execute();
            }catch (Exception e) {
                Log.d("ERROR_QSM", " RESPONSE: "+response);
                throw new NoSuchElementException(resp_post.message);
            }
        }
    }
}
