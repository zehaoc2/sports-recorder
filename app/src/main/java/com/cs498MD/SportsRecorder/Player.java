package com.cs498MD.SportsRecorder;

public class Player {
    private String name;
    private int score;
    private int onePoint;
    private int twoPoint;
    private int threePoint;
    private int onePointMiss;
    private int twoPointMiss;
    private int threePointMiss;
    private int foulCount;

    public int getOnePointMiss() {
        return onePointMiss;
    }

    public void setOnePointMiss(int onePointMiss) {
        this.onePointMiss = onePointMiss;
    }

    public int getTwoPointMiss() {
        return twoPointMiss;
    }

    public void setTwoPointMiss(int twoPointMiss) {
        this.twoPointMiss = twoPointMiss;
    }

    public int getThreePointMiss() {
        return threePointMiss;
    }

    public void setThreePointMiss(int threePointMiss) {
        this.threePointMiss = threePointMiss;
    }

    public Player () {

    }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public int getFoulCount() {
        return foulCount;
    }

    public void setFoulCount(int foulCount) {
        this.foulCount = foulCount;
    }
}
