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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.korpustokenandroidappnongit.apijsontranslator.Get_teammates_req_post;
import com.example.korpustokenandroidappnongit.apijsontranslator.Questionnaire_team_req_post;
import com.example.korpustokenandroidappnongit.request_methods.Get_teammates_method;
import com.example.korpustokenandroidappnongit.request_methods.Questionnaire_team_method;
import com.example.korpustokenandroidappnongit.scripts.UsefulScripts;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class QuestionnaireTeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_team);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.questionnaire_team);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

        final Map<TextView, RadioGroup> questions_container = new HashMap<TextView, RadioGroup>();
        Questionnaire_team_method team_get_action = new Questionnaire_team_method(QuestionnaireTeamActivity.this, questions_container,"GET");
        team_get_action.execute();

        Button confirm_button = (Button) findViewById(R.id.questionnaire_confirm_button);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UsefulScripts.isOnline(QuestionnaireTeamActivity.this)) {
                    if (UsefulScripts.EmptyFieldValidation(questions_container.values())) {
                        Map<String, Integer> answers = new HashMap<String, Integer>();
                        for (TextView question : questions_container.keySet()) {
                            answers.put(question.getText().toString(), questions_container.get(question).getCheckedRadioButtonId());
                        }
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(QuestionnaireTeamActivity.this);
                        Questionnaire_team_req_post request = new Questionnaire_team_req_post(sharedPreferences.getString("TOKEN", "EMPTY"), answers);
                        String json = new Gson().toJson(request);
                        Questionnaire_team_method questionnaire_team_method = new Questionnaire_team_method(json, "POST");
                        questionnaire_team_method.execute();
                        Log.d("TEST_QTA", answers.toString());

                        Toast toast = Toast.makeText(QuestionnaireTeamActivity.this, getResources().getString(R.string.successful), Toast.LENGTH_SHORT);
                        TextView toast_view = (TextView) toast.getView().findViewById(android.R.id.message);
                        toast_view.setTextColor(Color.GREEN);
                        toast.show();

                        setResult(RESULT_OK, new Intent().putExtra("EMPTY_VALUE", "EMPTY"));
                        QuestionnaireTeamActivity.this.finish();
                    }
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