import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import model.*;
import utils.AlertControl;

import java.util.HashSet;

/**
 * This class is the controller for the includePlayers stage and
 * allows to move to add or delete players to any match.
 * @author @alfonsoridao
 * @version 3.1.
 */

public class IncludePlayersController {
    @FXML private TableView tableFullPlayers;
    @FXML private TableView tablePlayersPitch;
    @FXML private TableView tablePlayersBench;

    @FXML private TableColumn<Player, String> nameAll;
    @FXML private TableColumn<Player, String> lastNameAll;
    @FXML private TableColumn<Player, String> positionAll;
    @FXML private TableColumn<Player, Integer> playedAll;
    @FXML private TableColumn<Player, String> statusAll;

    @FXML private TableColumn<Player, String> namePitch;
    @FXML private TableColumn<Player, String> lastNamePitch;
    @FXML private TableColumn<Player, String> positionPitch;
    @FXML private TableColumn<Player, Integer> playedPitch;
    @FXML private TableColumn<Player, String> statusPitch;

    @FXML private TableColumn<Player, String> nameBench;
    @FXML private TableColumn<Player, String> lastNameBench;
    @FXML private TableColumn<Player, String> positionBench;
    @FXML private TableColumn<Player, Integer> playedBench;
    @FXML private TableColumn<Player, String> statusBench;

    @FXML private JFXButton addPitch;
    @FXML private JFXButton removePitch;
    @FXML private JFXButton addBench;
    @FXML private JFXButton removeBench;

    @FXML private JFXButton done;

    private Match match;
    private PlayerList playerList;
    private MatchList matchList;

    private HashSet<Integer> availablePlayers = new HashSet<Integer>();
    private HashSet<Integer> pitchPlayers;
    private HashSet<Integer> benchPlayers;


    /**
     * Updates the three tables. The players on the pitch,
     * the players on the bench, and the available players to be selected.
     */
    public void updateTables() {

        tableFullPlayers.getItems().clear();
        for (int playerId : availablePlayers) {
            tableFullPlayers.getItems().add(playerList.getPlayerByPlayerId(playerId));
        }

        tablePlayersPitch.getItems().clear();
        if (pitchPlayers != null) {
            for (int playerId : pitchPlayers) {
                tablePlayersPitch.getItems().add(playerList.getPlayerByPlayerId(playerId));
            }
        }

        if (benchPlayers != null) {
            tablePlayersBench.getItems().clear();
            for (int playerId : benchPlayers) {
                tablePlayersBench.getItems().add(playerList.getPlayerByPlayerId(playerId));
            }
        }


    }


    /**
     * Initialize the three tables. And the buttons on the stage.
     */
    public void initialize() {
        if (nameAll != null) {
            nameAll.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
            lastNameAll.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
            positionAll.setCellValueFactory(new PropertyValueFactory<Player, String>("position"));
            playedAll.setCellValueFactory(new PropertyValueFactory<Player, Integer>("timesNoStop"));
            statusAll.setCellValueFactory(new PropertyValueFactory<Player, String>("status"));
            tableFullPlayers.getItems().clear();
        }

        if (namePitch != null) {
            namePitch.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
            lastNamePitch.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
            positionPitch.setCellValueFactory(new PropertyValueFactory<Player, String>("position"));
            playedPitch.setCellValueFactory(new PropertyValueFactory<Player, Integer>("timesNoStop"));
            statusPitch.setCellValueFactory(new PropertyValueFactory<Player, String>("status"));
            tablePlayersPitch.getItems().clear();
        }

        if (nameBench != null) {
            nameBench.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
            lastNameBench.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
            positionBench.setCellValueFactory(new PropertyValueFactory<Player, String>("position"));
            playedBench.setCellValueFactory(new PropertyValueFactory<Player, Integer>("timesNoStop"));
            statusBench.setCellValueFactory(new PropertyValueFactory<Player, String>("status"));
            tablePlayersBench.getItems().clear();
        }

        addPitch.disableProperty().bind(Bindings.isEmpty(tableFullPlayers.getSelectionModel().getSelectedItems())); //
        removePitch.disableProperty().bind(Bindings.isEmpty(tablePlayersPitch.getSelectionModel().getSelectedItems())); //
        addBench.disableProperty().bind(Bindings.isEmpty(tableFullPlayers.getSelectionModel().getSelectedItems())); //
        removeBench.disableProperty().bind(Bindings.isEmpty(tablePlayersBench.getSelectionModel().getSelectedItems())); //

    }

    /**
     * Initialize the three differents array. one with the total of players available.
     * And the other two with the players on the pitch and the players on the bench.
     */
    private void initArrayPlayers() {
        if (match.getPlayersPitch() == null) {
            pitchPlayers = new HashSet<Integer>(match.getMaxPlayersPitch());
        } else {
            pitchPlayers = match.getPlayersPitch();
        }

        if (match.getPlayersBench() == null) {
            benchPlayers = new HashSet<Integer>(match.getMaxPlayersBench());
        } else {
            benchPlayers = match.getPlayersBench();
        }

        ///Different options depends the kind of match///
        if (match.getKind().equals("Cup") || match.getKind().equals("League")) {
            for (int i = 0; i < playerList.getSize(); i++) {
                if (playerList.getPlayerByPlayerId(i).systemStatus.isAvailable()) {
                    availablePlayers.add(i);
                }
            }
            } else {
            for (int i = 0; i < playerList.getSize(); i++) {
                if (!(playerList.getPlayerByPlayerId(i).systemStatus.isDeleted())) {
                    availablePlayers.add(i);
                }
            }
        }
        availablePlayers.removeAll(pitchPlayers);
        availablePlayers.removeAll(benchPlayers);

        updateTables();

    }

    /**
     * Received the data transferred from the Match Stage.
     * @param match the match.
     * @param playerList the list of players.
     * @param matchList the list of matches.
     */
    public void transferData(Match match, PlayerList playerList, MatchList matchList) {
        this.match = match;
        this.matchList = matchList;
        this.playerList = playerList;
        playerList.updateTimeNoStop(match, matchList);
        initArrayPlayers();
    }

    /**
     * Action event to be shooted after pressing the button to move from the whole list to the pitch list.
     * Swipe the players in the sets.
     * @param e the button pressed.
     */
    public void AllMoveToPitch(ActionEvent e){
        if (match.getPlayersPitch().size() < match.getMaxPlayersPitch()) {
            Player player = (Player) tableFullPlayers.getSelectionModel().getSelectedItem();
            availablePlayers.remove(player.getPlayerId());
            pitchPlayers.add(player.getPlayerId());
            updateTables();
        } else {
            AlertControl.infoBox("you have reached the limit of player", "Max Reach");
        }
    }

    /**
     * Action event to be shooted after pressing the button to move from the whole list to the bench list.
     * Swipe the players in the sets.
     * @param e the button pressed.
     */
    public void AllMoveToBench(ActionEvent e){
        if (match.getPlayersBench().size() < match.getMaxPlayersBench()) {
            Player player = (Player) tableFullPlayers.getSelectionModel().getSelectedItem();
            availablePlayers.remove(player.getPlayerId());
            benchPlayers.add(player.getPlayerId());
            updateTables();
        } else {
            AlertControl.infoBox("you have reached the limit of player", "Max Reach");
        }

    }

    /**
     * Action event to be shooted after pressing the button to move from the pitch list to the whole list.
     * Swipe the players in the sets.
     * @param e the button pressed.
     */
    public void PitchMoveToAll(ActionEvent e){
        Player player = (Player) tablePlayersPitch.getSelectionModel().getSelectedItem();
        pitchPlayers.remove(player.getPlayerId());
        availablePlayers.add(player.getPlayerId());
        updateTables();
    }

    /**
     * Action event to be shooted after pressing the button to move from the bench list to the whole list.
     * Swipe the players in the sets.
     * @param e the button pressed.
     */
    public void BenchMoveToAll(ActionEvent e){
        Player player = (Player) tablePlayersBench.getSelectionModel().getSelectedItem();
        benchPlayers.remove(player.getPlayerId());
        availablePlayers.add(player.getPlayerId());
        updateTables();
    }

    /**
     * close the stage.
     * @param e the button pressed.
     */
    public void done(ActionEvent e) {
        Stage stage = (Stage) done.getScene().getWindow();
        stage.close();
    }

}

