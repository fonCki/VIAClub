package model;
import utils.MyFileHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * This class is a toolbox containing different methods related to match and match lists.
 * to work with files. all the methods are static.
 * @author @alfonsoridao
 * @version 3.1
 */

public class MatchListManager {

    /**
     * this method access to the file, and return the Object MatchList.
     * @return the list of match in MatchList format.
     */
    public static MatchList getMatchListFromFile()  {
        MatchList matchList = new MatchList();
        try {
            matchList = (MatchList) MyFileHandler.readFromBinaryFile("match-List.via");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return matchList;
    }

    /**
     * Given the match list, this method call MyFileHandler to write the information in the file.
     * @param matchList the list of matches.
     */
    public static void writeInFile(MatchList matchList) {
        try {
            MyFileHandler.writeToBinaryFile("model.Match-List.via", matchList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
    public static void addMatch(MatchList matchList, Match match) {
        matchList.addMatch(match);
    }


    public static void deleteMatch(MatchList matchList, Match match) {
        matchList.deleteMatch(match);
    }

    public static int getPosition(MatchList matchList, Match match) {
        return matchList.getIndex(match);
    }

    public static int getNewPosition(MatchList matchList) {
        return matchList.getSize();
    }

    public static void saveMatch(MatchList matchList, Match match, int id) {
        if (id < matchList.getSize()) {
            matchList.updateMatch(id, match);
        } else {
            matchList.addMatch(match);
        }
    }
*/


    /**Given a HashSet with ID numbers of players, this function sorts
     * the set by the number (position) of the player.
     * This tool is useful to show the list or to print it.
     * @param players The Hashset wit the playersID.
     * @param playerList The list of the players.
     * @return an ArrayList sorted ascending.
     */
    public static ArrayList<Integer> sortedMatchListByNumber(HashSet<Integer> players, PlayerList playerList) {
        ArrayList<Integer> tempSortedPlayersList = new ArrayList(players);
        Collections.sort(tempSortedPlayersList, (P1, P2) -> {
            if (playerList.getPlayerByPlayerId(P1).getNumber() < playerList.getPlayerByPlayerId(P2).getNumber() ) {
                return -1;
            } else {
                return 1;
            }
        });
        return tempSortedPlayersList;
    }

    /**
     * Create a String with all the information in a specific match.
     * After creation, the string is saved as a text file, with the file named with the unique match ID,
     * and the extension as .txt.
     * @param match the match to be converted.
     * @param playerList the complete list of all players in the system.
     */
    public static void createText(Match match, PlayerList playerList) {

        String title = "\t\tVia Club vs " + match.getOpponent() + "\n\n";
        String info = "\tDate: " + match.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)) + "\n" +
                      "\tPlace: " + match.getPlace() + "\n" +
                      "\tKind: " + match.getKind() + "\n\n";

        String playersPitch = "Players in the pitch: \n\n";
        ArrayList<Integer> sorted = sortedMatchListByNumber(match.getPlayersPitch(), playerList);
        for (int playerIndex: sorted) {
            playersPitch += playerList.getPlayerByPlayerId(playerIndex).getNumber() + ")" +
                    playerList.getPlayerByPlayerId(playerIndex).getName() + " " +
                    playerList.getPlayerByPlayerId(playerIndex).getLastName() + ", " +
                    playerList.getPlayerByPlayerId(playerIndex).getPosition() + "\n";
        }

        String playersBench = "\n\nPlayers in the bench: \n\n";
        ArrayList<Integer> sortedBench = sortedMatchListByNumber(match.getPlayersBench(), playerList);
        for (int playerIndex: sortedBench) {
            playersBench += playerList.getPlayerByPlayerId(playerIndex).getNumber() + ")" +
                    playerList.getPlayerByPlayerId(playerIndex).getName() + " " +
                    playerList.getPlayerByPlayerId(playerIndex).getLastName() + ", " +
                    playerList.getPlayerByPlayerId(playerIndex).getPosition() + "\n";
        }

        String list = title + info + playersPitch + playersBench;
        String fileName = "match_" +match.getMatchUID() + ".txt";
        try {
            MyFileHandler.writeToTextFile(fileName,list);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a String with all the information in a specific match.
     * The string also includes tags and XML format. After creation, the string is saved as a xml file,
     * with the file named with the unique match ID and the extension as .xml
     * @param match the match to be converted.
     * @param playerList the complete list of all players in the system.
     */
    public static void createXML(Match match, PlayerList playerList) {
        String title = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n" +
                        "<match>" + "\n";
        String info = "\t<opponent>"+ match.getOpponent() +"</opponent>" + "\n" +
                      "\t<date>"+ match.getDate() +"</date>" + "\n" +
                      "\t<place>"+ match.getPlace() +"</place>" + "\n" +
                      "\t<kind>"+ match.getKind() +"</kind>" + "\n";

        String startPlayersPitch = "\t<players>\n";
        ArrayList<Integer> sorted = sortedMatchListByNumber(match.getPlayersPitch(), playerList);
        for (int playerIndex: sorted) {
            startPlayersPitch += "\t\t<pitchPlayer>\n" +
                    "\t\t\t<number>" + playerList.getPlayerByPlayerId(playerIndex).getNumber() + "</number>\n" +
                    "\t\t\t<name>" + playerList.getPlayerByPlayerId(playerIndex).getName() + "</name>\n" +
                    "\t\t\t<lastName>" + playerList.getPlayerByPlayerId(playerIndex).getLastName() + "</lastName>\n" +
                    "\t\t\t<position>" + playerList.getPlayerByPlayerId(playerIndex).getPosition() + "</position>\n" +
                    "\t\t</pitchPlayer>\n";
        }

        String startPlayersBench = "";
        ArrayList<Integer> sortedBench = sortedMatchListByNumber(match.getPlayersBench(), playerList);
        for (int playerIndex: sortedBench) {
            startPlayersBench += "\t\t<benchPlayer>\n" +
                    "\t\t\t<number>" + playerList.getPlayerByPlayerId(playerIndex).getNumber() + "</number>\n" +
                    "\t\t\t<name>" + playerList.getPlayerByPlayerId(playerIndex).getName() + "</name>\n" +
                    "\t\t\t<lastName>" + playerList.getPlayerByPlayerId(playerIndex).getLastName() + "</lastName>\n" +
                    "\t\t\t<position>" + playerList.getPlayerByPlayerId(playerIndex).getPosition() + "</position>\n" +
                    "\t\t</benchPlayer>\n";
        }

        String finishPlayers = "\t</players>\n" +
                                "</match>";

        String list = title + info + startPlayersPitch + startPlayersBench + finishPlayers;
        String fileName = "match_" + match.getMatchUID() + ".xml";
        try {
            MyFileHandler.writeToTextFile(fileName,list);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}


