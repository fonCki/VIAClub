import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class PlayerListManager {

    public static PlayerList getPlayerListFromFile() {
        PlayerList playerList = new PlayerList();
        try {
            playerList = (PlayerList) MyFileHandler.readFromBinaryFile("Player-list.via");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
/*
        finally {
            HashSet<String> example= new HashSet<String>(4);
            example.add("Goalkeeper");
            example.add("Defender");
            playerList = new PlayerList();
            Player playerOne = new Player("Hugo", "Broglio", LocalDate.now(), 9, example, "Suspended");
            Player playerTwo = new Player("Mariano", "Cola", LocalDate.now(), 10, example, "Available");
            Player playerThree = new Player("Jose", "Pasuccho", LocalDate.now(), 5,example, "Injured");
            playerList.add(playerOne);
            playerList.add(playerTwo);
            playerList.add(playerThree);
            System.out.println(playerList);

        }

 */
        return playerList;
    }

    public static void writeInFile(PlayerList playerList) {
        try {
            MyFileHandler.writeToBinaryFile("Player-list.via", playerList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void savePlayer(PlayerList playerlist, Player player, int uid) {
        if (uid < playerlist.getSize()) {
            playerlist.updatePlayerList(uid, player);
        } else {
            playerlist.addToPlayerList(player);
        }

    }
/*
    public static ArrayList<Integer> getAvailableNumbers() {
        return getPlayerListUpdated().getAvailableNumbers();
    }

    public static void addPlayer(Player player){
        getPlayerListUpdated().add(player);
    }

 */
}
