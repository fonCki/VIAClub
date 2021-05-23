package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;


public class MatchList implements Serializable {
    private ArrayList<Match> matchList;

    public MatchList() {
        matchList = new ArrayList<Match>();
    }

    public MatchList(ArrayList<Match> matchList) {
        this.matchList = matchList;
    }

    public void addMatch(Match match) {
        matchList.add(match);
    }

    public ArrayList<Match> getMatchList() {
        return matchList;
    }

    public void deleteMatch(Match match) {
        matchList.remove(match);
    }

    public int getIndex(Match match) {
        for (int i = 0; i < matchList.size(); i++) {
            if (matchList.get(i).equals(match))
                return i;
        }
        return -1;
    }

    public Match getMatchByIndex(int index) {
        return matchList.get(index);
    }

    public int getSize() {
        return matchList.size();
    }

    public boolean isEmpty() {
        return (matchList.size() < 1);
    }

    public void updateMatch(int index, Match match) {
        if (index < matchList.size()) {
            matchList.set(index, match);
        }
    }

    public void updateBenchAndPitchArrays(int playerIndex) {
        for (Match match : matchList) {
            match.getPlayersBench().remove(playerIndex);
            match.getPlayersPitch().remove(playerIndex);
        }
    }

    public MatchList copy() {
        return new MatchList(matchList);
    }

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



    public Match nextMatch() {
        MatchList sortedList = sortedMatchListByDate();
        for (int i = 0; i < sortedList.getSize(); i++) {
            if (sortedList.getMatchByIndex(i).getDate().isAfter(LocalDate.now())) {
                return sortedList.getMatchByIndex(i);
            }
        }
        return null;
    }

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

    public int numbersOfFriendly() {
        int c=0;
        for (Match match : matchList) {
            if (match.getKind().equals("Friendly")) c++;
        }
        return c;
    }

    public int numbersOfLeague() {
        int c=0;
        for (Match match : matchList) {
            if (match.getKind().equals("League")) c++;
        }
        return c;
    }
    public int numbersOfCup() {
        int c=0;
        for (Match match : matchList) {
            if (match.getKind().equals("Cup")) c++;
        }
        return c;
    }



}


