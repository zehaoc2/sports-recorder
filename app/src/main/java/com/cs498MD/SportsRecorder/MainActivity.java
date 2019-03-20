package com.cs498MD.SportsRecorder;


import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static FloatingActionButton newMatch;
    private ArrayList<String> matchArray = new ArrayList<>();
    private MyCustomAdapter adapter;

//    private String MATCH = "matchId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setTitle("Matches");


        SharedPreferences sharedPreferences = this.getSharedPreferences("matchId", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("matchId", "Match1");
        editor.apply();

        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            matchArray.add(entry.getValue().toString());
        }
//        String matchJson = sharedPreferences.getString(matchId, "");

        newMatch = (FloatingActionButton) findViewById(R.id.newMatch);
        newMatch.setOnClickListener(this);

        adapter = new MyCustomAdapter(matchArray, this, MainActivity.this);

        ListView listView = (ListView) findViewById(R.id.matchList);
        listView.setEmptyView(findViewById(R.id.noMatches));
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("match", MODE_PRIVATE);
        int matchCount = sharedPreferences.getInt("matchCount", -1);

        matchCount++;
        String matchId = "Match" + matchCount;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("matchCount", matchCount);
        editor.putString("matchId", matchId);
        editor.apply();

        if (v.getId() == R.id.newMatch) {
            matchArray.add(matchId);
            adapter.notifyDataSetChanged();
            Intent intent = new Intent(this, InputActivity.class);
            intent.putExtra("matchId", Integer.toString(matchCount));
            startActivity(intent);
        }
    }
}