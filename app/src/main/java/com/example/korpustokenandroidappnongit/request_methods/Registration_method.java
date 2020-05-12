package com.example.korpustokenandroidappnongit.request_methods;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.korpustokenandroidappnongit.KorpusTokenAPI;
import com.example.korpustokenandroidappnongit.MainActivity;
import com.example.korpustokenandroidappnongit.R;
import com.example.korpustokenandroidappnongit.RegistrationActivity;
import com.example.korpustokenandroidappnongit.apijsontranslator.User_registration_resp_get;
import com.example.korpustokenandroidappnongit.apijsontranslator.User_registration_resp_post;
import com.example.korpustokenandroidappnongit.apijsontranslator.User_req_get;
import com.example.korpustokenandroidappnongit.scripts.UsefulScripts;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Registration_method extends AsyncTask<String, Void, String> {
    public User_registration_resp_get resp_get;
    public User_registration_resp_post resp_post;
    public List<String> teams;
    private String json;
    private String request_method;
    private String URL;
    private RegistrationActivity activity;
    public static final MediaType JSON = new KorpusTokenAPI().JSON;

    //GET
    public Registration_method(User_registration_resp_get resp,
                               RegistrationActivity activity, List<String> teams,
                               String request_method) {
        this.resp_get = resp;
        this.request_method = request_method;
        this.URL = new KorpusTokenAPI().Register();
        this.activity = activity;
        this.teams = teams;
    }

    //POST
    public Registration_method(User_registration_resp_post resp, String json,
                               RegistrationActivity activity, String request_method) {
        this.resp_post = resp;
        this.json = json;
        this.request_method = request_method;
        this.URL = new KorpusTokenAPI().Register();
        this.activity = activity;
    }


    @Override
    protected String doInBackground(String... strings) {
        try {
            if (request_method == "GET") {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(this.URL).build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            }else{
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(JSON, this.json.getBytes("UTF-8"));
                Request request = new Request.Builder().url(this.URL).post(requestBody).build();

                Response response = client.newCall(request).execute();
                return response.body().string();
            }
        }catch (IOException e){
            Log.e("LOGIN_METHOD: ", e.toString());
        }

            return null;
    }

    @Override
    protected void onPostExecute(String response) {
        if (this.request_method == "GET") {
            this.resp_get = new Gson().fromJson(response, User_registration_resp_get.class);
            LinearLayout team_linear_layout = (LinearLayout) this.activity.findViewById(R.id.team_linear_child_layout);
            try {
                ArrayList<CheckBox> team_checkboxes = new ArrayList<>();
                for (String team_name : this.resp_get.teams) {
                    CheckBox checkBox = new CheckBox(this.activity);
                    checkBox.setText(team_name);
                    checkBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean checked = ((CheckBox) v).isChecked();

                            if (checked) {
                                String team = ((CheckBox) v).getText().toString();
                                teams.add(team);
                            } else {
                                teams.remove(((CheckBox) v).getText().toString());
                            }
                        }
                    });
                    team_checkboxes.add(checkBox);
                }
                for (CheckBox checkBox : team_checkboxes) {
                    team_linear_layout.addView(checkBox);
                }
            } catch (NullPointerException e) {
                TextView err = new TextView(this.activity, null, 0, R.style.Text_Error);
                err.setText(R.string.server_error);
                team_linear_layout.addView(err);
            }
        }else{
            this.resp_post = new Gson().fromJson(response, User_registration_resp_post.class);
            if (!this.resp_post.validation_errors.isEmpty()){
                boolean flag = false;
                for (String error : this.resp_post.validation_errors){
                    if (error.equals("email")){
                        EditText email = (EditText) activity.findViewById(R.id.email_edit);
                        UsefulScripts.MakeToastError(this.activity, "Почта уже используется", "#FF0000", true);
                        email.setBackgroundResource(R.drawable.rounded_edittext_error);
                        flag = true;
                    }else if (error.equals("login")){
                        EditText login = (EditText) activity.findViewById(R.id.login_edit);
                        UsefulScripts.MakeToastError(this.activity, "Логин уже используется", "#FF0000", true);
                        login.setBackgroundResource(R.drawable.rounded_edittext_error);
                        flag = true;
                    }
                    if (flag){
                        activity.findViewById(R.id.scroll_main).scrollTo(0, 0);
                    }
                }
            }else{
                SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
                SharedPreferences.Editor editor = myPreferences.edit();
                editor.putString("TOKEN", this.resp_post.token);
                editor.commit();
                activity.startActivity(new Intent(activity, MainActivity.class));
            }
            System.out.println(this.resp_post.message);
        }
    }
}
