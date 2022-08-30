package model;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;

/**
 * This class represent a Match
 * @author @alfonsoridao
 * @version 3.1
 */

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

    /**
     * Four argument constructor initializing the match.
     * This is the only constructor, and besides the four variables passed as parameters,
     * the constructor generates two empty HashSet to be filled with the players on the pitch and bench.
     * Set the amount of players allowed to be on the bench.
     * And also, a unique ID is generated.
     * @param opponent the opponent of the match.
     * @param date the date of the match.
     * @param place the location of the match
     * @param kind the kind of match (League, Cup or Friendly)
     */
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

    /**
     * Getting the opponent.
     * @return the opponent in a String.
     */
    public String getOpponent() {
        return this.opponent;
    }

    /**
     * Setting the opponent.
     * @param rival the opponent.
     */
    public void setOpponent(String rival) {
        this.opponent = rival;
    }

    /**
     * Getting the place.
     * @return the location of the match in a String.
     */
    public String getPlace() {
        return this.place;
    }

    /**
     * Setting the place.
     * @param place the place.
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * Getting the date of the match.
     * @return the date of the match in LocalDate format.
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Setting the date of the match.
     * @param date the date of the match.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Getting the kind of the match.
     * @return the kind of the match in a String.
     */
    public String getKind() {
        return this.kind;
    }

    /**
     * Setting the kind of match.
     * @param kind the kind of match.
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * Getting the players who are in the list as Starters.
     * @return a Hashset of integers, representing the unique ID of the player.
     */
    public HashSet<Integer> getPlayersPitch() {
        return playersPitch;
    }

    /**
     * Setting the players who are in the pitch list.
     * @param playersPitch a Hashset with a set of uniques IDs.
     */
    public void setPlayersPitch(HashSet<Integer> playersPitch) {
        this.playersPitch = playersPitch;
    }

    /**
     * Getting the players who are in the list as bench Players.
     * @return a Hashset of integers, representing the unique ID of the player.
     */
    public HashSet<Integer> getPlayersBench() {
        return this.playersBench;
    }

    /**
     * Setting the players who are in the bench list.
     * @param playersBench a Hashset with a set of uniques IDs.
     */
    public void setPlayersBench(HashSet<Integer> playersBench) {
        this.playersBench = playersBench;
    }

    /**
     * Getting the max number of players allowed to be on the pitch.
     * @return an integer.
     */
    public int getMaxPlayersPitch() {
        return this.maxPlayersPitch;
    }

    /**
     * Getting the max number of players allowed to be on the bench.
     * @return an integer.
     */
    public int getMaxPlayersBench() {
        return this.maxPlayersBench;
    }

    /**
     * Getting the Unique Match ID.
     * @return the matchID as a String.
     */
    public String getMatchUID() {
        return this.matchUID;
    }

    /**
     * Setting the match If
     * @param matchUID the match ID as a string.
     */
    public void setMatchUID(String matchUID){
        this.matchUID = matchUID;
    }

    /**
     * Print a string in a nice way.
     * @return a String with all the object information.
     */
    public String toString() {
        return  "model.Match----------------------------\n" +
                "model.Match UID: " + matchUID + "\n" +
                "opponent: " + opponent + "\n" +
                "date: " + date + "\n" +
                "place: " + place + "\n" +
                "kind: " + kind + "\n" +
                "playersPitch: " + playersPitch + "\n" +
                "playersBench" + playersBench;

    }

    /**
     * Compare if all the fields in a object are equals.
     * @param obj the object to compare.
     * @return boolean response.
     */
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
