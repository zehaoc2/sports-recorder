package com.cs498MD.SportsRecorder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Stack;

public class InputActivity extends Activity implements View.OnClickListener {

    // Match Utils
    private String matchId;
    private Period period;
    private final String MATCH = "match";
    private TextView lastAction;

    // Teams
    private TextView myScoreView;
    private int onePoint;
    private int twoPoint;
    private int threePoint;
    private int onePointAtt;
    private int twoPointAtt;
    private int threePointAtt;
    private TextView opponentScoreView;

    // Active match info
    private Match match;
    private int myScore;
    private int prevMyScore;
    private int opponentScore;
    private int prevOppScore;
    private Stack<Action> history;

//    private TextView myNameView;
//    private TextView opponentNameView;
//    private ArrayList<Player> players;
//    private Player player;

//    private FButton endMatchBtn;



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
        // Set Game Util Views
        lastAction = findViewById(R.id.last_action);
        findViewById(R.id.undo).setOnClickListener(this);

//        endMatchBtn = findViewById(R.id.end_match);
//        endMatchBtn.setOnClickListener(this);

        // Set My Team Views
        Button myOnePointMade = findViewById(R.id.my_made_one_ptr);
        myOnePointMade.setOnClickListener(this);
        Button myTwoPointMade = findViewById(R.id.my_made_two_ptr);
        myTwoPointMade.setOnClickListener(this);
        Button myThreePointMade = findViewById(R.id.my_made_three_ptr);
        myThreePointMade.setOnClickListener(this);
        Button myOnePointMiss = findViewById(R.id.my_miss_one_ptr);
        myOnePointMiss.setOnClickListener(this);
        Button myTwoPointMiss = findViewById(R.id.my_miss_two_ptr);
        myTwoPointMiss.setOnClickListener(this);
        Button myThreePointMiss = findViewById(R.id.my_miss_three_ptr);
        myThreePointMiss.setOnClickListener(this);

//        myNameView = findViewById(R.id.my_name);
        myScoreView = findViewById(R.id.my_team_score);

        // Set Opponent Team Views
        //TODO: add opponent breakdown if time allows
//        opponentNameView = (TextView) findViewById(R.id.opponent_name);
        opponentScoreView = (TextView) findViewById(R.id.opp_score);
    }

    private void initMatchInfo() {
        matchId = getIntent().getStringExtra("matchId");

        SharedPreferences sharedPreferences = getSharedPreferences(MATCH, MODE_PRIVATE);
        Gson gson = new Gson();

        String matchJson = sharedPreferences.getString(matchId, "");

        if (matchJson == null || matchJson.equals("")) {
            match = new Match(Integer.parseInt(matchId));
        } else {
            match = gson.fromJson(matchJson, Match.class);
        }

//        TextView matchName = findViewById(R.id.match_name);
//        matchName.setText(match.getName() + " : Period" + (match.getPeriods().size() + 1));

        initPeriodInfo();
    }

    private void initPeriodInfo() {
        period = match.getPeriods().peek();

//        TextView matchName = findViewById(R.id.match_name);
//        matchName.setText(match.getName() + " : Period " + (match.getPeriods().size()));

        MyTeam myTeam = period.getMyTeam();
        OpponentTeam opponentTeam = period.getOpponentTeam();
//        if (players == null) {
//            players = myTeam.getPlayers();
//        }

        onePoint = 0;
        twoPoint = 0;
        threePoint = 0;
        onePointAtt = 0;
        twoPointAtt = 0;
        threePointAtt = 0;

        history = period.getHistory();

        if (history.isEmpty()) {
            lastAction.setText("");
        } else {
            lastAction.setText(history.peek().getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.view_prev_matches) {
            Intent intent = new Intent(this, GameStats.class);
            intent.putExtra("matchId", matchId);
            startActivity(intent);
        } else if (v.getId() == R.id.view_game_stats) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (v.getId() == R.id.undo && history.size() >= 1) {
            undoLastAction();
        }
//          else if (v.getId() == R.id.end_match){
//            showAlertDialog(v);
//        } else if (v.getId() == R.id.next_period) {
//            match.getPeriods().push(new Period());
//            saveMatchInfo();
//            initPeriodInfo();
//        }
        else {
            switch (v.getId()) {
                case R.id.my_made_one_ptr:
                    myScore += 1;
                    setLastAction(new Action(period.getMyTeam().getName(), Type.Score, 1));
                case R.id.my_made_two_ptr:
                    myScore += 2;
                    setLastAction(new Action(period.getMyTeam().getName(), Type.Score, 2));
                case R.id.my_made_three_ptr:
                    myScore += 3;
                    setLastAction(new Action(period.getMyTeam().getName(), Type.Score, 3));
                case R.id.my_miss_one_ptr:
                    onePointAtt++;
                    setLastAction(new Action(period.getMyTeam().getName(), Type.Attempt, 1));
                case R.id.my_miss_two_ptr:
                    twoPointAtt++;
                    setLastAction(new Action(period.getMyTeam().getName(), Type.Attempt, 2));
                case R.id.my_miss_three_ptr:
                    threePointAtt++;
                    setLastAction(new Action(period.getMyTeam().getName(), Type.Attempt, 3));
                case R.id.opp_made_one_ptr:
                    opponentScore += 1;
                    setLastAction(new Action(period.getOpponentTeam().getName(), Type.Score, 1));
                case R.id.opp_made_two_ptr:
                    opponentScore += 2;
                    setLastAction(new Action(period.getOpponentTeam().getName(), Type.Score, 2));
                case R.id.opp_made_three_ptr:
                    opponentScore += 3;
                    setLastAction(new Action(period.getOpponentTeam().getName(), Type.Score, 3));
                case R.id.opp_miss_one_ptr:
                    setLastAction(new Action(period.getOpponentTeam().getName(), Type.Attempt, 1));
                case R.id.opp_miss_two_ptr:
                    setLastAction(new Action(period.getOpponentTeam().getName(), Type.Attempt, 2));
                case R.id.opp_miss_three_ptr:
                    setLastAction(new Action(period.getOpponentTeam().getName(), Type.Attempt, 3));
            }
            myScoreView.setText(String.valueOf(myScore));
            opponentScoreView.setText(String.valueOf(opponentScore));
        }
    }

    /* ================================================================================================================================================= */
    /* ================================================================== SAVE MATCH =================================================================== */
    /* ================================================================================================================================================= */


    public void saveMatchInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(MATCH, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        period.setHistory(history);
        MyTeam myTeam = period.getMyTeam();

        myTeam.setScore(myScore - prevMyScore);
        myTeam.setOnePoint(onePoint);
        myTeam.setTwoPoint(twoPoint);
        myTeam.setThreePoint(threePoint);

        myTeam.setOnePointAttempt(onePointAtt - prevOppScore);
        myTeam.setTwoPointAttempt(twoPointAtt);
        myTeam.setThreePointAttempt(threePointAtt);

//        myTeam.setPlayers(players);

        OpponentTeam opponentTeam = period.getOpponentTeam();
        opponentTeam.setScore(opponentScore);

        editor.putString(matchId, new Gson().toJson(match, Match.class));

        editor.commit();
    }

    /* ================================================================================================================================================= */
    /* ==================================================================== Actions ==================================================================== */
    /* ================================================================================================================================================= */
    //TODO: Add opponent team if time allows.
    private void undoLastAction() {
        Action action = history.pop();
        if (action.getType() == Type.Score) {
            if (action.getTeamName().equals(period.getMyTeam().getName())) {
                myScore -= action.getPoint();
                myScoreView.setText(myScore);

                switch (action.getPoint()) {
                    case 1:
                        onePoint--;
                    case 2:
                        twoPoint--;
                    case 3:
                        threePoint--;
                }

            } else {
                opponentScore -= action.getPoint();
                opponentScoreView.setText(opponentScore);
            }
        } else if (action.getType() == Type.Attempt && action.getTeamName().equals(period.getMyTeam().getName())) {
            switch (action.getPoint()) {
                case 1:
                    onePointAtt--;
                case 2:
                    twoPointAtt--;
                case 3:
                    threePointAtt--;

            }
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


//    public void initPlayerButtonColor(){
//        player_button_colors = new ArrayList<>();
//        player_button_colors.add(R.color.yellow);
//        player_button_colors.add(R.color.fbutton_color_carrot);
//        player_button_colors.add(R.color.fbutton_color_pumpkin);
//        player_button_colors.add(R.color.fbutton_color_pomegranate);
//    }
    


    public void showAlertDialog (final View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Are you sure you want to end this match?");
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

    /* ================================================================================================================================================= */
    /* ============================================================== ANDROID LIFE CYCLE =============================================================== */
    /* ================================================================================================================================================= */

    @Override
    protected void onStop() {
        super.onStop();
        saveMatchInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveMatchInfo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveMatchInfo();
    }

    @Override
    public void onBackPressed() {
        IsFinish("Are you sure you want to end this match?");
    }

    private void RunAnimation(TextView tv)
    {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.vibrate);
        a.reset();

        tv.clearAnimation();
        tv.startAnimation(a);
    }


}
