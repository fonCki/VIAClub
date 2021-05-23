package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class contains a list of players.
 * @author @alfonsoridao
 * @version 3.1
 */

public class PlayerList implements Serializable {

    private ArrayList<Player> playersList;

    /**
     * Non-argument constructor initializing the playerList.
     */
    public PlayerList() {
        playersList = new ArrayList<Player>();
    }

    /**
     * Add a new player at the end of the list.
     * @param player the player to be added.
     */
    public void addToPlayerList(Player player) {
        playersList.add(player);
    }

    /**
     * Subtract a player from the list.
     * @param player the match to be eliminated.
     */
    public void deletePlayer(Player player) {
        player.systemStatus.setDeleted();
    }

    /**
     * Replace the player located in the position passes as a parameter,
     * for the new match passed as a parameter.
     * is the Index is bigger than the size, then the player is added at the end.
     * @param playerID the location to be replaced.
     * @param player the new match.
     */
    public void updatePlayerList(int playerID, Player player) {
        if (playerID < playersList.size()) {
            playersList.set(playerID, player);
        } else {
            playersList.add(player);
        }

    }

    /**
     *Getting a full list of all the players.
     * @return an ArrayList of player.
     */
    public ArrayList<Player> getPlayersList() {
        return this.playersList;
    }

    /**
     * Given a playerID return the player.
     * @param playerID the Unique player ID.
     * @return the player as a Player Object.
     */
    public Player getPlayerByPlayerId(int playerID) {
        return playersList.get(playerID);
    }

    /**
     * This function returns a list of the first 49 numbers available to
     * be taken for a new player. The condition to be available is that no
     * one has been selected for another player before.
     *
     * @return a HashSet.
     */
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


    /**
     * Getting the amount of players in the playerList.
     * @return an int with the number of players.
     */
    public int getSize() {
        return playersList.size();
    }

    /**
     * This function returns the number of players in the list without including the deleted.
     * @return an int with the number of players NOT DELETED.
     */
    public int getNumberOfPlayers() { // All except deleted
        int c=0;
        for (Player player: playersList) {
            if (!(player.systemStatus.isDeleted()))
                c++;
        }
        return c;
    }

    /**
     * This function goes through all the players and resets the times play variable.
     */
    public void clearTimesPlayer() {
        for (Player player: playersList) {
            player.resetTimeNoStop();
        }
    }

    /**
     * given a Player, return the unique PlayerID.
     * The PlayerID is also the position in the list.
     * return -1 if the player is not founded.
     * @param player the player.
     * @return an int with the position(PlayerID).
     */
    public int getPlayerID(Player player){
        if (player != null) {
            for (int i = 0; i < playersList.size(); i++) {
                if (playersList.get(i).equals(player)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Returns true if the list is empty, otherwise returns false.
     * @return a boolean response.
     */
    public boolean isEmpty() {
        return (getNumberOfPlayers() < 1);
    }


    /**
     * Print a string with a header to be identified.
     * @return a String with all the object information.
     */
    public String toString() {
        return "PRINTING PlayerList OBJECT: \n" + playersList;
    }

}
