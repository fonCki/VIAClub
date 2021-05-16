import java.io.Serializable;
import java.util.ArrayList;

public class MatchList implements Serializable {
    private ArrayList<Match> matchList ;

    public MatchList() {
        matchList = new ArrayList<Match>();
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

    public Match getMatchByIndex(int index){
        return matchList.get(index);
    }

    public int getSize() {
        return matchList.size();
    }

    public void updateMatch(int index, Match match) {
        if (index < matchList.size()) {
            matchList.set(index, match);
        }
    }

    public void updateBenchAndPitchArrays(int playerIndex) {
        for (Match match: matchList){
            match.getPlayersBench().remove(playerIndex);
            match.getPlayersPitch().remove(playerIndex);
        }
    }
}
