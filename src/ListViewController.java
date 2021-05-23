import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.Match;
import model.MatchListManager;
import model.PlayerList;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashSet;


public class ListViewController {
    @FXML private Label opponent;
    @FXML private Label date;
    @FXML private Label place;
    @FXML private Label kind;

    @FXML private TextArea starters;
    @FXML private TextArea bench;


    private String getString(HashSet<Integer> players, PlayerList playerList) {
        String starters = "";
        ArrayList<Integer> sortedList = MatchListManager.sortedMatchListByNumber(players, playerList);
        for (int playerIndex: sortedList) {
            starters += playerList.getPlayerByPlayerId(playerIndex).getNumber() + " - " +
                        playerList.getPlayerByPlayerId(playerIndex).getName() + " " +
                        playerList.getPlayerByPlayerId(playerIndex).getLastName() + " | " +
                        playerList.getPlayerByPlayerId(playerIndex).getPosition() + "\n";
        }
        return starters;
    }



    public void transferData(Match match, PlayerList playerList) {
        opponent.setText("Via CLUB vs "+match.getOpponent());
        date.setText(match.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
        place.setText(match.getPlace());
        kind.setText(match.getKind());
        starters.setDisable(true);
        starters.setText(getString(match.getPlayersPitch(), playerList));
        bench.setDisable(true);
        bench.setText(getString(match.getPlayersBench(), playerList));
    }

}
