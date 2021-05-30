import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.Match;
import model.PlayerList;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class is the controller for the view stage and shows
 * information related to a match and in a view way.
 * @author @alfonsoridao
 * @version 3.1.
*/

public class ListViewController {
    @FXML private Label opponent;
    @FXML private Label date;
    @FXML private Label place;
    @FXML private Label kind;
    @FXML private TextArea starters;
    @FXML private TextArea bench;


    /**
     * This function creates a string with all the players in the set, sorted by the number of players.
     * The string includes the number, name, last name, and position of ID player in the set.
     * @param players a HashSet of uniques ID of players
     * @param playerList the full list of players.
     * @return an String with the information sorted by player Number
     */
    private String getString(HashSet<Integer> players, PlayerList playerList) {
        String starters = "";
        ArrayList<Integer> sortedList = playerList.sortedListPlayerByNumber(players);
        for (int playerIndex: sortedList) {
            starters += playerList.getPlayerByPlayerId(playerIndex).getNumber() + " - " +
                        playerList.getPlayerByPlayerId(playerIndex).getName() + " " +
                        playerList.getPlayerByPlayerId(playerIndex).getLastName() + " | " +
                        playerList.getPlayerByPlayerId(playerIndex).getPosition() + "\n";
        }
        return starters;
    }


    /**
     * This function gets the data transferred from the Match Stage and initialize the
     * fields in the view stage. Set all the labels and text areas.
     * @param match the match to be showed
     * @param playerList the full player list.
     */
    public void transferData(Match match, PlayerList playerList) {
        opponent.setText("Via CLUB vs "+ match.getOpponent());
        date.setText(match.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
        place.setText(match.getPlace());
        kind.setText(match.getKind());
        starters.setDisable(true);
        starters.setText(getString(match.getPlayersPitch(), playerList));
        bench.setDisable(true);
        bench.setText(getString(match.getPlayersBench(), playerList));
    }

}
