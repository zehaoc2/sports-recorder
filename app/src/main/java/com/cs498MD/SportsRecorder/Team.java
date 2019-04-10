package com.cs498MD.SportsRecorder;

import java.util.ArrayList;

public class Team {
    private String name;
    private int score;
    private int onePoint;
    private int twoPoint;
    private int threePoint;
    private int miss;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOnePoint() {
        return onePoint;
    }

    public void setOnePoint(int onePoint) {
        this.onePoint = onePoint;
    }

    public int getTwoPoint() {
        return twoPoint;
    }

    public void setTwoPoint(int twoPoint) {
        this.twoPoint = twoPoint;
    }

    public int getThreePoint() {
        return threePoint;
    }

    public void setThreePoint(int threePoint) {
        this.threePoint = threePoint;
    }

    public int getMiss() {
        return miss;
    }

    public void setMiss(int miss) {
        this.miss = miss;
    }
}
