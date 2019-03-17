package com.cs498MD.SportsRecorder;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.terry.view.swipeanimationbutton.SwipeAnimationButton;
import com.terry.view.swipeanimationbutton.SwipeAnimationListener;

import info.hoang8f.widget.FButton;

public class InputActivity extends Activity implements View.OnClickListener{

    private String matchId;

    private TextView myScoreView;
    private SwipeAnimationButton swipeAnimationButton;
    private SwipeAnimationButton swipeAnimationButton2;
    private SwipeAnimationButton swipeAnimationButton3;
    private Button foulBtn;

    private TextView opponentScoreView;
    private Button opponentAddBtn;

    private TextView lastAction;
    private ImageButton undo;




    private Button addPeriodBtn;

    public static final String OPPONENT_SCORE = "opponentScore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_page);

        matchId = getIntent().getStringExtra("matchId");

        setMatchUtils();
        setMyTeam();
        setOpponentTeam();

        loadMatchInfo();
        updateMatchInfo();

        addPeriodBtn = findViewById(R.id.period_add);
        addPeriodBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.period_add){
            //add period event
            FButton myButton = new FButton(this );
            myButton.setButtonColor(getResources().getColor(R.color.fbutton_color_wet_asphalt));
            myButton.setWidth(48);
            myButton.setHeight(48);
            myButton.setShadowEnabled(true);
            myButton.setShadowHeight(5);
            myButton.setCornerRadius(5);
            myButton.setText("New");

            LinearLayout ll = (LinearLayout)findViewById(R.id.button_layout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(myButton, lp);

        }

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

        myScoreView = (TextView) findViewById(R.id.my_score);
        foulBtn = (Button) findViewById(R.id.foul_btn);

    }

    private void setOpponentTeam() {

        opponentScoreView = (TextView) findViewById(R.id.opponent_score);
        opponentAddBtn = (Button) findViewById(R.id.opponent_add);

        opponentAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(matchId, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


//        editor.putInt(OPPONENT_SCORE, opponentScore + 1);
        editor.apply();

        loadMatchInfo();
        updateMatchInfo();
    }

    public void loadMatchInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(matchId, MODE_PRIVATE);
//        opponentScore = sharedPreferences.getInt(OPPONENT_SCORE, 0);
    }

    public void updateMatchInfo() {
//        opponentScoreView.setText(Integer.toString(opponentScore));
    }
}
