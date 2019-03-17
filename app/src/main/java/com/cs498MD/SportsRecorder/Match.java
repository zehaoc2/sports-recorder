package com.cs498MD.SportsRecorder;

import java.util.ArrayList;

public class Match {
    MyTeam myTeam;
    OpponentTeam opponentTeam;
    ArrayList<Player> players;

    public Match() {
        myTeam = new MyTeam();
        opponentTeam = new OpponentTeam();
    }

    public MyTeam getMyTeam() {
        return myTeam;
    }

    public OpponentTeam getOpponentTeam() {
        return opponentTeam;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
