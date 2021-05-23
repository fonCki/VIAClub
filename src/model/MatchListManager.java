package model;

import model.Match;
import model.MatchList;
import utils.MyFileHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class MatchListManager {
    public static MatchList getMatchListFromFile()  {
        MatchList matchList = new MatchList();
        try {
            matchList = (MatchList) MyFileHandler.readFromBinaryFile("model.Match-List.via");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
/*
        finally {
            model.Match matchOne = new model.Match("Boca", LocalDate.now(), "Horsens", "League");
            model.Match matchTwo = new model.Match("River", LocalDate.now(), "Berlin", "Cup");
            model.Match matchThree = new model.Match("Lanus", LocalDate.now(), "Moscow", "League");
            model.Match matchFour = new model.Match("Arsenal", LocalDate.now(), "Buenos Aires", "Friendly");
            matchList.addMatch(matchOne);
            matchList.addMatch(matchTwo);
            matchList.addMatch(matchThree);
            matchList.addMatch(matchFour);
        }
*/
        return matchList;
    }

    public static void writeInFile(MatchList matchList) {
        try {
            MyFileHandler.writeToBinaryFile("model.Match-List.via", matchList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
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


