import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class PlayerList implements Serializable {
    private ArrayList<Player> playersList;

    public PlayerList() {
        playersList = new ArrayList<Player>();
    }

    public void addToPlayerList(Player player) {
        playersList.add(player);
    }

    public void deletePlayer(Player player) {
        player.systemStatus.setDeleted();
    }

    public void updatePlayerList(int uid, Player player) {
        playersList.set(uid, player);
    }

    public ArrayList<Player> getPlayersList() {
        return this.playersList;
    }




    public Player getPlayerByPlayerId(int playerId) {
        return playersList.get(playerId);
    }

    public HashSet<Integer> getAvailableNumbers() {
        HashSet<Integer> tempHashset = new HashSet<Integer>();
        for (int i=1; i < playersList.size() + 50; i++) {
            tempHashset.add(i);
        }
        for (Player player: playersList) {
            if (!(player.systemStatus.isDeleted())) {
                tempHashset.remove(player.getNumber());
            }
        }
        return tempHashset;
    }


    public int getSize() {
        return playersList.size();
    }

    public int getNumberOfPlayers() { // All except deleted
        int c=0;
        for (Player player: playersList) {
            if (!(player.systemStatus.isDeleted()))
                c++;
        }
        return c;
    }

    public int getPosition(Player player){
        if (player != null) {
            for (int i = 0; i < playersList.size(); i++) {
                if (playersList.get(i).equals(player)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return (getNumberOfPlayers() < 1);
    }


    @Override
    public String toString() {
        return "PRINTING PlayerList OBJECT: \n" + playersList;
    }

}
