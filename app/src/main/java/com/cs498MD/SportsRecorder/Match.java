package com.cs498MD.SportsRecorder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Match {
    MyTeam myTeam;
    OpponentTeam opponentTeam;
    ArrayList<Player> players;
    Stack<String> history;

    public Match() {
        myTeam = new MyTeam();
        opponentTeam = new OpponentTeam();
        history = new Stack<>();
        history.push("");
    }

    public MyTeam getMyTeam() {
        return myTeam;
    }

    public OpponentTeam getOpponentTeam() {
        return opponentTeam;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Stack<String> getHistory() {
        return history;
    }

    public void setHistory(Stack<String> history) {
        this.history = history;
    }
}
