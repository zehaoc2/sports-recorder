package com.cs498MD.SportsRecorder;

import java.util.ArrayList;

public class Match {
    int matchId;
    Team myTeam;
    Team opponentTeam;
    ArrayList<Player> players;

    public Match(Team myTeam, Team opponentTeam, ArrayList<Player> players) {
        this.myTeam = myTeam;
        this.opponentTeam = opponentTeam;
        this.players = players;
    }
}
