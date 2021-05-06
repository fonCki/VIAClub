import java.util.ArrayList;

public class PlayerList {
    private ArrayList<Player> playersList;

    public PlayerList() {
        playersList = new ArrayList<Player>();
    }

    public void add(Player player) {
        playersList.add(player);
    }

    public void remove(Player player) {
        playersList.remove(player);
    }

    public Player[] getPlayersList() {
        return playersList.toArray(playersList.toArray(new Player[0]));
    }
}
