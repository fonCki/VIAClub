import java.io.Serializable;
import java.util.ArrayList;

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


    public Player getPlayerByNumber(int number) {
        for (Player player: playersList) {
            if (player.getNumber() == number)
                return player;
        }
        return null;
    }

    public ArrayList<Integer> getAvailableNumbers() {
        ArrayList<Integer> tempArray = new ArrayList<Integer>();
        for (int i=1; i < 100; i++) {
            if (getPlayerByNumber(i) == null ||
                    getPlayerByNumber(i).systemStatus.isDeleted()){
                tempArray.add(i);
            }
        }
        return tempArray;
    }

    public int getSize() {
        System.out.println(playersList.size());
        return playersList.size();
    }

    public int getPlayers() { // All except deleted
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


    @Override
    public String toString() {
        return "PRINTING PlayerList OBJECT: \n" + playersList;
    }

}
