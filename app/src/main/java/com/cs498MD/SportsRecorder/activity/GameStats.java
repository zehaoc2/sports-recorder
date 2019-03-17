package com.cs498MD.SportsRecorder.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cs498MD.SportsRecorder.R;

public class GameStats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_stats);

        TableLayout table = findViewById(R.id.overallStats);

//        Toast.makeText(this, "You got here", Toast.LENGTH_SHORT);

//        <TextView
//        android:id="@+id/gameText"
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//        android:text="TextView"
//        android:background="@drawable/cell_shape"/>

//        GradientDrawable gd = new GradientDrawable();
//        gd.setColor(0x000);
//        gd.setCornerRadius(5);
//        gd.setStroke(2, 0x000);

        TableRow header = new TableRow(GameStats.this);
        header.setBackgroundResource(R.drawable.cell_shape);
        String[] h = {"", "Total Score", "Period 1", "Period 2", "Period 3", "Period 4", "Period 4+"};
        String[] teams = {"My Team", "Opponent"};

        for (int i = 0; i < 7; i++) {
            TextView tv = new TextView(GameStats.this);
            tv.setText(h[i]);
            header.addView(tv);
        }
        table.addView(header);


        for (int i=0; i < 2; i++) {
            TableRow row = new TableRow(GameStats.this);
            row.setBackgroundResource(R.drawable.cell_shape);
            for (int j = 0; j < 7; j++) {
                TextView tv = new TextView(GameStats.this);

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
