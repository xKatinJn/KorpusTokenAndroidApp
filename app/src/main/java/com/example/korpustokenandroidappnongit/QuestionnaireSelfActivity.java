package com.example.korpustokenandroidappnongit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.korpustokenandroidappnongit.apijsontranslator.Get_user_req_post;
import com.example.korpustokenandroidappnongit.apijsontranslator.Questionnaire_self_req_post;
import com.example.korpustokenandroidappnongit.request_methods.Get_user_method;
import com.example.korpustokenandroidappnongit.request_methods.Questionnaire_self_method;
import com.example.korpustokenandroidappnongit.scripts.UsefulScripts;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class QuestionnaireSelfActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_self);

        if (!UsefulScripts.isOnline(QuestionnaireSelfActivity.this)){
            UsefulScripts.MakeToastInternetError(QuestionnaireSelfActivity.this);
            finish();
        }else{
            final Map<String, EditText> questions = new HashMap<String, EditText>();
            Questionnaire_self_method questionnaire_self_action = new Questionnaire_self_method(QuestionnaireSelfActivity.this, questions, "GET");
            questionnaire_self_action.execute();

            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(R.string.questionnaire_self);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(true);

            Button confirm_button = (Button) findViewById(R.id.questionnaire_confirm_button);
            confirm_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (UsefulScripts.isOnline(QuestionnaireSelfActivity.this)){
                        ArrayList<EditText> questions_edittext = new ArrayList<EditText>();
                        for (EditText editText : questions.values()){
                            questions_edittext.add(editText);
                        }
                        if (UsefulScripts.EmptyFieldValidation(questions_edittext)){
                            Map<String, String> answers = new HashMap<String, String>();
                            for (String question : questions.keySet()){
                                answers.put(question, questions.get(question).getText().toString());
                            }
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(QuestionnaireSelfActivity.this);
                            String json = new Gson().toJson(new Questionnaire_self_req_post(answers, sharedPreferences.getString("TOKEN", "EMPTY")));
                            Questionnaire_self_method questionnaire_post_action = new Questionnaire_self_method(QuestionnaireSelfActivity.this, json, "POST");
                            questionnaire_post_action.execute();

                            Toast toast = Toast.makeText(QuestionnaireSelfActivity.this, getResources().getString(R.string.successful), Toast.LENGTH_SHORT);
                            TextView toast_view = (TextView) toast.getView().findViewById(android.R.id.message);
                            toast_view.setTextColor(Color.GREEN);
                            toast.show();

                            setResult(RESULT_OK, new Intent().putExtra("EMPTY_VALUE", "EMPTY"));
                            QuestionnaireSelfActivity.this.finish();
                        }
                    }
                }
            });
        }
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
