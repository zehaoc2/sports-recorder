package com.cs498MD.SportsRecorder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Stack;

public class InputActivity extends Activity implements View.OnClickListener {

    // Match Utils
    private String matchId;
    private Period period;
    private final String MATCH = "match";
    private TextView lastAction;
    private TextView matchName;
    private TextView myKid;

    // Teams
    private int kidOne;
    private int kidTwo;
    private int kidThree;
    private int kidMiss;

    // Period
    private int currPeriod;

    private TextView myScoreView;
    private TextView opponentScoreView;

    // Active match info
    private Match match;
    private int kidScore;
    private int othersScore;
    private int oppScore;
    private int prevKidScore;
    private int prevOthersScore;
    private int prevOppScore;

    private Stack<Action> history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_page);

        initViews();
        initMatchInfo();
    }

    /* ================================================================================================================================================= */
    /* ================================================================ Initialization ================================================================= */
    /* ================================================================================================================================================= */

    private void initViews() {
        // Game Util Views
        lastAction = findViewById(R.id.last_action);
        findViewById(R.id.undo).setOnClickListener(this);
        findViewById(R.id.end_game).setOnClickListener(this);
        findViewById(R.id.view_game_stats).setOnClickListener(this);

        matchName = findViewById(R.id.match_name);
        myScoreView = findViewById(R.id.my_score);
        opponentScoreView = findViewById(R.id.opp_score);
        myKid = findViewById(R.id.my_kid);

        // Others Views
        findViewById(R.id.other_free_throw).setOnClickListener(this);
        findViewById(R.id.other_two_ptr).setOnClickListener(this);
        findViewById(R.id.other_three_ptr).setOnClickListener(this);
        findViewById(R.id.other_miss).setOnClickListener(this);

        // Kid Views
        findViewById(R.id.myKid_free_throw).setOnClickListener(this);
        findViewById(R.id.myKid_two_ptr).setOnClickListener(this);
        findViewById(R.id.myKid_three_ptr).setOnClickListener(this);
        findViewById(R.id.myKid_miss).setOnClickListener(this);

        // Opponent Views
        findViewById(R.id.opp_free_throw).setOnClickListener(this);
        findViewById(R.id.opp_two_ptr).setOnClickListener(this);
        findViewById(R.id.opp_three_ptr).setOnClickListener(this);
        findViewById(R.id.opp_miss).setOnClickListener(this);

        // Periods
        findViewById(R.id.period_one).setOnClickListener(this);
        findViewById(R.id.period_two).setOnClickListener(this);
        findViewById(R.id.period_three).setOnClickListener(this);
        findViewById(R.id.period_four).setOnClickListener(this);
        findViewById(R.id.period_fourPlus).setOnClickListener(this);
    }

    private void initMatchInfo() {
        matchId = getIntent().getStringExtra("matchId");
        match = new Match(Integer.parseInt(matchId));
        match.setName(getIntent().getStringExtra("matchName"));
        matchName.setText(getIntent().getStringExtra("matchName"));


        initPeriodInfo(0);

        /* Assume we only enter inputActivity by clicking add button in mainActivity */

//        SharedPreferences sharedPreferences = getSharedPreferences(MATCH, MODE_PRIVATE);
//        Gson gson = new Gson();
//
//        String matchJson = sharedPreferences.getString(matchId, "");
//
//        if (matchJson == null || matchJson.equals("")) {
//            match = new Match(Integer.parseInt(matchId));
//        } else {
//            match = gson.fromJson(matchJson, Match.class);
//        }
    }

    private void initPeriodInfo(int idx) {
        if (idx > 0) {
            Team kid = period.getKid();
            kid.setMiss(kidMiss);
            kid.setScore(kidScore - prevKidScore);
            kid.setOnePoint(kidOne);
            kid.setTwoPoint(kidTwo);
            kid.setThreePoint(kidThree);

            period.getOpponent().setScore(oppScore - prevOppScore);
            period.getOthers().setScore(othersScore - prevOthersScore);
        }

        // SET BUTTON COLOR
        switch (idx) {
            case 0:
                currPeriod = R.id.period_one;
                break;
            case 1:
                currPeriod = R.id.period_two;
                break;
            case 2:
                currPeriod = R.id.period_three;
                break;
            case 3:
                currPeriod = R.id.period_four;
                break;
            case 4:
                currPeriod = R.id.period_fourPlus;
                break;
        }

        findViewById(currPeriod).setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.selected_period)));
        // END OF SET BUTTON COLOR

        period = match.getPeriods()[idx];
        period.getKid().setName("My Team (" + getIntent().getStringExtra("kidName") + ")");
        myKid.setText(getIntent().getStringExtra("kidName"));

        prevOppScore = oppScore; prevKidScore = kidScore; prevOthersScore = othersScore;
        kidOne = 0; kidTwo = 0; kidThree = 0; kidMiss = 0;

        history = period.getHistory();
        if (history.isEmpty()) {
            lastAction.setText("");
        } else {
            lastAction.setText(history.peek().getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.view_game_stats) {
            Intent intent = new Intent(this, GameStats.class);
            intent.putExtra("matchId", matchId);
            startActivity(intent);
        } else if (v.getId() == R.id.undo && history.size() >= 1) {
            undoLastAction();
        } else if (v.getId() == R.id.end_game) {
            showAlertDialog(v);
        } else if (v.getId() == R.id.period_one) {
            findViewById(currPeriod).setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.periods)));
            initPeriodInfo(0);
        } else if (v.getId() == R.id.period_two) {
            findViewById(currPeriod).setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.periods)));
            initPeriodInfo(1);
        } else if (v.getId() == R.id.period_three) {
            findViewById(currPeriod).setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.periods)));
            initPeriodInfo(2);
        } else if (v.getId() == R.id.period_four) {
            findViewById(currPeriod).setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.periods)));
            initPeriodInfo(3);
        } else if (v.getId() == R.id.period_fourPlus) {
            findViewById(currPeriod).setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.periods)));
            initPeriodInfo(4);
        } else if (v.getId() == R.id.period_one) {

        } else if (v.getId() == R.id.period_two) {

        } else if (v.getId() == R.id.period_three) {

        } else if (v.getId() == R.id.period_four) {

        } else if (v.getId() == R.id.period_fourPlus) {

        }


        else {
            switch (v.getId()) {
                case R.id.opp_free_throw:
                    oppScore += 1;
                    setLastAction(new Action(period.getOpponent().getName(), Type.Score, 1));
                    break;
                case R.id.opp_two_ptr:
                    oppScore += 2;
                    setLastAction(new Action(period.getOpponent().getName(), Type.Score, 2));
                    break;
                case R.id.opp_three_ptr:
                    oppScore += 3;
                    setLastAction(new Action(period.getOpponent().getName(), Type.Score, 3));
                    break;
                case R.id.opp_miss:
                    setLastAction(new Action(period.getOpponent().getName(), Type.Attempt, 1));
                    break;
                case R.id.other_free_throw:
                    othersScore += 1;
                    setLastAction(new Action(period.getOthers().getName(), Type.Score, 1));
                    break;
                case R.id.other_two_ptr:
                    othersScore += 2;
                    setLastAction(new Action(period.getOthers().getName(), Type.Score, 2));
                    break;
                case R.id.other_three_ptr:
                    othersScore += 3;
                    setLastAction(new Action(period.getOthers().getName(), Type.Score, 3));
                    break;
                case R.id.other_miss:
                    setLastAction(new Action(period.getOthers().getName(), Type.Attempt, 1));
                    break;
                case R.id.myKid_free_throw:
                    kidOne += 1;
                    kidScore += 1;
                    setLastAction(new Action(period.getKid().getName(), Type.Score, 1));
                    break;
                case R.id.myKid_two_ptr:
                    kidTwo += 2;
                    kidScore += 2;
                    setLastAction(new Action(period.getKid().getName(), Type.Score, 2));
                    break;
                case R.id.myKid_three_ptr:
                    kidThree += 3;
                    kidScore += 3;
                    setLastAction(new Action(period.getKid().getName(), Type.Score, 3));
                    break;
                case R.id.myKid_miss:
                    kidMiss += 1;
                    setLastAction(new Action(period.getKid().getName(), Type.Attempt, 1));
                    break;
            }

            myScoreView.setText(String.valueOf(kidScore + othersScore));
            opponentScoreView.setText(String.valueOf(oppScore));
        }
    }

    /* ================================================================================================================================================= */
    /* ==================================================================== Actions ==================================================================== */
    /* ================================================================================================================================================= */
    private void undoLastAction() {
        Action action = history.pop();
        if (action.getType() == Type.Score) {
            if (action.getTeamName().equals(period.getOthers().getName())) {
                othersScore -= action.getPoint();
                myScoreView.setText(String.valueOf(othersScore + kidScore));
            } else if (action.getTeamName().equals(period.getOpponent().getName())) {
                oppScore -= action.getPoint();
                opponentScoreView.setText(String.valueOf(oppScore));
            } else {
                kidScore -= action.getPoint();
                myScoreView.setText(String.valueOf(othersScore + kidScore));

                switch (action.getPoint()) {
                    case 1:
                        kidOne--;
                    case 2:
                        kidTwo--;
                    case 3:
                        kidThree--;
                }
            }
        } else if (action.getType() == Type.Attempt && action.getTeamName().equals(period.getKid().getName())) {
            kidMiss--;
        }
        lastAction.setText(history.isEmpty() ? "" : history.peek().getMessage());
    }

    private void setLastAction(Action action) {
        history.push(action);
        lastAction.setText(action.getMessage());
    }

    /* ================================================================================================================================================= */
    /* ================================================================== END MATCH ==================================================================== */
    /* ================================================================================================================================================= */

    private void IsFinish(String alertmessage) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        InputActivity.super.onBackPressed();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(alertmessage)
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    public void showAlertDialog (final View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Are you sure you want to end this match?");
        alert.setTitle("Sports Recorder");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                match.setDone(true);
                Intent intent = new Intent(InputActivity.this, GameStats.class);
                intent.putExtra("matchId", matchId);
                startActivity(intent);
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.create().show();
    }

    /* ================================================================================================================================================= */
    /* ================================================================== SAVE MATCH =================================================================== */
    /* ================================================================================================================================================= */


    public void saveMatchInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(MATCH, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(matchId, new Gson().toJson(match, Match.class));

        editor.commit();
//        editor.apply();
    }

    /* ================================================================================================================================================= */
    /* ============================================================== ANDROID LIFE CYCLE =============================================================== */
    /* ================================================================================================================================================= */

    @Override
    protected void onPause() {
        super.onPause();
        saveMatchInfo();
    }

    @Override
    public void onBackPressed() {
        IsFinish("Are you sure you want to end this match?");
    }
}
