package com.cs498MD.SportsRecorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameStats extends AppCompatActivity {

    private static TextView scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_stats);

        scores = findViewById(R.id.score);
        scores.setBackgroundResource(R.drawable.border_bottom);

        TableLayout scoreTable = findViewById(R.id.overallStats);
        TableLayout playerTable = findViewById(R.id.playerStats);

        TableRow scoreHeader = new TableRow(GameStats.this);
        String[] h = {"", "Total", "Period 1", "Period 2", "Period 3", "Period 4", "Period 4+"};
        String[] teams = {"My Team", "Opponent"};
        // Set header for score breakdown table
        for (int i = 0; i < 7; i++) {
            TextView tv = new TextView(GameStats.this);
            tv.setText(h[i]);
            tv.setPadding(8, 0, 8, 0);
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

            if (i < 6) { tv.setBackgroundResource(R.drawable.cell_shape); }
            scoreHeader.addView(tv);
        }
        scoreTable.addView(scoreHeader);

        // Set table info for score breakdown table
        for (int i=0; i < 2; i++) {
            TableRow row = new TableRow(GameStats.this);
            for (int j = 0; j < 7; j++) {
                TextView tv = new TextView(GameStats.this);
                tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                tv.setPadding(8, 0, 8, 0);

                if (i < 6) { tv.setBackgroundResource(R.drawable.cell_shape); }

                if (j == 0) {
                    tv.setText(teams[i]);
                } else {
                    tv.setText("Something");
                }
                row.addView(tv);
            }
            scoreTable.addView(row);
        }

        TableRow playerHeader = new TableRow(GameStats.this);
        String[] players = {"Bill", "Susan"};
        String[] points = {"Total", "1 Point", "2 Point", "3 Point"};
        for (int i = 0; i < players.length; i++) {
            TextView tv = new TextView(GameStats.this);
            tv.setText(points[i]);
            tv.setPadding(8, 0, 8, 0);
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

            if (i < 6) { tv.setBackgroundResource(R.drawable.cell_shape); }
            playerHeader.addView(tv);
        }
        playerTable.addView(playerHeader);

        for (int i=0; i < 2; i++) {
            TableRow row = new TableRow(GameStats.this);
            for (int j = 0; j < 7; j++) {
                TextView tv = new TextView(GameStats.this);
                tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                tv.setPadding(8, 0, 8, 0);

                if (i < 6) { tv.setBackgroundResource(R.drawable.cell_shape); }

                if (j == 0) {
                    tv.setText(players[i]);
                } else {
                    tv.setText("0");
                }
                row.addView(tv);
            }
            playerTable.addView(row);
        }


    }
}
