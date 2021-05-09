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
    private ArrayList<Player> playersPitch;
    private ArrayList<Player> playersBench;
    private int maxPlayersBench;
    private int players;

    public Match() {

    }

    public Match(String opponent, LocalDate date, String place, String kind) {
        switch (kind) {
            case "League" : {
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
        this.opponent = opponent;
        this.date = date;
        this.place = place;
        this.kind = kind;
        this.playersPitch = new ArrayList<Player>(11);
        this.playersBench = new ArrayList<Player>(maxPlayersBench);
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

    public ArrayList<Player> getPlayersPitch() {
        return playersPitch;
    }

    public void setPlayersPitch(ArrayList<Player> players) {
        this.playersPitch = players;
    }

    public ArrayList<Player> getPlayersBench() {
        return playersBench;
    }

    public void setPlayersBench(ArrayList<Player> playersBench) {
        this.playersBench = playersBench;
    }

    public void addPlayerPitch(Player player) {
        this.playersPitch.add(player);
    }


    @Override
    public String toString() {
        return "Match----------------------------\n" +
                "opponent: " + opponent + "\n" +
                "date: " + date + "\n" +
                "place: " + place + "\n" +
                "playersPitch: " + playersPitch + "\n" +
                "playersBench: " + playersPitch + "\n" +
                "----------------------------------\n";
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Match)) {
            return false;
        } else {
            return ((Match) obj).opponent.equals(opponent) &&
                    ((Match) obj).date.equals(date) &&
                    ((Match) obj).place.equals(place) &&
                    ((Match) obj).kind.equals(kind); //&&
//                    ((Match) obj).playersPitch.equals(playersPitch) &&
//                    ((Match) obj).playersBench.equals(playersBench);
        }
    }
}
