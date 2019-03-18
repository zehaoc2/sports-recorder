package com.cs498MD.SportsRecorder;

enum Type {Attempt, Score, Foul;}

public class Action {
    private String teamName;
    Type type;
    int point;
    String message;

    public Type getType() {
        return type;
    }

    public Action(String teamName, Type type, int point) {
        this.teamName = teamName;
        this.type = type;
        this.point = point;

        if (type == Type.Attempt) {
            this.message = teamName + " missed " + point + " point" + (point == 1 ? "!" : "s!");
        } else if (type == Type.Score) {
            this.message = teamName + " got " + point + " point" + (point == 1 ? "!" : "s!");
        } else {
            this.message = teamName + " made a foul!";
        }
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
