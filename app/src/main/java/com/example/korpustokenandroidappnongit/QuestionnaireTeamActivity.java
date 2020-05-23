package com.example.korpustokenandroidappnongit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.korpustokenandroidappnongit.request_methods.Questionnaire_team_method;

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

        Questionnaire_team_method team_get_action = new Questionnaire_team_method(QuestionnaireTeamActivity.this, "GET");
        team_get_action.execute();
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