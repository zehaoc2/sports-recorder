package com.cs498MD.SportsRecorder;

import java.util.Stack;

public class Period {
    private Team others;
    private Team kid;
    private Team opponent;
    private Stack<Action> history;

    public Period() {
        this.others = new Team("Others");
        this.kid = new Team("My Kid");
        this.opponent = new Team("Opponent");
        this.history = new Stack<>();
    }

    public Team getOthers() {
        return others;
    }

    public void setOthers(Team others) {
        this.others = others;
    }

    public Team getKid() {
        return kid;
    }

    public void setKid(Team kid) {
        this.kid = kid;
    }

    public Team getOpponent() {
        return opponent;
    }

    public void setOpponent(Team opponent) {
        this.opponent = opponent;
    }

    public Stack<Action> getHistory() {
        return history;
    }

    public void setHistory(Stack<Action> history) {
        this.history = history;
    }
}
