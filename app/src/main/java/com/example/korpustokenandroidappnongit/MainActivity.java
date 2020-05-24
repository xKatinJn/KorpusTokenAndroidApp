package com.example.korpustokenandroidappnongit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.korpustokenandroidappnongit.apijsontranslator.Get_user_req_post;
import com.example.korpustokenandroidappnongit.request_methods.Get_user_method;
import com.example.korpustokenandroidappnongit.scripts.MainActivityExpandableListAdapter;
import com.example.korpustokenandroidappnongit.scripts.UsefulScripts;
import com.google.gson.Gson;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FlowingDrawer mDrawer;
    public static int questionnaire_self_request = 0;
    public static int questionnaire_team_request = 1;
    private MainActivityExpandableListAdapter adapter;
    private ExpandableListView menu;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String token = this.sharedPreferences.getString("TOKEN", "EMPTY");

        if (token.equals("EMPTY")) {
            finishAffinity();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }else{
            if (!UsefulScripts.isOnline(MainActivity.this))
                UsefulScripts.MakeToastInternetError(MainActivity.this);
            Get_user_req_post user_req_get = new Get_user_req_post(Arrays.asList("ALL"), token, null);
            String json = new Gson().toJson(user_req_get);
            Get_user_method user_get_action = new Get_user_method(json, Arrays.asList("ALL"), MainActivity.this);
            user_get_action.execute();

            ImageView logout_imageview = (ImageView) findViewById(R.id.logout_imageview);
            this.menu = findViewById(R.id.menu_expandable);
            mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
            mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);

            final HashMap<String, List<String>> menu_item = new HashMap<>();

            final ArrayList<String> questionnaire_groups = new ArrayList<>();
            questionnaire_groups.add("Личная анкета");
            questionnaire_groups.add("Командная анкета");
            menu_item.put("Анкеты", questionnaire_groups);

            this.adapter = new MainActivityExpandableListAdapter(menu_item);
            this.menu.setAdapter(this.adapter);

            menu.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    return false;
                }
            });

            menu.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    switch (groupPosition){
                        case 0:
                            switch (childPosition){
                                case 0:
                                    if (sharedPreferences.getBoolean("QUESTIONNAIRE_SELF", true)) {
                                        if (UsefulScripts.isOnline(MainActivity.this)) {
                                            startActivityForResult(new Intent(MainActivity.this, QuestionnaireSelfActivity.class), questionnaire_self_request);
                                            Log.d("MAIN_ACTIVITY", "QUESTIONNAIRE_SELF");
                                        }else{
                                            UsefulScripts.MakeToastInternetError(MainActivity.this);
                                        }
                                    }else{
                                        UsefulScripts.MakeToastError(MainActivity.this, "Анкета уже заполнена", "#FF0000", false);
                                    }
                                    break;
                                case 1:
                                    if (sharedPreferences.getBoolean("QUESTIONNAIRE_TEAM", true)) {
                                        if (UsefulScripts.isOnline(MainActivity.this)) {
                                            startActivity(new Intent(MainActivity.this, QuestionnaireTeamActivity.class));
                                            Log.d("MAIN_ACTIVITY", "QUESTIONNAIRE_TEAM");
                                        }else{
                                            UsefulScripts.MakeToastInternetError(MainActivity.this);
                                        }
                                    }else{
                                        UsefulScripts.MakeToastError(MainActivity.this, "Анкета уже заполнена", "#FF0000", false);
                                    }
                                    break;
                            }
                            break;
                        case 1:
                            //ASSESSMENT
                            break;
                    }
                    return false;
                }
            });

            mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
                @Override
                public void onDrawerStateChange(int oldState, int newState) {
                    if (newState == ElasticDrawer.STATE_CLOSED) {
                        menu.collapseGroup(0);
                    } else {

                    }
                }

                @Override
                public void onDrawerSlide(float openRatio, int offsetPixels) {

                }
            });

            logout_imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    finishAffinity();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == questionnaire_self_request){
                if (data != null) {
                    if (this.sharedPreferences.getBoolean("QUESTIONNAIRE_SELF", true)) {
                        UsefulScripts.ReloadActivity(MainActivity.this);
                    }
                }
            }else if (requestCode == questionnaire_team_request){
                if (data != null) {
                    if (this.sharedPreferences.getBoolean("QUESTIONNAIRE_TEAM", true)) {
                        UsefulScripts.ReloadActivity(MainActivity.this);
                    }
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (mDrawer.isMenuVisible()) {
            mDrawer.closeMenu();
        } else {
            super.onBackPressed();
        }
    }
}
