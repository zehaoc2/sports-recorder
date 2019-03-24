package com.cs498MD.SportsRecorder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

public class InputActivity extends Activity implements View.OnClickListener {

    // Match Util
    private String matchId;
    private Period period;
    private final String MATCH = "match";
    private TextView lastAction;

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
    private Match match;
    private int foulCount;
    private int myScore;
    private int opponentScore;
    private Stack<Action> history;
    private ArrayList<Player> players;
    private Player player;



    private ArrayList<Integer> periodBtnIds;

    private String periodUniqueId;
    private int periodNo;

    private String playerUniqueId;
    private int playerNo;

    private FButton periodAddBtn;
    private FButton playerAddBtn;

    private FButton teamBtn;

    private FButton endMatchBtn;
    private int onePoint;
    private int twoPoint;
    private int threePoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_page);

        matchId = getIntent().getStringExtra("matchId");
        periodBtnIds = new ArrayList<>();

//        periodNo = 1;
//        periodUniqueId = "Clicking Period ";
        playerNo = 0;
        playerUniqueId = "Clicking Player ";
        totalFailAttempts = new HashMap<>();

        //set team button (the first player head button's on click listener)
        teamBtn = findViewById(R.id.player_head);
        teamBtn.setId(playerNo);
        teamBtn.setOnClickListener(this);

        //set end match button onclick listener
        endMatchBtn = findViewById(R.id.end_match);
        endMatchBtn.setOnClickListener(this);

        setUpAttemptsMap();

        setMatchUtils();
        setMyTeam();
        setOpponentTeam();

        initMatchInfo();

        //set dynamic add button for period
//        periodAddBtn = findViewById(R.id.period_add);
//        periodAddBtn.setOnClickListener(this);

        //set dynamic add button for player
        playerAddBtn = findViewById(R.id.player_add);
        playerAddBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.player_add){
            addPlayerButton();
        } else if (v.getId() == R.id.opponent_add) {
            opponentScoreView.setText(String.valueOf("Score: " + ++opponentScore));
            setLastAction(new Action(period.getOpponentTeam().getName(), Type.Score, 1));
        } else if (v.getId() == R.id.undo && history.size() >= 1) {
            undoLastAction();
        } else if (v.getId() == R.id.foul_btn) {
            if (player != null)
                player.setFoulCount(player.getFoulCount() + 1);
            foulCount++;
            setLastAction(new Action(period.getMyTeam().getName(), Type.Foul, null));
        } else if (v.getId() == R.id.player_head) {
            player = null;
        } else if (v.getId() == R.id.end_match){
            showAlertDialog(v);
            Log.e("TEST", "Clicked End Mathc");

        } else if (v.getId() == R.id.next_period) {
            match.getPeriods().push(new Period());
            saveMatchInfo();
            initPeriodInfo();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveMatchInfo();
    }

    public void showAlertDialog (final View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Are you sure you want to end this match? ");
        alert.setTitle("Sports Recorder");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Match Ended and Saved!", Toast.LENGTH_LONG);
                Log.e("TEST", "Dialog Yes Clicked");

                Intent intent = new Intent(InputActivity.this, GameStats.class);
                intent.putExtra("matchId", matchId);
                startActivity(intent);
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("TEST", "Dialog No Clicked");

            }
        });

        alert.create().show();
    }

    private void undoLastAction() {
        Action action = history.pop();
        if (action.getType() == Type.Score) {
            if (action.getTeamName().equals(period.getMyTeam().getName())) {
                myScore -= action.getPoint();
                myScoreView.setText("Score: " + String.valueOf(myScore));
            } else {
                opponentScore -= action.getPoint();
                opponentScoreView.setText("Score: " + String.valueOf(opponentScore));
            }


        } else if (action.getType() == Type.Attempt) {
            //TODO: change player count
            int score = action.getPoint();
            totalFailAttempts.put(score, totalFailAttempts.get(score) - 1);
        } else {
            //TODO: change player count
            foulCount--;
        }
        lastAction.setText(history.isEmpty() ? "" : history.peek().getMessage());
    }

    private void setLastAction(Action action) {
        history.push(action);
        lastAction.setText(action.getMessage());
    }

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

        periodNo++;
        myButton.setTag(periodUniqueId + periodNo);
        myButton.setText("PP" + periodNo);


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

//        myButton.setTag(playerUniqueId + playerNo);
        myButton.setId(playerNo);
        players.add(new Player("P" + playerNo));

        myButton.setText("P" + ++playerNo);


        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player = players.get(myButton.getId());
            }
        });

        return myButton;
    }

    public static int convertDipToPixels(float dips, Context context)
    {
        return (int) (dips * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    private void setMatchUtils() {
        lastAction = findViewById(R.id.last_action);
        findViewById(R.id.undo).setOnClickListener(this);
        findViewById(R.id.next_period).setOnClickListener(this);
    }

    private void setMyTeam() {
        swipeAnimationButton = (SwipeAnimationButton) findViewById(R.id.swipe_btn);


        swipeAnimationButton.setOnSwipeAnimationListener(new SwipeAnimationListener() {
            @Override
            public void onSwiped(boolean isRight) {
                if (isRight) {
                    if (player != null) {
                        player.setScore(player.getScore() + 1);
                        player.setOnePoint(player.getOnePoint() + 1);
                    }

                    myScoreView.setText("Score: " + String.valueOf(++myScore));
                    onePoint++;
                    setLastAction(new Action(period.getMyTeam().getName(), Type.Score, 1));

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
                    setLastAction(new Action(period.getMyTeam().getName(), Type.Attempt, 1));
                    totalFailAttempts.put(1, totalFailAttempts.get(1) + 1);
                    if (player != null) {
                        player.setOnePointAttempt(player.getOnePointAttempt() + 1);
                    }

                }
            }
        });

        swipeAnimationButton2 = (SwipeAnimationButton) findViewById(R.id.swipe_btn2);
        swipeAnimationButton2.setOnSwipeAnimationListener(new SwipeAnimationListener() {
            @Override
            public void onSwiped(boolean isRight) {
                if (isRight) {
                    if (player != null) {
                        player.setScore(player.getScore() + 2);
                        player.setTwoPoint(player.getTwoPoint() + 1);
                    }
                    myScore += 2;
                    twoPoint++;
                    myScoreView.setText("Score: " + String.valueOf(myScore));
                    setLastAction(new Action(period.getMyTeam().getName(), Type.Score, 2));

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
                    setLastAction(new Action(period.getMyTeam().getName(), Type.Attempt, 2));
                    totalFailAttempts.put(2, totalFailAttempts.get(2) + 1);
                    if (player != null) {
                        player.setTwoPointAttempt(player.getTwoPointAttempt() + 1);
                    }

                }
            }
        });

        swipeAnimationButton3 = (SwipeAnimationButton) findViewById(R.id.swipe_btn3);
        swipeAnimationButton3.setOnSwipeAnimationListener(new SwipeAnimationListener() {
            @Override
            public void onSwiped(boolean isRight) {
                if (isRight) {
                    if (player != null) {
                        player.setScore(player.getScore() + 3);
                        player.setThreePoint(player.getThreePoint() + 1);
                    }

                    myScore += 3;
                    threePoint++;
                    myScoreView.setText("Score: " + String.valueOf(myScore));
                    setLastAction(new Action(period.getMyTeam().getName(), Type.Score, 3));

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
                    setLastAction(new Action(period.getMyTeam().getName(), Type.Attempt, 3));
                    totalFailAttempts.put(3, totalFailAttempts.get(3) + 1);
                    if (player != null) {
                        player.setThreePointAttempt(player.getThreePointAttempt() + 1);
                    }

                }
            }
        });

        myNameView = (TextView) findViewById(R.id.my_name);
        myScoreView = (TextView) findViewById(R.id.my_score);
        foulBtn = (Button) findViewById(R.id.foul_btn);
        foulBtn.setOnClickListener(this);

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
            match = new Match(Integer.parseInt(matchId));
        } else {
            match = gson.fromJson(matchJson, Match.class);
        }

        initPeriodInfo();
    }

    private void initPeriodInfo() {
        period = match.getPeriods().peek();

        MyTeam myTeam = period.getMyTeam();
        OpponentTeam opponentTeam = period.getOpponentTeam();
        players = myTeam.getPlayers();
        history = period.getHistory();
        myScore = myTeam.getScore();
        opponentScore = opponentTeam.getScore();

        myNameView.setText(myTeam.getName());
        myScoreView.setText("Score: " + String.valueOf(myScore));
        opponentNameView.setText(opponentTeam.getName());
        opponentScoreView.setText("Score: " + String.valueOf(opponentScore));


        if (history.isEmpty()) {
            lastAction.setText("");
        } else {
            lastAction.setText(history.peek().getMessage());
        }
    }

    public void saveMatchInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(MATCH, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        period.setHistory(history);
        MyTeam myTeam = period.getMyTeam();
        OpponentTeam opponentTeam = period.getOpponentTeam();
        myTeam.setScore(myScore);
        opponentTeam.setScore(opponentScore);

        myTeam.setOnePoint(onePoint);
        myTeam.setTwoPoint(twoPoint);
        myTeam.setThreePoint(threePoint);

        myTeam.setOnePointAttempt(totalFailAttempts.get(1));
        myTeam.setTwoPointAttempt(totalFailAttempts.get(2));
        myTeam.setThreePointAttempt(totalFailAttempts.get(3));
        myTeam.setFoulCount(foulCount);

        editor.putString(matchId, new Gson().toJson(match, Match.class));

        editor.apply();
    }

    private void setUpAttemptsMap(){
        totalFailAttempts.put(1,0);
        totalFailAttempts.put(2,0);
        totalFailAttempts.put(3,0);
    }

}
