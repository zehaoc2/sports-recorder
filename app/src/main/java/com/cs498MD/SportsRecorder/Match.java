package com.cs498MD.SportsRecorder;
import java.util.Stack;

public class Match {
    private int id;
    private String name;
    private Stack<Period> periods;
    private boolean isDone;

    public Match(int id) {
        this.id = id;
        this.name = "Match " + id;
        periods = new Stack<>();
        periods.push(new Period());
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
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

    public Stack<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(Stack<Period> periods) {
        this.periods = periods;
    }
}
