package com.cs498MD.SportsRecorder;


import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

//    private static final long RIPPLE_DURATION = 250;
//
//    public static final String TEST = "TEST";
//    private Toolbar toolbar;
//    private FrameLayout home_page_menu;
//
//    private View side_setting;

    private static FloatingActionButton newMatch;
    private ArrayList<String> matchArray = new ArrayList<>();
    private int matchCount = 1;
    private MyCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setTitle("Matches");

        // TODO: Take out later
        matchArray.add("TempMatch");

        newMatch = (FloatingActionButton) findViewById(R.id.newMatch);
        newMatch.setOnClickListener(this);

        adapter = new MyCustomAdapter(matchArray, this);

        ListView listView = (ListView) findViewById(R.id.matchList);
        listView.setEmptyView(findViewById(R.id.noMatches));
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.newMatch) {
            String matchName = "Match" + matchCount;
            matchCount++;
            matchArray.add(matchName);
            adapter.notifyDataSetChanged();
            startActivity(new Intent(this, InputActivity.class));
        }
//        switch (v.getId()) {
//
//            case R.id.profile_text_btn:
//                // click side menu profile text btn;
//                Log.e(TEST, "profile clicked");
//                break;
//
//            case R.id.stats_text_btn:
//                // click side menu stats text btn;
//                Log.e(TEST, "stats clicked");
//                break;
//
//            case R.id.opponents_text_btn:
//                // click side menu opponents text btn;
//                Log.e(TEST, "opponents clicked");
//                break;
//            case R.id.settings_text_btn:
//                //click side menu settings btn
//                Log.e(TEST, "settings clicked");
//                startActivity(new Intent(this, InputActivity.class));
//
//                break;
//            case R.id.newMatchBtn:
//                //create new match
//                break;
//            case R.id.preMatchBtn:
//                //previous match
//                break;
//            case R.id.manageBtn:
//                //manage my team
//                break;
//
//
//            default:
//                break;
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.home_page);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.home_page);
//
//
//        toolbar = findViewById(R.id.toolbar);
//        home_page_menu = findViewById(R.id.root);
//        side_setting = (ImageView) findViewById(R.id.side_image_btn);
//
//
//        initHomeMenuBtns();
//
//
//
//        if (toolbar != null) {
//            setSupportActionBar(toolbar);
//            getSupportActionBar().setTitle(null);
//        }


//        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.side_page, null);
//        home_page_menu.addView(guillotineMenu);
//
//
//        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.side_image_btn_vertical), side_setting)
//                .setGuillotineListener(new GuillotineListener() {
//            @Override
//            public void onGuillotineOpened() {
//                //side menu is opened
//                Log.e(TEST, "side menu opened");
//
//                //find side menus's btns on opened
//                initSideMenuBtns();
//            }
//
//            @Override
//            public void onGuillotineClosed() {
//                //side menu is closed
//            }
//        }).setStartDelay(RIPPLE_DURATION)
//                .setActionBarViewForAnimation(toolbar)
//                .setClosedOnStart(true)
//                .build();

    }



//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()) {
//            case R.id.profile_text_btn:
//                // click side menu profile text btn;
//                Log.e(TEST, "profile clicked");
//                break;
//
//            case R.id.stats_text_btn:
//                // click side menu stats text btn;
//                Log.e(TEST, "stats clicked");
//                break;
//
//            case R.id.opponents_text_btn:
//                // click side menu opponents text btn;
//                Log.e(TEST, "opponents clicked");
//                break;
//            case R.id.settings_text_btn:
//                //click side menu settings btn
//                Log.e(TEST, "settings clicked");
//                startActivity(new Intent(this, SettingActivity.class));
//
//                break;
//            case R.id.newMatchBtn:
//                //create new match
//                break;
//            case R.id.preMatchBtn:
//                //previous match
//                break;
//            case R.id.manageBtn:
//                //manage my team
//                break;
//
//
//            default:
//                break;
//        }
//
//    }
//
//    public void initSideMenuBtns(){
//
//        MainActivity.this.findViewById(R.id.profile_text_btn).setOnClickListener(MainActivity.this);
//        MainActivity.this.findViewById(R.id.stats_text_btn).setOnClickListener(MainActivity.this);
//        MainActivity.this.findViewById(R.id.opponents_text_btn).setOnClickListener(MainActivity.this);
//        MainActivity.this.findViewById(R.id.settings_text_btn).setOnClickListener(MainActivity.this);
//
//    }
//
//    public void initHomeMenuBtns(){
//        MainActivity.this.findViewById(R.id.newMatchBtn).setOnClickListener(MainActivity.this);
//        MainActivity.this.findViewById(R.id.preMatchBtn).setOnClickListener(MainActivity.this);
//        MainActivity.this.findViewById(R.id.manageBtn).setOnClickListener(MainActivity.this);
//    }

//}
