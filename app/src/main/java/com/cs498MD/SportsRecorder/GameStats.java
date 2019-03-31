package com.cs498MD.SportsRecorder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Stack;

public class GameStats extends AppCompatActivity implements View.OnClickListener {

    private static final Integer SCORE_TABLE = 0;
    private static final Integer TEAM_TABLE = 1;
    private static final Integer PLAYER_TABLE = 2;

    private static TextView scores;
    private String matchJson;
    private Match match;
    private MyTeam myTeam;
    private OpponentTeam oppTeam;

    private TableLayout scoreTable;
    private TableLayout teamTable;
    private TableLayout playerTable;

    private static String[] SCORE_HEADER = {"Team", "Total", "QTR 1", "QTR 2", "QTR 3", "QTR 4", "QTR 4+"};
    private static String[] TEAM_HEADER = {"Total", "1 PT", "2 PT", "3 PT", "1 PT Miss", "2 PT Miss", "3 PT Miss", "Foul"};
    private static String[] PLAYER_HEADER = {"Player", "Total", "1 PT", "2 PT", "3 PT", "1 PT Miss", "2 PT Miss", "3 PT Miss", "Foul"};

    private void formatHeaderText(TextView tv, String text) {
        tv.setText(text);
        tv.setPadding(32, 0, 32, 0);
        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        tv.setTextSize(20);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setBackgroundResource(R.drawable.cell_shape);
    }

    private void formatTableText(TextView tv) {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_stats);

        findViewById(R.id.send).setOnClickListener(this);

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
        myTeam = gson.fromJson(matchJson, MyTeam.class);
        oppTeam = gson.fromJson(matchJson, OpponentTeam.class);

        Log.d("TEAM DEBUG", myTeam.getName());

        populateScoreTable();
        populateTeamTable();
        populatePlayerTable();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, matchJson);
            sendIntent.setType("text/plain");

            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(sendIntent);
            }
        }
    }

    private void populateScoreTable() {
        // Create Header Row
        createHeaderRow(SCORE_TABLE);

        // TODO: Extract Team names from Shared Preferences
        String[] teams = {"My Team", "Opponent"};

        Stack<Period> periods = match.getPeriods();
        int myScore = 0, opponentScore = 0;
        for (int i = 0; i < periods.size(); i++) {
            myScore += periods.get(i).getMyTeam().getScore();
            opponentScore += periods.get(i).getOpponentTeam().getScore();
        }

        // Set table info for score breakdown table
        for (int i=0; i < teams.length; i++) {
            TableRow row = new TableRow(GameStats.this);
            for (int j = 0; j < SCORE_HEADER.length; j++) {
                TextView tv = new TextView(GameStats.this);
                formatTableText(tv);

                if (i < SCORE_HEADER.length) { tv.setBackgroundResource(R.drawable.cell_shape); }

                if (j == 0) {
                    tv.setText(teams[i]);
                    tv.setTextColor(i==0 ? 0xFFC0392B:0xFF2980B9);
                } else if (j == 1) {
                    tv.setText("" + (i == 0 ? myScore : opponentScore));
                } else {
                    if ((j - 2) < periods.size()) {
                        tv.setText("" + (i == 0 ? periods.get(j - 2).getMyTeam().getScore() : periods.get(j - 2).getOpponentTeam().getScore()));
                    } else {
                        tv.setText("N/A");
                    }
                }
                row.addView(tv);
            }
            scoreTable.addView(row);
        }
    }

    private void populateTeamTable() {
        createHeaderRow(TEAM_TABLE);
        Integer onePoint = myTeam.getOnePoint();
        Integer twoPoint = myTeam.getTwoPoint();
        Integer threePoint = myTeam.getThreePoint();

        Integer total = onePoint + (twoPoint*2) + (threePoint*3);

        Integer onePA = myTeam.getOnePointAttempt();
        Integer twoPA = myTeam.getTwoPointAttempt();
        Integer threePA = myTeam.getThreePointAttempt();

        Integer numFouls = myTeam.getFoulCount();

        Integer[] values = {total, onePoint, twoPoint, threePoint, onePA, twoPA, threePA, numFouls};

        TableRow row = new TableRow(GameStats.this);
        for (int i = 0; i < TEAM_HEADER.length; i++) {
            TextView tv = new TextView(GameStats.this);
            formatTableText(tv);
            tv.setText(values[i].toString());
            if (i < TEAM_HEADER.length) { tv.setBackgroundResource(R.drawable.cell_shape); }

            row.addView(tv);
        }

        teamTable.addView(row);
    }

    private void populatePlayerTable() {
        ArrayList<Player> players = myTeam.getPlayers();

        // Only create Players Table if there are players to keep track of
        if (players.isEmpty()) {
            TextView playerStatsTitle = findViewById(R.id.playerStatsTitle);
            playerStatsTitle.setText("");
            return;
        }
        createHeaderRow(PLAYER_TABLE);

        for (int i=0; i < players.size(); i++) {
            TableRow row = new TableRow(GameStats.this);
            Player player = players.get(i);

            for (int j = 0; j < PLAYER_HEADER.length; j++) {
                TextView tv = new TextView(GameStats.this);
                formatTableText(tv);
                tv.setBackgroundResource(R.drawable.cell_shape);

//                if (i < PLAYER_HEADER.length) { tv.setBackgroundResource(R.drawable.cell_shape); }

                if (j == 0) {
                    tv.setText(player.getName());
                } else {
                    // TODO: Extract player info from shared preferences
                    tv.setText("0");
                }
                row.addView(tv);
            }
            playerTable.addView(row);
        }
    }
}
