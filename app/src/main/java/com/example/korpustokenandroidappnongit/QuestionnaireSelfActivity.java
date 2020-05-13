package com.example.korpustokenandroidappnongit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.korpustokenandroidappnongit.scripts.UsefulScripts;

public class QuestionnaireSelfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_self);

        if (!UsefulScripts.isOnline(QuestionnaireSelfActivity.this)){
            UsefulScripts.MakeToastInternetError(QuestionnaireSelfActivity.this);
            finish();
        }else{
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(R.string.questionnaire_self);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(true);
        }
    }
}
