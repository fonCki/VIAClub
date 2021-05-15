
import java.io.IOException;
import java.time.LocalDate;

public class MatchListManager {
    public static MatchList getMatchListFromFile()  {
        MatchList matchList = new MatchList();
        try {
            matchList = (MatchList) MyFileHandler.readFromBinaryFile("Match-List.via");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
/*
        finally {
            Match matchOne = new Match("Boca", LocalDate.now(), "Horsens", "League");
            Match matchTwo = new Match("River", LocalDate.now(), "Berlin", "Cup");
            Match matchThree = new Match("Lanus", LocalDate.now(), "Moscow", "League");
            Match matchFour = new Match("Arsenal", LocalDate.now(), "Buenos Aires", "Friendly");
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
            MyFileHandler.writeToBinaryFile("Match-List.via", matchList);
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

}
