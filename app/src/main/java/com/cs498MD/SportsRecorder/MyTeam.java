package com.cs498MD.SportsRecorder;

public class MyTeam extends Team {
    private String name;
    public int score;
    private int onePoint;
    private int twoPoint;
    private int threePoint;
    private int foulCount;

    public MyTeam() {
        name = "My Team";
        onePoint = 0;
        twoPoint = 0;
        threePoint = 0;
        foulCount = 0;
        System.out.println(score);
    }

}