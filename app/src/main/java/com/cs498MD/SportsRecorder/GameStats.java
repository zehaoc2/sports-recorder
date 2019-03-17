package com.cs498MD.SportsRecorder;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class GameStats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_stats);

        TableLayout table = findViewById(R.id.overallStats);

        TableRow header = new TableRow(GameStats.this);

        String[] h = {"", "Total", "Period 1", "Period 2", "Period 3", "Period 4", "Period 4+"};
        String[] teams = {"My Team", "Opponent"};

        for (int i = 0; i < 7; i++) {
            TextView tv = new TextView(GameStats.this);
            tv.setText(h[i]);
            tv.setPadding(8, 0, 8, 0);
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

            if (i < 6) {
                tv.setBackgroundResource(R.drawable.cell_shape);
            }

            header.addView(tv);
        }
        table.addView(header);


        for (int i=0; i < 2; i++) {
            TableRow row = new TableRow(GameStats.this);
            for (int j = 0; j < 7; j++) {
                TextView tv = new TextView(GameStats.this);
                tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                tv.setPadding(8, 0, 8, 0);

                if (i < 6) {
                    tv.setBackgroundResource(R.drawable.cell_shape);
                }

                if (j == 0) {
                    tv.setText(teams[i]);
                } else {
                    tv.setText("Something");
                }
                row.addView(tv);
            }

            table.addView(row);
        }
    }
}
