package com.example.korpustokenandroidappnongit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.example.korpustokenandroidappnongit.apijsontranslator.User_registration_req_post;
import com.example.korpustokenandroidappnongit.apijsontranslator.User_registration_resp_get;
import com.example.korpustokenandroidappnongit.apijsontranslator.User_registration_resp_post;
import com.example.korpustokenandroidappnongit.request_methods.Registration_method;
import com.example.korpustokenandroidappnongit.scripts.UsefulScripts;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        final ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_main);

        final User_registration_resp_get resp_teams = new User_registration_resp_get();
        final List<String> teams = new ArrayList<>();
        Registration_method get_teams = new Registration_method(resp_teams, this, teams, "GET");
        get_teams.execute();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.registration);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

        final EditText email = (EditText) findViewById(R.id.email_edit);
        final EditText login = (EditText) findViewById(R.id.login_edit);
        final EditText password = (EditText) findViewById(R.id.password1_edit);
        final EditText password_repeat = (EditText) findViewById(R.id.password2_edit);
        final EditText name = (EditText) findViewById(R.id.name_edit);
        final EditText surname = (EditText) findViewById(R.id.surname_edit);
        final EditText tg_nickname = (EditText) findViewById(R.id.tg_nickname_edit);
        final DatePicker birthdate = (DatePicker) findViewById(R.id.birthdate_datepicker);
        final EditText education = (EditText) findViewById(R.id.education_edit);
        final EditText courses = (EditText) findViewById(R.id.courses_edit);
        final EditText work_exp = (EditText) findViewById(R.id.work_exp_edit);
        final String[] sex = {""};
        RadioGroup sex_radio_group = (RadioGroup) findViewById(R.id.sex_radio_group);
        sex_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.male_radio:
                        sex[0] = getResources().getString(R.string.sex_male);
                        break;
                    case R.id.female_radio:
                        sex[0] = getResources().getString(R.string.sex_female);
                        break;
                }
            }
        });

        Button confirm_button = (Button) findViewById(R.id.registration_button);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UsefulScripts.isOnline(RegistrationActivity.this)){
                    if (UsefulScripts.RegistrationValidation(Arrays.asList(login, name, surname, tg_nickname), email, password, password_repeat)){
                        String birthdate_str = birthdate.getDayOfMonth()+"-"+birthdate.getMonth()+"-"+birthdate.getYear();
                        User_registration_req_post req = new User_registration_req_post(
                                email.getText().toString(), login.getText().toString(),
                                password.getText().toString(), tg_nickname.getText().toString(),
                                courses.getText().toString(), birthdate_str,
                                sex[0], education.getText().toString(),
                                work_exp.getText().toString(), name.getText().toString(),
                                surname.getText().toString(), teams
                        );
                        System.out.println(sex[0]);
                        String json = new Gson().toJson(req);
                        User_registration_resp_post response = new User_registration_resp_post();
                        Registration_method registration_action = new Registration_method(
                                response, json, RegistrationActivity.this, "POST"
                        );
                        registration_action.execute();
                    }else{
                        scrollView.scrollTo(0, (int)email.getY());
                    }
                }else{
                    UsefulScripts.MakeToastInternetError(RegistrationActivity.this);
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}