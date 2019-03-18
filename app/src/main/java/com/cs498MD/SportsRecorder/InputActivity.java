package com.cs498MD.SportsRecorder;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.terry.view.swipeanimationbutton.SwipeAnimationButton;
import com.terry.view.swipeanimationbutton.SwipeAnimationListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import info.hoang8f.widget.FButton;

public class InputActivity extends Activity implements View.OnClickListener{

    // Match Util
    private String matchId;
    private Match match;
    private final String MATCH = "match";
    private TextView lastAction;
    private FButton undo;

    // My Team
    private TextView myNameView;
    private TextView myScoreView;
    private SwipeAnimationButton swipeAnimationButton;
    private SwipeAnimationButton swipeAnimationButton2;
    private SwipeAnimationButton swipeAnimationButton3;
    private Button foulBtn;
    private HashMap<Integer, Integer> totalFailAttempts;

    // Opponent Team
    private TextView opponentNameView;
    private TextView opponentScoreView;
    private Button opponentAddBtn;

    // Active match info
    private int myScore;
    private int opponentScore;
    private Stack<Action> history;


    private ArrayList<Integer> periodBtnIds;

    private String periodUniqueId;
    private int periodNo;

    private String playerUniqueId;
    private int playerNo;

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
        playerNo = 1;
        playerUniqueId = "Clicking Player ";
        totalFailAttempts = new HashMap<>();
        setUpAttpemtsMap();

        setMatchUtils();
        setMyTeam();
        setOpponentTeam();

        initMatchInfo();

        //set dynamic add button for period
        periodAddBtn = findViewById(R.id.period_add);
        periodAddBtn.setOnClickListener(this);

        //set dynamic add button for player
        playerAddBtn = findViewById(R.id.player_add);
        playerAddBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.period_add) {
            //add period event
            if(periodNo >=5){
                Toast.makeText(getApplicationContext(), "can not deal too many btns right now", Toast.LENGTH_LONG).show();
            }

            addPeriodButton();
        } else if (v.getId() == R.id.player_add){
            addPlayerButton();
        } else if (v.getId() == R.id.opponent_add) {
            opponentScoreView.setText(String.valueOf("Score: " + ++opponentScore));
            setLastAction(new Action(match.getOpponentTeam().getName(), Type.Score, 1));
        } else if (v.getId() == R.id.undo) {
            Toast.makeText(this, "clicked", Toast.LENGTH_LONG);
            undoLastAction();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveMatchInfo();
    }

    private void undoLastAction() {
        Action lastAction = history.pop();
        if (lastAction.getType() == Type.Score) {
            if (lastAction.getTeamName().equals(match.getMyTeam().getName())) {
                myScore -= lastAction.getPoint();
            } else {
                opponentScore -= lastAction.getPoint();
            }
            //TODO:set score in textview
        } else if (lastAction.getType() == Type.Attempt) {
            //TODO
        } else {
            //TODO
        }
        setLastAction(history.peek());
    }

    private void setLastAction(Action action) {
        history.push(action);
        lastAction.setText(action.getMessage());
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

        LinearLayout ll = (LinearLayout)findViewById(R.id.button_player_layout);

        ll.addView(myButton, lp);
        ll.removeView(playerAddBtn);
        ll.addView(playerAddBtn, lp);

        playerNo++;
        myButton.setTag(playerUniqueId + playerNo);
        myButton.setText("P" + playerNo);

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
        undo.setOnClickListener(this);
    }

    private void setMyTeam() {
        swipeAnimationButton = (SwipeAnimationButton) findViewById(R.id.swipe_btn);


        swipeAnimationButton.setOnSwipeAnimationListener(new SwipeAnimationListener() {
            @Override
            public void onSwiped(boolean isRight) {
                if (isRight) {
                    myScoreView.setText("Score: " + String.valueOf(++myScore));
                    setLastAction(new Action(match.getMyTeam().getName(), Type.Score, 1));

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            swipeAnimationButton.collapseButton();

                        }
                    }, 400);
                } else {

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            swipeAnimationButton.collapseButton();

                        }
                    }, 400);
                    setLastAction(new Action(match.getMyTeam().getName(), Type.Attempt, 1));
                    totalFailAttempts.put(1, totalFailAttempts.get(1) + 1);

                }
            }
        });

        swipeAnimationButton2 = (SwipeAnimationButton) findViewById(R.id.swipe_btn2);
        swipeAnimationButton2.setOnSwipeAnimationListener(new SwipeAnimationListener() {
            @Override
            public void onSwiped(boolean isRight) {
                if (isRight) {
                    myScore += 2;
                    myScoreView.setText("Score: " + String.valueOf(myScore));
                    setLastAction(new Action(match.getMyTeam().getName(), Type.Score, 2));

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            swipeAnimationButton2.collapseButton();

                        }
                    }, 400);
                } else {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            swipeAnimationButton2.collapseButton();

                        }
                    }, 400);
                    setLastAction(new Action(match.getMyTeam().getName(), Type.Attempt, 2));
                    totalFailAttempts.put(2, totalFailAttempts.get(2) + 1);

                }
            }
        });

        swipeAnimationButton3 = (SwipeAnimationButton) findViewById(R.id.swipe_btn3);
        swipeAnimationButton3.setOnSwipeAnimationListener(new SwipeAnimationListener() {
            @Override
            public void onSwiped(boolean isRight) {
                if (isRight) {
                    myScore += 3;
                    myScoreView.setText("Score: " + String.valueOf(myScore));
                    setLastAction(new Action(match.getMyTeam().getName(), Type.Score, 3));

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            swipeAnimationButton3.collapseButton();

                        }
                    }, 400);
                } else {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeAnimationButton3.collapseButton();
                        }
                    }, 400);
                    setLastAction(new Action(match.getMyTeam().getName(), Type.Attempt, 3));
                    totalFailAttempts.put(3, totalFailAttempts.get(3) + 1);

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
        opponentAddBtn.setOnClickListener(this);
    }

    private void initMatchInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(MATCH, MODE_PRIVATE);
        String matchJson = sharedPreferences.getString(matchId, "");

        Gson gson = new Gson();

        if (matchJson == null || matchJson.equals("")) {
            match = new Match();
        } else {
            match = gson.fromJson(matchJson, Match.class);
        }

        MyTeam myTeam = match.getMyTeam();
        OpponentTeam opponentTeam = match.getOpponentTeam();
        ArrayList<Player> players = match.getPlayers();
        history = match.getHistory();
        myScore = myTeam.getScore();
        opponentScore = opponentTeam.getScore();

        myNameView.setText(myTeam.getName());
        myScoreView.setText("Score: " + String.valueOf(myScore));
        opponentNameView.setText(opponentTeam.getName());
        opponentScoreView.setText("Score: " + String.valueOf(opponentScore));
        //TODO: initialize value to players
        if (history.peek() == null) {
            lastAction.setText("");
        } else {
            lastAction.setText(history.peek().getMessage());
        }

    }

    public void saveMatchInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(MATCH, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        match.setHistory(history);
        MyTeam myTeam = match.getMyTeam();
        OpponentTeam opponentTeam = match.getOpponentTeam();
        myTeam.setScore(myScore);
        opponentTeam.setScore(opponentScore);

        editor.putString(matchId, new Gson().toJson(match));
        editor.apply();
    }
    private void setUpAttpemtsMap(){
        totalFailAttempts.put(1,0);
        totalFailAttempts.put(2,0);
        totalFailAttempts.put(3,0);
    }

}
