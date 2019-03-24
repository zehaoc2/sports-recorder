package com.cs498MD.SportsRecorder;
import java.util.Stack;

public class Match {
    private int id;
    private String name;
    private Stack<Period> periods;

    public Match(int id) {
        this.id = id;
        this.name = "Match " + id;
        periods = new Stack<>();
        periods.push(new Period());
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
