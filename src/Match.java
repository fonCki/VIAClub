import java.io.Serializable;
import java.security.SecureRandomParameters;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class Match implements Serializable {
    private String opponent;
    private LocalDate date;
    private String place;
    private String kind;
    private ArrayList<Integer> playersPitch;
    private ArrayList<Integer> playersBench;
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
                maxPlayersBench = 50;
                break;
            }
        };
        playersPitch = new ArrayList<Integer>(maxPlayersPitch);
        playersBench = new ArrayList<Integer>(maxPlayersBench);

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

    public ArrayList<Integer> getPlayersPitch() {
        return playersPitch;
    }

    public void setPlayersPitch(ArrayList<Integer> playersPitch) {
        this.playersPitch = playersPitch;
    }

    public ArrayList<Integer> getPlayersBench() {
        return playersBench;
    }

    public void setPlayersBench(ArrayList<Integer> playersBench) {
        this.playersBench = playersBench;
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
