package model;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;

public class Match implements Serializable {

    private String opponent;
    private LocalDate date;
    private String place;
    private String kind;
    private HashSet<Integer> playersPitch;
    private HashSet<Integer> playersBench;
    private final int maxPlayersPitch = 11;
    private int maxPlayersBench;
    private String matchUID;


    public Match(String opponent, LocalDate date, String place, String kind) {
        this.opponent = opponent;
        this.date = date;
        this.place = place;
        this.kind = kind;
        switch (kind) {
            case "League" : {
                maxPlayersBench = 6;
                break;
                }
            case "Cup" : {
                maxPlayersBench = 5;
                break;
            }
            case "Friendly" : {
                maxPlayersBench = 1000;
                break;
            }
        };
        playersPitch = new HashSet<>(maxPlayersPitch);
        playersBench = new HashSet<>(maxPlayersBench);
        this.matchUID = "UID"+Instant.now().getEpochSecond();
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String rival) {
        this.opponent = rival;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public HashSet<Integer> getPlayersPitch() {
        return playersPitch;
    }

    public void setPlayersPitch(HashSet<Integer> playersPitch) {
        this.playersPitch = playersPitch;
    }

    public HashSet<Integer> getPlayersBench() {
        return playersBench;
    }

    public void setPlayersBench(HashSet<Integer> playersBench) {
        this.playersBench = playersBench;
    }

    public int getMaxPlayersPitch() {
        return this.maxPlayersPitch;
    }

    public int getMaxPlayersBench() {
        return this.maxPlayersBench;
    }
    public String getMatchUID() {
        return this.matchUID;
    }

    public void setMatchUID(String matchUID){
        this.matchUID = matchUID;
    }

    @Override
    public String toString() {
        return "model.Match----------------------------\n" +
                "model.Match UID: " + matchUID + "\n" +
                "opponent: " + opponent + "\n" +
                "date: " + date + "\n" +
                "place: " + place + "\n" +
                "kind: " + kind + "\n" +
                "playersPitch: " + playersPitch + "\n" +
                "playersBench" + playersBench;

    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Match)) {
            return false;
        } else {
            return ((Match) obj).opponent.equals(opponent) &&
                    ((Match) obj).date.equals(date) &&
                    ((Match) obj).place.equals(place) &&
                    ((Match) obj).kind.equals(kind) &&
                    ((Match) obj).playersPitch.equals(playersPitch) &&
                    ((Match) obj).playersBench.equals(playersBench) &&
                    ((Match) obj).maxPlayersPitch == maxPlayersPitch &&
                    ((Match) obj).maxPlayersBench == maxPlayersBench &&
                    ((Match) obj).matchUID.equals(matchUID);
        }
    }
}
