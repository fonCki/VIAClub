package model;

import utils.MyFileHandler;

import java.io.IOException;

/**
 * This class is a toolbox containing different methods related to players and player lists.
 * to work with files. all the methods are static.
 * @author @alfonsoridao
 * @version 3.1
 */

public class PlayerListManager {

    /**
     * this method access to the file, and return the Object playerList.
     * @return the list of players in playerList format.
     */
    public static PlayerList getPlayerListFromFile() {
        PlayerList playerList = new PlayerList();
        try {
            playerList = (PlayerList) MyFileHandler.readFromBinaryFile("player-list.via");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return playerList;
    }

    /**
     * Given the player list, this method call MyFileHandler to write the information in the file.
     * @param playerList the list of players.
     */
    public static void writeInFile(PlayerList playerList) {
        try {
            MyFileHandler.writeToBinaryFile("player-list.via", playerList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
