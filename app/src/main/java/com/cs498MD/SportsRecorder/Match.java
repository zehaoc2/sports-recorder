package com.cs498MD.SportsRecorder;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Stack;

import static android.content.Context.MODE_PRIVATE;

public class Match {
    private int id;
    private String name;
    private MyTeam myTeam;
    private OpponentTeam opponentTeam;
    private Stack<Action> history;

    public Match() {
    }

    public Match(int id) {
        this.id = id;
        this.name = "Match" + id;
        this.myTeam = new MyTeam();
        this.opponentTeam = new OpponentTeam();
        this.history = new Stack<Action>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyTeam getMyTeam() {
        return myTeam;
    }

    public OpponentTeam getOpponentTeam() {
        return opponentTeam;
    }

    public Stack<Action> getHistory() {
        return history;
    }

    public void setHistory(Stack<Action> history) {
        this.history = history;
    }
}
