package com.example.korpustokenandroidappnongit.request_methods;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import com.example.korpustokenandroidappnongit.MainActivity;
import com.example.korpustokenandroidappnongit.R;
import com.example.korpustokenandroidappnongit.apijsontranslator.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Get_user_method extends AsyncTask<String, Void, String> {
    private String json;
    private User user;
    private Activity activity;
    private List<String> params;
    private String URL = new KorpusTokenAPI().User();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public Get_user_method(String json, List<String> params, Activity activity){
        this.json = json;
        this.activity = activity;
        this.params = params;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, this.json.getBytes("UTF-8"));
            Request request = new Request.Builder().url(this.URL).method("POST", body).build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch (IOException e){
            Log.e("GET_USER_METHOD", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        this.user = new Gson().fromJson(response, User.class);
        Log.d("USER_GET_METHOD", "USER HAS BEEN RECEIVED");
        try {
            if (this.user.message.equals("Access denied")) {
                // do smth
            } else {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (this.params.get(0).equals("ALL")) {
                    for (String param : KorpusTokenAPI.PARAMS) {
                        try {
                            Field field = this.user.getClass().getField(param.toLowerCase());
                            field.setAccessible(true);
                            editor.putString(param, (String) field.get(this.user));
                            editor.commit();
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            Log.e("USER_GET", e.toString());
                        }
                    }
                } else {
                    for (String param : this.params) {
                        try {
                            Field field = this.user.getClass().getField(param.toLowerCase());
                            field.setAccessible(true);
                            editor.putString(param, (String) field.get(this.user));
                            editor.commit();
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            Log.e("USER_GET", e.toString());
                        }
                    }
                }
                if (this.activity.getClass() == MainActivity.class) {
                    TextView login = (TextView) this.activity.findViewById(R.id.nickname_textview);
                    TextView name_surname = (TextView) this.activity.findViewById(R.id.namesurname_textview);
                    login.setText(sharedPreferences.getString("LOGIN", "USER_LOGIN").toUpperCase());
                    name_surname.setText(sharedPreferences.getString("NAME", "USER_NAME") + " " + sharedPreferences.getString("SURNAME", "USER_SURNAME"));
                }
            }
        }catch (NullPointerException e){
            Log.e("USER_GET_METHOD", "NULL POINTER");
        }
    }

}
