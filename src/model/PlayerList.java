package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
        if (playerID < playersList.size()) {
            return playersList.get(playerID);
        } else {
            return null;
        }

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
     * Given a HashSet with ID numbers of players, this function sorts
     * the set by the number (position) of the player.
     * This tool is useful to show the list or to print it.
     * @param players The Hashset wit the playersID.
     * @return an ArrayList sorted ascending.
     */
    public ArrayList<Integer> sortedListPlayerByNumber(HashSet<Integer> players) {
        ArrayList<Integer> tempSortedPlayersList = new ArrayList(players);
        Collections.sort(tempSortedPlayersList, (P1, P2) -> {
            if (getPlayerByPlayerId(P1).getNumber() < getPlayerByPlayerId(P2).getNumber() ) {
                return -1;
            } else {
                return 1;
            }
        });
        return tempSortedPlayersList;
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
     * This method count from a given match through the past,
     * counting how many times a player plays without stopping.
     * As soon as a player does not appear in the list, reset the player time to zero.
     * @param match The match to be used as an initial count in the timeline.
     * @param matchList the Match List.
     */
    public void updateTimeNoStop(Match match, MatchList matchList) {
        updateTimeNoStop(match.getDate(), matchList);
    }

    /**
     * The same method, but in this case, the start of the timeline
     * is not a match but a given date.
     * @param date A date.
     * @param matchList the Match List.
     */
    public void updateTimeNoStop(LocalDate date, MatchList matchList) {
        clearTimesPlayer();
        MatchList sortedMatchList = matchList.sortedMatchListByDate();
        int matchIndex = 0;
        while (matchIndex < sortedMatchList.getSize() &&
                sortedMatchList.getMatchByIndex(matchIndex).getDate().isBefore(date))  {
            HashSet<Integer> playersPitch = sortedMatchList.getMatchByIndex(matchIndex).getPlayersPitch();
            for (int i=0; i < getSize(); i++) {
                if (playersPitch.contains(getPlayerByPlayerId(i).getPlayerId())) {
                    getPlayerByPlayerId(i).addTimesNoStop();
                } else {
                    getPlayerByPlayerId(i).resetTimeNoStop();
                }
            }
            matchIndex++;
        }
        for (int i=0; i < sortedMatchList.getSize(); i++) {
            if (matchList.getMatchByIndex(i).getDate().isAfter(date)) {
                for (Integer playerIndex: matchList.getMatchByIndex(i).getPlayersPitch()) {
                    getPlayerByPlayerId(playerIndex).addTimesNoStop();
                }
            }
        }
    }



    /**
     * This method searches throughout the MatchList to all the past matches
     * and returns the player who has played more consecutive times without rest.
     * @param matchList the Match List.
     * @return a Player Object
     */
    public Player mostPlayedPlayer(MatchList matchList) {
        if (matchList == null || matchList.isEmpty()) {
            return null;
        } else {
            updateTimeNoStop(LocalDate.now(), matchList);
            Player mostPlayed;
            if (isEmpty()) {
                return null;
            } else {
                mostPlayed = getPlayerByPlayerId(0);
                for (int i = 1; i < getSize(); i++) {
                    if (!getPlayerByPlayerId(i).systemStatus.isDeleted()) {
                        if (getPlayerByPlayerId(i).getTimesNoStop() > mostPlayed.getTimesNoStop()) {
                            mostPlayed = getPlayerByPlayerId(i);
                        }

                    }
                }
            }
            return mostPlayed;
        }
    }

    /**
     * This method returns a set with IDs of all the players who never have been on the pitch list in the past matches.
     * @param matchList the Match List.
     * @return a HashSet with all the playersID
     */
    public HashSet<Integer> neverPlayed(MatchList matchList) {
        HashSet<Integer> allPlayersHashset = new HashSet<Integer>();
        HashSet<Integer> hashSetNeverPlayed = new HashSet<Integer>();
        for (int i = 0; i < getSize(); i++) {
            allPlayersHashset.add(i);
        }

        for (int i = 0; i < matchList.getSize(); i++) {
            allPlayersHashset.removeAll(matchList.getMatchByIndex(i).getPlayersPitch());
        }
        for (int playerID : allPlayersHashset) {
            if (!getPlayerByPlayerId(playerID).systemStatus.isDeleted()) {
                hashSetNeverPlayed.add(playerID);
            }
        }
        return hashSetNeverPlayed;
    }













    /**
     * Print a string with a header to be identified.
     * @return a String with all the object information.
     */
    public String toString() {
        return "PRINTING PlayerList OBJECT: \n" + playersList;
    }

}
