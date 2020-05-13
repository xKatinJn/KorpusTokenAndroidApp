package com.example.korpustokenandroidappnongit.request_methods;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.korpustokenandroidappnongit.LoginActivity;
import com.example.korpustokenandroidappnongit.MainActivity;
import com.example.korpustokenandroidappnongit.R;
import com.example.korpustokenandroidappnongit.apijsontranslator.User_login_resp_post;
import com.example.korpustokenandroidappnongit.scripts.UsefulScripts;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login_method extends AsyncTask<String, Void, String> {
    public User_login_resp_post login;
    private LoginActivity activity;
    private String json;
    private String request_method;
    private String URL;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public Login_method(User_login_resp_post login, String json, LoginActivity activity, String request_method) {
        this.login = login;
        this.json = json;
        this.request_method = request_method;
        this.URL = new KorpusTokenAPI().Login();
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        try{
            if (this.request_method == "POST"){
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(JSON, this.json.getBytes("UTF-8"));
                Request request = new Request.Builder().url(this.URL).post(body).build();

                Response response = client.newCall(request).execute();
                return response.body().string();
            }else{

            }
        }catch (IOException e){
            Log.e("LOGIN_METHOD: ", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String resp) {
        if (resp != null) {
            try {
                this.login = new Gson().fromJson(resp, User_login_resp_post.class);
                System.out.println(login.message);
                if (this.login.message.equals("Logged")) {
                    SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
                    SharedPreferences.Editor editor = myPreferences.edit();
                    editor.putString("TOKEN", login.token);
                    editor.commit();
                    activity.finishAffinity();
                    activity.startActivity(new Intent(this.activity, MainActivity.class));
                } else if (this.login.message.equals("Login or password incorrect")) {
                    activity.findViewById(R.id.login_edit).setBackgroundResource(R.drawable.rounded_edittext_top_error);
                    activity.findViewById(R.id.password_edit).setBackgroundResource(R.drawable.rounded_edittext_bottom_error);
                    UsefulScripts.MakeToastError(this.activity, "Логин или пароль введены неверно", "#FF0000", true);
                }
            }catch (Exception e){
                throw e;
            }
        }
    }
}