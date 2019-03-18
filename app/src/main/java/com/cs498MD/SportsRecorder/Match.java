package com.cs498MD.SportsRecorder;

import java.util.ArrayList;
import java.util.Stack;

public class Match {
    MyTeam myTeam;
    OpponentTeam opponentTeam;
    ArrayList<Player> players;
    Stack<Action> history;

    public Match() {
        myTeam = new MyTeam();
        opponentTeam = new OpponentTeam();
        history = new Stack<Action>();
        history.push(null);
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

    public Stack<Action> getHistory() {
        return history;
    }

    public void setHistory(Stack<Action> history) {
        this.history = history;
    }
}
