package com.cs498MD.SportsRecorder;
import java.util.Stack;

public class Match {
    private int id;
    private String name;
    private Period[] periods = new Period[5];
    private boolean isDone;

    public Match(int id) {
        this.id = id;
        this.name = "Match " + id;
        for (int i = 0; i < periods.length; i++) {
            periods[i] = new Period();
        }
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

    public Period[] getPeriods() {
        return periods;
    }

    public void setPeriods(Period[] periods) {
        this.periods = periods;
    }
}
