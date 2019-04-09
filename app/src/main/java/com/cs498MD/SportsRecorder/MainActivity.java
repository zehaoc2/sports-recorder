package com.cs498MD.SportsRecorder;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static FloatingActionButton newMatch;
    private ArrayList<String> matchNameArray = new ArrayList<>();
    private ArrayList<String> matchIdArray = new ArrayList<>();
    private MyCustomAdapter adapter;

    private String MATCH = "match";

    private boolean isNumeric(String num) {
        return num.matches("[0-9]+");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setTitle("Matches");

        newMatch = (FloatingActionButton) findViewById(R.id.newMatch);
        newMatch.setOnClickListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences(MATCH, MODE_PRIVATE);
        Gson gson = new Gson();

        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String matchId = entry.getKey();

            if (!isNumeric(matchId)) {
                continue;
            }

            String matchJson = sharedPreferences.getString(matchId, "");
            Match match = gson.fromJson(matchJson, Match.class);

            matchNameArray.add(match.getName());
            matchIdArray.add(matchId);
        }

        adapter = new MyCustomAdapter(matchNameArray, matchIdArray, this, MainActivity.this);

        ListView listView = (ListView) findViewById(R.id.matchList);
        listView.setEmptyView(findViewById(R.id.noMatches));
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("match", MODE_PRIVATE);
        int matchCount = sharedPreferences.getInt("matchCount", -1);

        matchCount++;
        String matchName = "Match " + matchCount;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("matchCount", matchCount);
        editor.putString("matchId", matchName);
        editor.apply();

        if (v.getId() == R.id.newMatch) {
            matchNameArray.add(matchName);

            matchIdArray.add("" + matchCount);
            adapter.notifyDataSetChanged();
            Intent intent = new Intent(this, InputActivity.class);
            intent.putExtra("matchId", Integer.toString(matchCount));
            startActivity(intent);
        }
    }
}