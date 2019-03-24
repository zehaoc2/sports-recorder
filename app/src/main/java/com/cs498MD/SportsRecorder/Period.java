package com.cs498MD.SportsRecorder;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Stack;

import static android.content.Context.MODE_PRIVATE;

public class Period {
    private MyTeam myTeam;
    private OpponentTeam opponentTeam;
    private Stack<Action> history;

    public Period() {
        this.myTeam = new MyTeam();
        this.opponentTeam = new OpponentTeam();
        this.history = new Stack<>();
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
