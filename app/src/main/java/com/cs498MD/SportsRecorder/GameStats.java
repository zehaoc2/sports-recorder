package com.cs498MD.SportsRecorder;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

//        Toast.makeText(this, "You got here", Toast.LENGTH_SHORT);
//        TableRow header = new TableRow(GameStats.this);

//        <TextView
//        android:id="@+id/gameText"
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//        android:text="TextView"
//        android:background="@drawable/cell_shape"/>

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(0x000);
        gd.setCornerRadius(5);
        gd.setStroke(2, 0x000);

        for (int i=0; i < 3; i++) {
            TableRow row = new TableRow(GameStats.this);
            row.setBackgroundResource(R.drawable.cell_shape);
            for (int j = 0; j < 7; j++) {
                int value = 0;
                TextView tv = new TextView(GameStats.this);
                tv.setText("Something");
                row.addView(tv);
            }

            table.addView(row);
        }
    }
}
