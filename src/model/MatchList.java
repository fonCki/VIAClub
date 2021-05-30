package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains a list of matches.
 * @author @alfonsoridao
 * @version 3.1
 */

public class MatchList implements Serializable {

    private ArrayList<Match> matchList;

    /**
     * Non-argument constructor initializing the matchList.
     */
    public MatchList() {
        matchList = new ArrayList<Match>();
    }

    /**
     * One argument constructor initializing the matchList.
     * @param matchList the match list.
     */
    public MatchList(ArrayList<Match> matchList) {
        this.matchList = matchList;
    }

    /**
     * Adds a match to the list.
     * @param match the match to be added.
     */
    public void addMatch(Match match) {
        this.matchList.add(match);
    }

    /**
     * Getting the whole list of matches
     * @return an array with all the matches.
     */
    public ArrayList<Match> getMatchList() {
        return matchList;
    }

    /**
     * Subtract a match from the list.
     * @param match the match to be eliminated.
     */
    public void deleteMatch(Match match) {
        matchList.remove(match);
    }

    /**
     * Getting the position of the match inside the list.
     * returns -1 if the match was not founded.
     * @param match the match to be founded.
     * @return an integer with the position.
     */
    public int getIndex(Match match) {
        for (int i = 0; i < matchList.size(); i++) {
            if (matchList.get(i).equals(match))
                return i;
        }
        return -1;
    }

    /**
     * Returns a match located in the position given as a parameter.
     * @param index the position to make the search.
     * @return a Match.
     */
    public Match getMatchByIndex(int index) {
        return matchList.get(index);
    }

    /**
     * Returns the numbers of match includes in the list.
     * @return an integer.
     */
    public int getSize() {
        return matchList.size();
    }

    /**
     * Returns the position where can be add a new match.
     * @return an integer with the last position.
     */
    public int getNewPosition() {
        return matchList.size();
    }

    /**
     * Returns true if the list is empty, otherwise returns false.
     * @return a boolean response.
     */
    public boolean isEmpty() {
        return (matchList.size() < 1);
    }

    /**
     * Replace the match located in the position passes as a parameter,
     * for the new match passed as a parameter.
     * is the Index is bigger than the size, then add the match at the end.
     * @param index the location to be replaced.
     * @param match the new match.
     */
    public void updateMatch(int index, Match match) {
        if (index < matchList.size()) {
            matchList.set(index, match);
        } else {
            matchList.add(match);
        }
    }

    /**
     *  Search in all the matches in the list.
     *  For each match look in the player's sets, searching for a specific playerID.
     *  If it finds it, it is deleted.
     * @param playerID the unique PlayerID
     */
    public void updateBenchAndPitchArrays(int playerID) {
        for (Match match : matchList) {
            match.getPlayersBench().remove(playerID);
            match.getPlayersPitch().remove(playerID);
        }
    }

    /**
     * returns a copy of a list of matches, sorted by date.
     * @return a MatchList object with a copy.
     */
    public MatchList sortedMatchListByDate() {
        ArrayList<Match> tempSortedMatchList = new ArrayList(matchList);
        Collections.sort(tempSortedMatchList, (M1, M2) -> {
            if (M1.getDate().isBefore(M2.getDate())) {
                return -1;
            } else {
                return 1;
            }
        });
        return new MatchList(tempSortedMatchList);
    }


    /**
     * Returns the next future match to be played. if there is no Match, return null.
     * @return te match object.
     */
    public Match nextMatch() {
        MatchList sortedList = sortedMatchListByDate();
        for (int i = 0; i < sortedList.getSize(); i++) {
            if (sortedList.getMatchByIndex(i).getDate().isAfter(LocalDate.now())) {
                return sortedList.getMatchByIndex(i);
            }
        }
        return null;
    }

    /**
     * Returns an new ArrayList of matches, with all the past matches.
     * @return an Arraylist.
     */
    public ArrayList<Match> pastMatches() {
        ArrayList<Match> tempPastArrayList =  new ArrayList<>();
        for (Match match: sortedMatchListByDate().getMatchList()) {
            if (match.getDate().isBefore(LocalDate.now())) {
                tempPastArrayList.add(match);
            } else {
                break; // ARREGLAR ESTO!!! NO PODER UN BREAK ASI! ARMAR UN WHILE!!
            }
        }
        return tempPastArrayList;
    }

    /**
     * updateRelation is an initializer to check if the players' IDs
     * inside the bench and pitch sets are in the playerList.
     * If they are not founded, they are eliminated from the list.
     * @param playerList The players List.
     */
    public  void updateRelation(PlayerList playerList) {
        for (int i=0; i < getSize(); i++) {
            for (int playerIndex: getMatchByIndex(i).getPlayersPitch()) {
                if (playerList.getPlayerByPlayerId(playerIndex) == null) {
                    updateBenchAndPitchArrays(playerIndex);
                }
            }
        }
    }

    /**
     * This functions gives the number of "Friendly" matches in the list.
     * @return an integer with the numbers
     */
    public int numbersOfFriendly() {
        int c=0;
        for (Match match : matchList) {
            if (match.getKind().equals("Friendly")) c++;
        }
        return c;
    }

    /**
     * This functions gives the number of "League" matches in the list.
     * @return an integer with the numbers
     */
    public int numbersOfLeague() {
        int c=0;
        for (Match match : matchList) {
            if (match.getKind().equals("League")) c++;
        }
        return c;
    }

    /**
     * This functions gives the number of "Cup" matches in the list.
     * @return an integer with the numbers
     */
    public int numbersOfCup() {
        int c=0;
        for (Match match : matchList) {
            if (match.getKind().equals("Cup")) c++;
        }
        return c;
    }

}


