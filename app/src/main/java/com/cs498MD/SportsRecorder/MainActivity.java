package com.cs498MD.SportsRecorder;


import android.app.ActionBar;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static FloatingActionButton newMatch;
    private ArrayList<String> matchArray = new ArrayList<>();
    private MyCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setTitle("Matches");

        // TODO: Take out later
        matchArray.add("TempMatch");

        newMatch = (FloatingActionButton) findViewById(R.id.newMatch);
        newMatch.setOnClickListener(this);

        adapter = new MyCustomAdapter(matchArray, this);

        ListView listView = (ListView) findViewById(R.id.matchList);
        listView.setEmptyView(findViewById(R.id.noMatches));
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("match", MODE_PRIVATE);
        int matchCount = sharedPreferences.getInt("matchCount", -1);

        matchCount++;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("matchCount", matchCount);
        editor.putString("matchId", "");
        editor.apply();

        if (v.getId() == R.id.newMatch) {
            String matchName = "Match" + matchCount;
            matchArray.add(matchName);
            adapter.notifyDataSetChanged();
            Intent intent = new Intent(this, InputActivity.class);
            intent.putExtra("matchId", ""+matchCount);
            startActivity(intent);
        }
    }
}