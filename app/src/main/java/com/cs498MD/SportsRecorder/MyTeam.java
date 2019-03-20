package com.cs498MD.SportsRecorder;

import java.util.ArrayList;

public class MyTeam extends Team {
    private int onePoint;
    private int twoPoint;
    private int threePoint;
    private int foulCount;
    private ArrayList<Player> players;

    public MyTeam() {
        name = "My Team";
        players = new ArrayList<>();
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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}