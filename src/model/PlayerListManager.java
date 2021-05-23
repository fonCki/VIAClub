package model;

import utils.MyFileHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;

public class PlayerListManager {

    public static PlayerList getPlayerListFromFile() {
        PlayerList playerList = new PlayerList();
        try {
            playerList = (PlayerList) MyFileHandler.readFromBinaryFile("model.Player-list.via");
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
            playerList = new model.PlayerList();
            model.Player playerOne = new model.Player("Hugo", "Broglio", LocalDate.now(), 9, example, "Suspended");
            model.Player playerTwo = new model.Player("Mariano", "Cola", LocalDate.now(), 10, example, "Available");
            model.Player playerThree = new model.Player("Jose", "Pasuccho", LocalDate.now(), 5,example, "Injured");
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
            MyFileHandler.writeToBinaryFile("model.Player-list.via", playerList);
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

    public static void clearTimesPlayed(PlayerList playerList) {
        for (int i=0; i < playerList.getSize(); i++) {
            playerList.getPlayerByPlayerId(i).resetTimeNoStop();
        }
    }

    public static void updateTimeNoStop(Match match, PlayerList playerList, MatchList matchList) {
        updateTimeNoStop(match.getDate(), playerList, matchList);
    }

    public static void updateTimeNoStop(LocalDate date, PlayerList playerList, MatchList matchList) {
        PlayerListManager.clearTimesPlayed(playerList);
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
