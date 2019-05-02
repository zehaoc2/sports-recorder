package com.cs498MD.SportsRecorder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class GameStats extends AppCompatActivity implements View.OnClickListener {

    private static final Integer SCORE_TABLE = 0;
    private static final Integer TEAM_TABLE = 1;
    private static final Integer PLAYER_TABLE = 2;

    private Integer[] teamPeriodScores = {0, 0, 0, 0, 0, 0}; // 0th Period is the Total Game Score
    private Integer[] oppPeriodScores = {0, 0, 0, 0, 0, 0};
    private Integer[] gameBreakDown = {0, 0, 0, 0, 0};
    private Integer[] myKidBreakDown = {0, 0, 0, 0, 0};
    private Integer[] othersBreakDown = {0, 0, 0, 0, 0};

    private String myKidName;

    private static TextView scores;
    private String matchJson;
    private Match match;
    private String matchName;

    private TableLayout scoreTable;
    private TableLayout teamTable;
    private TableLayout playerTable;

    private static String[] SCORE_HEADER = {"Team", "Total", "QTR 1", "QTR 2", "QTR 3", "QTR 4", "QTR 4+"};
    private static String[] TEAM_HEADER = {"Total", "1 PT", "2 PT", "3 PT", "Foul"};
    private static String[] PLAYER_HEADER = {"Player", "Total", "1 PT", "2 PT", "3 PT", "Foul"};

    private void formatHeaderText(TextView tv, String text) {
        tv.setText(text);
        tv.setPadding(32, 0, 32, 0);
        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        tv.setTextSize(20);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setBackgroundResource(R.drawable.cell_shape);
    }

    private void formatTableText(TextView tv) {
        tv.setBackgroundResource(R.drawable.cell_shape);
        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        tv.setPadding(32, 0, 32, 0);
        tv.setTextSize(20);
    }

    private void createHeaderRow(Integer table) {
        switch (table) {
            case 0:
                TableRow scoreHeader = new TableRow(GameStats.this);

                // Set header for score breakdown table
                for (int i = 0; i < SCORE_HEADER.length; i++) {
                    TextView tv = new TextView(GameStats.this);
                    formatHeaderText(tv, SCORE_HEADER[i]);

                    if (i < SCORE_HEADER.length) { tv.setBackgroundResource(R.drawable.cell_shape); }
                    scoreHeader.addView(tv);
                }
                scoreTable.addView(scoreHeader);
                break;
            case 1:
                TableRow teamHeader = new TableRow(GameStats.this);

                for (int i = 0; i < TEAM_HEADER.length; i++) {
                    TextView tv = new TextView(GameStats.this);
                    formatHeaderText(tv, TEAM_HEADER[i]);

                    if (i < TEAM_HEADER.length) { tv.setBackgroundResource(R.drawable.cell_shape); }
                    teamHeader.addView(tv);
                }

                teamTable.addView(teamHeader);
                break;
            case 2:
                TableRow playerHeader = new TableRow(GameStats.this);

                for (int i = 0; i < PLAYER_HEADER.length; i++) {
                    TextView tv = new TextView(GameStats.this);
                    formatHeaderText(tv, PLAYER_HEADER[i]);

                    if (i < PLAYER_HEADER.length) { tv.setBackgroundResource(R.drawable.cell_shape); }
                    playerHeader.addView(tv);
                }
                playerTable.addView(playerHeader);
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.stats_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_stats);

//        findViewById(R.id.send).setOnClickListener(this);

        scores = findViewById(R.id.score);
        scores.setBackgroundResource(R.drawable.border_bottom);

        scoreTable = findViewById(R.id.overallStats);
        teamTable = findViewById(R.id.teamStats);
        playerTable = findViewById(R.id.playerStats);

        String matchId = getIntent().getStringExtra("matchId");
        SharedPreferences sharedPreferences = getSharedPreferences("match", MODE_PRIVATE);

        matchJson = sharedPreferences.getString(matchId, "");

        Gson gson = new Gson();
        match = gson.fromJson(matchJson, Match.class);

        matchName = match.getName();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);

        actionBar.setTitle(matchName);

        int periodCount = 1;

        Period[] periods = match.getPeriods();
        for (int i = 0; i < periods.length; i++){
            Period period = periods[i];

            Team oppTeam = period.getOpponent();
            oppPeriodScores[0] += oppTeam.getScore();
            oppPeriodScores[periodCount] += (oppTeam.getScore());

            Team myKid = period.getKid();
            Team others = period.getOthers();
            teamPeriodScores[0] += myKid.getScore() + others.getScore();
            teamPeriodScores[periodCount++] += myKid.getScore() + others.getScore();

            // PLAYER STUFF!!
            myKidBreakDown[0] += myKid.getScore();
            myKidBreakDown[1] += myKid.getOnePoint();
            myKidBreakDown[2] += myKid.getTwoPoint();
            myKidBreakDown[3] += myKid.getThreePoint();
            myKidBreakDown[4] += myKid.getMiss();

            othersBreakDown[0] += others.getScore();
            othersBreakDown[1] += others.getOnePoint();
            othersBreakDown[2] += others.getTwoPoint();
            othersBreakDown[3] += others.getThreePoint();
            othersBreakDown[4] += others.getMiss();

            myKidName = myKid.getName();

            if (periodCount > 5) {
                periodCount = 5;
            }

            gameBreakDown[0] += myKid.getScore() + others.getScore();
            gameBreakDown[1] += myKid.getOnePoint() + others.getOnePoint();
            gameBreakDown[2] += myKid.getTwoPoint() + others.getTwoPoint();
            gameBreakDown[3] += myKid.getThreePoint() + others.getThreePoint();
            gameBreakDown[4] += myKid.getMiss() + others.getMiss();
        }

        Log.d("TEAM DEBUG", match.getName());

        populateScoreTable();
//        populateTeamTable();
        populatePlayerTable();

        // Hiding Text for Team Table
//        ((TextView) findViewById(R.id.teamStatsTitle)).setText("");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.stats_menu_id) {
            Log.e("TEST", "clicked");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.send) {
//            Intent sendIntent = new Intent();
//            sendIntent.setAction(Intent.ACTION_SEND);
//            sendIntent.putExtra(Intent.EXTRA_TEXT, matchJson);
//            sendIntent.setType("text/plain");
//
//            if (sendIntent.resolveActivity(getPackageManager()) != null) {
//                startActivity(sendIntent);
//            }
//        }
    }

    private void populateScoreTable() {
        // Create Header Row
        createHeaderRow(SCORE_TABLE);

        // TODO: Extract Team names from Shared Preferences
        String[] teams = {"My Team", "Opponent"};

        for (int i = 0; i < teams.length; i++) {
            TableRow row = new TableRow(GameStats.this);

            TextView tv = new TextView(GameStats.this);
            formatTableText(tv);
            tv.setTextColor(i==0 ? 0xFFC0392B:0xFF2980B9);
            tv.setText(teams[i]);
            row.addView(tv);

            for (int j = 0; j < teamPeriodScores.length; j++) {
                tv = new TextView(GameStats.this);
                formatTableText(tv);
                tv.setText(i == 0 ? teamPeriodScores[j].toString() : oppPeriodScores[j].toString());
                row.addView(tv);
            }

            scoreTable.addView(row);
        }
    }

    private void populateTeamTable() {
        createHeaderRow(TEAM_TABLE);

        TableRow row = new TableRow(GameStats.this);
        for (int i = 0; i < TEAM_HEADER.length; i++) {
            TextView tv = new TextView(GameStats.this);
            formatTableText(tv);
            tv.setText(gameBreakDown[i].toString());
            row.addView(tv);
        }

        teamTable.addView(row);
    }

    private void populatePlayerTable() {
        // Only create Players Table if there are players to keep track of
        String[] players = {myKidName, "Others", "Overall"};

        createHeaderRow(PLAYER_TABLE);

        TableRow kidRow = new TableRow(GameStats.this);
        TableRow otherRow = new TableRow(GameStats.this);
        TableRow overallRow = new TableRow(GameStats.this);

        for (int i = 0; i < players.length; i++) {
            TextView tv = new TextView(GameStats.this);
            formatTableText(tv);
            switch (i) {
                case 0:
                    tv.setText(players[i]);
                    kidRow.addView(tv);
                    break;
                case 1:
                    tv.setText(players[i]);
                    otherRow.addView(tv);
                    break;
                case 2:
                    tv.setText(players[i]);
                    overallRow.addView(tv);
                    break;
            }
        }

        for (int i = 0; i < PLAYER_HEADER.length - 1; i++) {
            TextView kid = new TextView(GameStats.this);
            TextView other = new TextView(GameStats.this);
            TextView overall = new TextView(GameStats.this);

            formatTableText(kid);
            formatTableText(other);
            formatTableText(overall);

            int kidScore = myKidBreakDown[i];
            int otherScore = othersBreakDown[i];

            kid.setText(Integer.toString(kidScore));
            other.setText(Integer.toString(otherScore));
            overall.setText(Integer.toString(kidScore + otherScore));

            kidRow.addView(kid);
            otherRow.addView(other);
            overallRow.addView(overall);
        }

        playerTable.addView(kidRow);
        playerTable.addView(otherRow);
        playerTable.addView(overallRow);

//        for (String player : players) {
//            TableRow row = new TableRow(GameStats.this);

//            TextView tv = new TextView(GameStats.this);
//            formatTableText(tv);
//            tv.setText(player);
//            row.addView(tv);
//
//            for (int i = 0; i < PLAYER_HEADER.length - 1; i++) {
//                tv = new TextView(GameStats.this);
//                formatTableText(tv);
//                tv.setText(player.equals("Others") ? othersBreakDown[i].toString() : myKidBreakDown[i].toString());
//                row.addView(tv);
//            }
//
//            playerTable.addView(row);
//        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (match.isDone()) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}