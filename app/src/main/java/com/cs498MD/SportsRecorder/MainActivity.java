package com.cs498MD.SportsRecorder;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;



import com.yalantis.guillotine.animation.GuillotineAnimation;
import com.yalantis.guillotine.interfaces.GuillotineListener;

import info.hoang8f.widget.FButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final long RIPPLE_DURATION = 250;


    public static final String TEST = "TEST";
    private Toolbar toolbar;
    private FrameLayout home_page_menu;

    private View side_setting;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);


        toolbar = findViewById(R.id.toolbar);
        home_page_menu = findViewById(R.id.root);
        side_setting = (ImageView) findViewById(R.id.side_image_btn);


        initHomeMenuBtns();



        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }


        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.side_page, null);
        home_page_menu.addView(guillotineMenu);


        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.side_image_btn_vertical), side_setting)
                .setGuillotineListener(new GuillotineListener() {
            @Override
            public void onGuillotineOpened() {
                //side menu is opened
                Log.e(TEST, "side menu opened");

                //find side menus's btns on opened
                initSideMenuBtns();

            }

            @Override
            public void onGuillotineClosed() {
                //side menu is closed


            }
        }).setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.profile_text_btn:
                // click side menu profile text btn;
                Log.e(TEST, "profile clicked");
                break;

            case R.id.stats_text_btn:
                // click side menu stats text btn;
                Log.e(TEST, "stats clicked");
                break;

            case R.id.opponents_text_btn:
                // click side menu opponents text btn;
                Log.e(TEST, "opponents clicked");
                break;
            case R.id.settings_text_btn:
                //click side menu settings btn
                Log.e(TEST, "settings clicked");
                startActivity(new Intent(this, SettingActivity.class));

                break;
            case R.id.newMatchBtn:
                //create new match
                break;
            case R.id.preMatchBtn:
                //previous match
                break;
            case R.id.manageBtn:
                //manage my team
                break;


            default:
                break;
        }

    }

    public void initSideMenuBtns(){

        MainActivity.this.findViewById(R.id.profile_text_btn).setOnClickListener(MainActivity.this);
        MainActivity.this.findViewById(R.id.stats_text_btn).setOnClickListener(MainActivity.this);
        MainActivity.this.findViewById(R.id.opponents_text_btn).setOnClickListener(MainActivity.this);
        MainActivity.this.findViewById(R.id.settings_text_btn).setOnClickListener(MainActivity.this);

    }

    public void initHomeMenuBtns(){
        MainActivity.this.findViewById(R.id.newMatchBtn).setOnClickListener(MainActivity.this);
        MainActivity.this.findViewById(R.id.preMatchBtn).setOnClickListener(MainActivity.this);
        MainActivity.this.findViewById(R.id.manageBtn).setOnClickListener(MainActivity.this);
    }


}
