package com.cs498MD.SportsRecorder;

public class Player {
    private String name;
    private int score;
    private int onePoint;
    private int twoPoint;
    private int threePoint;
    private int onePointAttempt;
    private int twoPointAttempt;
    private int threePointAttempt;
    private int foulCount;

    public int getOnePointAttempt() {
        return onePointAttempt;
    }

    public void setOnePointAttempt(int onePointAttempt) {
        this.onePointAttempt = onePointAttempt;
    }

    public int getTwoPointAttempt() {
        return twoPointAttempt;
    }

    public void setTwoPointAttempt(int twoPointAttempt) {
        this.twoPointAttempt = twoPointAttempt;
    }

    public int getThreePointAttempt() {
        return threePointAttempt;
    }

    public void setThreePointAttempt(int threePointAttempt) {
        this.threePointAttempt = threePointAttempt;
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
