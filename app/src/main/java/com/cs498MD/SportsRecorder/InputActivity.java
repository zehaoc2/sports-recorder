package com.cs498MD.SportsRecorder;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.terry.view.swipeanimationbutton.SwipeAnimationButton;
import com.terry.view.swipeanimationbutton.SwipeAnimationListener;

public class InputActivity extends Activity {

    private TextView myScoreView;
    private TextView opponentScoreView;
    private Button opponentAddBtn;
    private int opponentScore;

    public static final String OPPONENT = "opponent";
    public static final String OPPONENT_SCORE = "opponentScore";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_page);

        SwipeAnimationButton swipeAnimationButton = (SwipeAnimationButton) findViewById(R.id.swipe_btn);
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


        SwipeAnimationButton swipeAnimationButton2 = (SwipeAnimationButton) findViewById(R.id.swipe_btn2);
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

        SwipeAnimationButton swipeAnimationButton3 = (SwipeAnimationButton) findViewById(R.id.swipe_btn3);
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
        opponentScoreView = (TextView) findViewById(R.id.opponent_score);
        opponentAddBtn = (Button) findViewById(R.id.opponent_add);



        loadOpponentScore();
        updateViews();

        opponentAddBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                saveData();
            }
        });

    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(OPPONENT, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(OPPONENT_SCORE, opponentScore + 1);
        editor.apply();

        loadOpponentScore();
        updateViews();
    }

    public void loadOpponentScore() {
        SharedPreferences sharedPreferences = getSharedPreferences(OPPONENT, MODE_PRIVATE);
        opponentScore = sharedPreferences.getInt(OPPONENT_SCORE, 0);
    }

    public void updateViews() {
        opponentScoreView.setText(Integer.toString(opponentScore));
    }
}
