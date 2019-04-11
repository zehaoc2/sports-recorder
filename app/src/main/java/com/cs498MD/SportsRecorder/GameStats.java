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

//    private Map<String, Player> playerMap = new HashMap<>();

    private static TextView scores;
    private String matchJson;
    private Match match;
    private String matchName;

    private TableLayout scoreTable;
    private TableLayout teamTable;
    private TableLayout playerTable;

    private static String[] SCORE_HEADER = {"Team", "Total", "QTR 1", "QTR 2", "QTR 3", "QTR 4", "QTR 4+"};
    private static String[] TEAM_HEADER = {"Total", "1 PT", "2 PT", "3 PT", "Miss"};
    private static String[] PLAYER_HEADER = {"Player", "Total", "1 PT", "2 PT", "3 PT", "Miss"};

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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stats_menu, menu);
        return true;
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

            // PLAYER STUFF!!
//            if (i == (periods.size() - 1)) {
//                ArrayList<Player> players = myTeam.getPlayers();
//                Log.d("ARRAY DEBUG", Integer.toString(players.size()));
//
//                for (int p = 0; p < players.size(); p++) {
//                    Player player = players.get(p);
//                    Log.d("MAP DEBUG", player.getName());
//
//                    playerMap.put(player.getName(), player);
//                }
//            }
        }

        Log.d("TEAM DEBUG", match.getName());

        populateScoreTable();
        populateTeamTable();
        populatePlayerTable();
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
        String[] players = {myKidName, "Others"};

        createHeaderRow(PLAYER_TABLE);

        for (String player : players) {
            TableRow row = new TableRow(GameStats.this);

            TextView tv = new TextView(GameStats.this);
            formatTableText(tv);
            tv.setText(player);
            row.addView(tv);

            for (int i = 0; i < PLAYER_HEADER.length - 1; i++) {
                tv = new TextView(GameStats.this);
                formatTableText(tv);
                tv.setText(player.equals("Others") ? othersBreakDown[i].toString() : myKidBreakDown[i].toString());
                row.addView(tv);
            }

            playerTable.addView(row);
        }
    }

//    private void populatePlayerTable() {
//        // Only create Players Table if there are players to keep track of
//        if (playerMap.isEmpty()) {
//            TextView playerStatsTitle = findViewById(R.id.playerStatsTitle);
//            playerStatsTitle.setText("");
//            return;
//        }
//
//        createHeaderRow(PLAYER_TABLE);
//        for (Map.Entry<String, Player> entry : playerMap.entrySet()) {
//            Log.d("DEBUG PLAYER", "Key = " + entry.getKey() + ", Value = " + entry.getValue());
//
//            String playerName = entry.getKey();
//            Player player = entry.getValue();
//
//            Integer[] values = { player.getScore(),
//                    player.getOnePoint(),
//                    player.getTwoPoint(),
//                    player.getThreePoint(),
//                    player.getOnePointAttempt(),
//                    player.getTwoPointAttempt(),
//                    player.getThreePointAttempt(),
//                    player.getFoulCount()
//            };
//
//            TableRow row = new TableRow(GameStats.this);
//            TextView tv = new TextView(GameStats.this);
//            formatTableText(tv);
//            tv.setText(playerName);
//            row.addView(tv);
//
//            for (int i = 0; i < values.length; i++) {
//                tv = new TextView(GameStats.this);
//                formatTableText(tv);
//                tv.setText(values[i].toString());
//
//                row.addView(tv);
//            }
//
//            playerTable.addView(row);
//        }
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (match.isDone()) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}