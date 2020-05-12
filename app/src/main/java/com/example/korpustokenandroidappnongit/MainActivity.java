package com.example.korpustokenandroidappnongit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toolbar;


import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private FlowingDrawer mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String token = myPreferences.getString("TOKEN", "EMPTY");
        TextView textView = (TextView) findViewById(R.id.nickname_textview);
        textView.setText(token);
        mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);

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
