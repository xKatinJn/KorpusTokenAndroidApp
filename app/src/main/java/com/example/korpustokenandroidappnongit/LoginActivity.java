package com.example.korpustokenandroidappnongit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.AndroidRuntimeException;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.korpustokenandroidappnongit.apijsontranslator.User_login_req_post;
import com.example.korpustokenandroidappnongit.apijsontranslator.User_login_resp_post;
import com.example.korpustokenandroidappnongit.request_methods.Login_method;
import com.example.korpustokenandroidappnongit.scripts.UsefulScripts;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        final EditText login = (EditText) findViewById(R.id.login_edit);
        final EditText password = (EditText) findViewById(R.id.password_edit);

        Button registration_button = (Button) findViewById(R.id.registration_button);
        Button confirm_button = (Button) findViewById(R.id.login_button);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UsefulScripts.isOnline(LoginActivity.this)) {
                    if (UsefulScripts.EmptyFieldValidation(Arrays.asList(login, password))) {
                        try {
                            User_login_req_post req = new User_login_req_post(login.getText().toString(), password.getText().toString());
                            User_login_resp_post login_resp = new User_login_resp_post();
                            Gson gson = new Gson();
                            String json = gson.toJson(req);
                            Login_method login_action = new Login_method(login_resp, json, LoginActivity.this, "POST");
                            login_action.execute();
                        }catch (Exception e){
                            UsefulScripts.MakeToastError(LoginActivity.this, "Ошибка на серверной части. Пожалуйста, сообщите о проблеме разработчику", "#FF0000", true);
                        }
                    }
                }else{
                    UsefulScripts.MakeToastInternetError(LoginActivity.this);
                }
            }
        });
        registration_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}
