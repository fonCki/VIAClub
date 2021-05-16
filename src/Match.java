import java.io.Serializable;
import java.security.SecureRandomParameters;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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


    public Match(String opponent, LocalDate date, String place, String kind) {
        this.opponent = opponent;
        this.date = date;
        this.place = place;
        this.kind = kind;
        switch (kind.toLowerCase()) {
            case "league" : {
                maxPlayersBench = 6;
                break;
                }
            case "cup" : {
                maxPlayersBench = 5;
                break;
            }
            case "friendly" : {
                maxPlayersBench = 1000;
                break;
            }
        };
        playersPitch = new HashSet<>(maxPlayersPitch);
        playersBench = new HashSet<>(maxPlayersBench);

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

    @Override
    public String toString() {
        return "Match----------------------------\n" +
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
                    ((Match) obj).playersBench.equals(playersBench);
        }
    }
}
