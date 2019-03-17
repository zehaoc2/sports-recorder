package com.cs498MD.SportsRecorder.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cs498MD.SportsRecorder.R;
import com.cs498MD.SportsRecorder.java.Match;
import com.cs498MD.SportsRecorder.java.MyTeam;
import com.cs498MD.SportsRecorder.java.OpponentTeam;
import com.cs498MD.SportsRecorder.java.Player;
import com.google.gson.Gson;
import com.terry.view.swipeanimationbutton.SwipeAnimationButton;
import com.terry.view.swipeanimationbutton.SwipeAnimationListener;

import java.util.ArrayList;

import info.hoang8f.widget.FButton;

public class InputActivity extends Activity implements View.OnClickListener{

    private String matchId;

    private TextView myNameView;
    private TextView myScoreView;
    private SwipeAnimationButton swipeAnimationButton;
    private SwipeAnimationButton swipeAnimationButton2;
    private SwipeAnimationButton swipeAnimationButton3;
    private Button foulBtn;

    private TextView opponentNameView;
    private TextView opponentScoreView;
    private Button opponentAddBtn;

    private TextView lastAction;
    private ImageButton undo;

    private final String MATCH = "match";

    private ArrayList<Integer> periodBtnIds;

    private String periodUniqueId;
    private int periodNo;

    private FButton periodAddBtn;
    private FButton playerAddBtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_page);

        matchId = getIntent().getStringExtra("matchId");
        periodBtnIds = new ArrayList<>();
        periodNo = 1;
        periodUniqueId = "Clicking Period ";


        setMatchUtils();
        setMyTeam();
        setOpponentTeam();

        loadMatchInfo();


        //set dynamic add button for period
        periodAddBtn = findViewById(R.id.period_add);
        periodAddBtn.setOnClickListener(this);

        //set dynamic add button for player
        playerAddBtn = findViewById(R.id.player_add);
        playerAddBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.period_add){
            //add period event
            if(periodNo >=5){
                Toast.makeText(getApplicationContext(), "can not deal too manny btns right now", Toast.LENGTH_LONG).show();
            }

            addPeriodButton();
        }
        else if(v.getId() == R.id.player_add){
            addPlayerButton();
        }

    }


    /**
     * when user click the fButton, we dynamically add a period
     * we push the new id to an arraylist and keep update the onlick listener
     * @return
     */

    private FButton addPeriodButton(){

        final FButton myButton = new FButton(this );
        myButton.setButtonColor(getResources().getColor(R.color.fbutton_color_wet_asphalt));

        myButton.setMinHeight(R.dimen.button_min_height);
        myButton.setMinWidth(R.dimen.button_min_width);

        myButton.setShadowEnabled(true);
        myButton.setTextColor(getResources().getColor(R.color.classic_white));
        myButton.setShadowHeight(12);
        myButton.setCornerRadius(20);
        myButton.setText("New");

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(convertDipToPixels(48,InputActivity.this), convertDipToPixels(48,InputActivity.this) );
        myButton.setLayoutParams(lp);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) myButton.getLayoutParams();
        params.width = convertDipToPixels(48,InputActivity.this);
        params.height = convertDipToPixels(48,InputActivity.this);
        params.setMarginStart(convertDipToPixels(8,InputActivity.this));
        params.setMarginEnd(convertDipToPixels(8,InputActivity.this));
        params.topMargin = convertDipToPixels(8,InputActivity.this);
        params.bottomMargin = convertDipToPixels(8,InputActivity.this);

        LinearLayout ll = (LinearLayout)findViewById(R.id.button_layout);

        ll.addView(myButton, lp);
        ll.removeView(periodAddBtn);
        ll.addView(periodAddBtn, lp);

        periodNo++;
        myButton.setTag(periodUniqueId + periodNo);
        myButton.setText("P" + periodNo);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), (String)myButton.getTag(), Toast.LENGTH_LONG).show();
            }
        });

        return myButton;

    }

    private FButton addPlayerButton(){

        final FButton myButton = new FButton(this );
        myButton.setButtonColor(getResources().getColor(R.color.fbutton_color_wet_asphalt));

        myButton.setMinHeight(R.dimen.button_min_height);
        myButton.setMinWidth(R.dimen.button_min_width);

        myButton.setShadowEnabled(true);
        myButton.setTextColor(getResources().getColor(R.color.classic_white));
        myButton.setShadowHeight(12);
        myButton.setCornerRadius(20);
        myButton.setText("New");

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(convertDipToPixels(48,InputActivity.this), convertDipToPixels(48,InputActivity.this) );
        myButton.setLayoutParams(lp);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) myButton.getLayoutParams();
        params.width = convertDipToPixels(48,InputActivity.this);
        params.height = convertDipToPixels(48,InputActivity.this);
        params.setMarginStart(convertDipToPixels(8,InputActivity.this));
        params.setMarginEnd(convertDipToPixels(8,InputActivity.this));
        params.topMargin = convertDipToPixels(8,InputActivity.this);
        params.bottomMargin = convertDipToPixels(8,InputActivity.this);

        LinearLayout ll = (LinearLayout)findViewById(R.id.button_layout);

        ll.addView(myButton, lp);
        ll.removeView(playerAddBtn);
        ll.addView(playerAddBtn, lp);

        periodNo++;
        myButton.setTag(periodUniqueId + periodNo);
        myButton.setText("P" + periodNo);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), (String)myButton.getTag(), Toast.LENGTH_LONG).show();
            }
        });

        return myButton;


    }



    /**
     * Convert dp to pxiels, return an int
     * @param dips
     * @param context
     * @return
     */

    public static int convertDipToPixels(float dips, Context context)
    {
        return (int) (dips * context.getResources().getDisplayMetrics().density + 0.5f);
    }




    private void setMatchUtils() {
        lastAction = findViewById(R.id.last_action);
        undo = findViewById(R.id.undo);

        undo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setMyTeam() {
        swipeAnimationButton = (SwipeAnimationButton) findViewById(R.id.swipe_btn);
        swipeAnimationButton.setOnSwipeAnimationListener(new SwipeAnimationListener() {
            @Override
            public void onSwiped(boolean isRight) {
                if (isRight) {
                    Toast.makeText(getApplicationContext(), "right Swipe!!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "left Swipe!!!", Toast.LENGTH_LONG).show();
                }
            }
        });

        swipeAnimationButton2 = (SwipeAnimationButton) findViewById(R.id.swipe_btn2);
        swipeAnimationButton2.setOnSwipeAnimationListener(new SwipeAnimationListener() {
            @Override
            public void onSwiped(boolean isRight) {
                if (isRight) {
                    Toast.makeText(getApplicationContext(), "2right Swipe!!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "2left Swipe!!!", Toast.LENGTH_LONG).show();
                }
            }
        });

        swipeAnimationButton3 = (SwipeAnimationButton) findViewById(R.id.swipe_btn3);
        swipeAnimationButton3.setOnSwipeAnimationListener(new SwipeAnimationListener() {
            @Override
            public void onSwiped(boolean isRight) {
                if (isRight) {
                    Toast.makeText(getApplicationContext(), "3right Swipe!!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "3left Swipe!!!", Toast.LENGTH_LONG).show();
                }
            }
        });

        myNameView = (TextView) findViewById(R.id.my_name);
        myScoreView = (TextView) findViewById(R.id.my_score);
        foulBtn = (Button) findViewById(R.id.foul_btn);

    }

    private void setOpponentTeam() {

        opponentNameView = (TextView) findViewById(R.id.opponent_name);
        opponentScoreView = (TextView) findViewById(R.id.opponent_score);
        opponentAddBtn = (Button) findViewById(R.id.opponent_add);

        opponentAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                saveData();
            }
        });
    }

    public void loadMatchInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(MATCH, MODE_PRIVATE);
        String matchJson = sharedPreferences.getString(matchId, "");

        Match match;
        Gson gson = new Gson();

        if (matchJson == null || matchJson.equals("")) {
            match = new Match();
        } else {
            match = gson.fromJson(matchJson, Match.class);
        }

        MyTeam myTeam = match.getMyTeam();
        OpponentTeam opponentTeam = match.getOpponentTeam();
        ArrayList<Player> players = match.getPlayers();



    }

    public void updateMatchInfo(String match) {
        SharedPreferences sharedPreferences = getSharedPreferences(MATCH, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(matchId, match);
        editor.apply();

        loadMatchInfo();
    }
}
