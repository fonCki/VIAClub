package model;

import utils.MyFileHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;

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
            MyFileHandler.writeToBinaryFile("model.Player-list.via", playerList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
    public static void savePlayer(PlayerList playerlist, Player player, int uid) {
        if (uid < playerlist.getSize()) {
            playerlist.updatePlayerList(uid, player);
        } else {
            playerlist.addToPlayerList(player);
        }

    }

    public static void clearTimesPlayed(PlayerList playerList) {
        for (int i=0; i < playerList.getSize(); i++) {
            playerList.getPlayerByPlayerId(i).resetTimeNoStop();
        }
    }
*/
    public static void updateTimeNoStop(Match match, PlayerList playerList, MatchList matchList) {
        updateTimeNoStop(match.getDate(), playerList, matchList);
    }

    public static void updateTimeNoStop(LocalDate date, PlayerList playerList, MatchList matchList) {
        playerList.clearTimesPlayer();
        MatchList sortedMatchList = matchList.sortedMatchListByDate();
        int matchIndex = 0;
        while (matchIndex < sortedMatchList.getSize() &&
                sortedMatchList.getMatchByIndex(matchIndex).getDate().isBefore(date))  {
            HashSet<Integer> playersPitch = sortedMatchList.getMatchByIndex(matchIndex).getPlayersPitch();
            for (int i=0; i < playerList.getSize(); i++) {
                if (playersPitch.contains(playerList.getPlayerByPlayerId(i).getPlayerId())) {
                    playerList.getPlayerByPlayerId(i).addTimesNoStop();
                } else {
                    playerList.getPlayerByPlayerId(i).resetTimeNoStop();
                }
            }
            matchIndex++;
        }
        for (int i=0; i < sortedMatchList.getSize(); i++) {
            if (matchList.getMatchByIndex(i).getDate().isAfter(date)) {
                for (Integer playerIndex: matchList.getMatchByIndex(i).getPlayersPitch()) {
                    playerList.getPlayerByPlayerId(playerIndex).addTimesNoStop();
                }
            }
        }
    }

    public static Player mostPlayedPlayer(PlayerList playerList, MatchList matchList) {
        if (matchList == null || matchList.isEmpty()) {
            return null;
        } else {
            updateTimeNoStop(LocalDate.now(), playerList, matchList);
            Player mostPlayed;
            if (playerList.isEmpty()) {
                return null;
            } else {
                mostPlayed = playerList.getPlayerByPlayerId(0);
                for (int i = 1; i < playerList.getSize(); i++) {
                    if (!playerList.getPlayerByPlayerId(i).systemStatus.isDeleted()) {
                        if (playerList.getPlayerByPlayerId(i).getTimesNoStop() > mostPlayed.getTimesNoStop()) {
                            mostPlayed = playerList.getPlayerByPlayerId(i);
                        }

                    }
                }
            }
            return mostPlayed;
        }
    }

    public static HashSet<Integer> neverPlayed(PlayerList playerList, MatchList matchList) {
        HashSet<Integer> allPlayersHashset = new HashSet<Integer>();
        HashSet<Integer> hashSetNeverPlayed = new HashSet<Integer>();
        for (int i = 0; i < playerList.getSize(); i++) {
            allPlayersHashset.add(i);
        }

        for (int i = 0; i < matchList.getSize(); i++) {
            allPlayersHashset.removeAll(matchList.getMatchByIndex(i).getPlayersPitch());
        }
        for (int playerID : allPlayersHashset) {
            if (!playerList.getPlayerByPlayerId(playerID).systemStatus.isDeleted()) {
                hashSetNeverPlayed.add(playerID);
            }
        }
        return hashSetNeverPlayed;
    }
/*
    public static model.Player lessPlayedPlayer(model.PlayerList playerList, model.MatchList matchList) {
        updateTimeNoStop(matchList.nextMatch(), playerList, matchList);
        model.Player lessPlayed;
        if (playerList.isEmpty()) {
            return null;
        } else {
            lessPlayed = playerList.getPlayerByPlayerId(0);
            for (int i = 1; i < playerList.getSize(); i++) {
                if (!playerList.getPlayerByPlayerId(i).systemStatus.isDeleted()) {
                    if (playerList.getPlayerByPlayerId(i).getTimesNoStop() < lessPlayed.getTimesNoStop()) {
                        lessPlayed = playerList.getPlayerByPlayerId(i);
                    }
                }
            }
        }
        return lessPlayed;
    }

*/

/*
    public static ArrayList<Integer> getAvailableNumbers() {
        return getPlayerListUpdated().getAvailableNumbers();
    }

    public static void addPlayer(model.Player player){
        getPlayerListUpdated().add(player);
    }

 */
}
