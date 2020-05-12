package com.example.korpustokenandroidappnongit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toolbar;


import com.example.korpustokenandroidappnongit.apijsontranslator.User_req_get;
import com.example.korpustokenandroidappnongit.request_methods.User_get_method;
import com.google.gson.Gson;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private FlowingDrawer mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User_req_get user_req_get = new User_req_get(Arrays.asList("ALL"));
        String json = new Gson().toJson(user_req_get);
        User_get_method user_get_action = new User_get_method(json, Arrays.asList("ALL"), MainActivity.this);
        //user_get_action.execute();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String token = sharedPreferences.getString("TOKEN", "EMPTY");

        TextView login = (TextView) findViewById(R.id.nickname_textview);
        TextView name_surname = (TextView) findViewById(R.id.namesurname_textview);
        mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);

        login.setText(sharedPreferences.getString("LOGIN", "USER_LOGIN"));
        name_surname.setText(sharedPreferences.getString("NAME", "USER_NAME")+" "+sharedPreferences.getString("SURNAME", "USER_SURNAME"));

        mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == ElasticDrawer.STATE_CLOSED){

                }else{

                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {

            }
        });
    }

//    private void setupMenu() {
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentContainer fc = (FragmentContainer) findViewById(R.id.fragment_container_view_tag);
//        fm.beginTransaction().add(R.id.fragment_container_view_tag, );
//    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isMenuVisible()) {
            mDrawer.closeMenu();
        } else {
            super.onBackPressed();
        }
    }
}
