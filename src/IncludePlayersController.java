import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

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


    public void initialize() {
        if (nameAll != null) {
            nameAll.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
            lastNameAll.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
            positionAll.setCellValueFactory(new PropertyValueFactory<Player, String>("position"));
            playedAll.setCellValueFactory(new PropertyValueFactory<Player, Integer>("timesNoStop"));
            statusAll.setCellValueFactory(new PropertyValueFactory<Player, String>("status"));
            tableFullPlayers.getItems().clear();
        } else {
            nameAll = new TableColumn<>();
        }

        if (namePitch != null) {
            namePitch.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
            lastNamePitch.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
            positionPitch.setCellValueFactory(new PropertyValueFactory<Player, String>("position"));
            playedPitch.setCellValueFactory(new PropertyValueFactory<Player, Integer>("timesNoStop"));
            statusPitch.setCellValueFactory(new PropertyValueFactory<Player, String>("status"));
            tablePlayersPitch.getItems().clear();
        } else {
            namePitch = new TableColumn<>();
        }

        if (nameBench != null) {
            nameBench.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
            lastNameBench.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
            positionBench.setCellValueFactory(new PropertyValueFactory<Player, String>("position"));
            playedBench.setCellValueFactory(new PropertyValueFactory<Player, Integer>("timesNoStop"));
            statusBench.setCellValueFactory(new PropertyValueFactory<Player, String>("status"));
            tablePlayersBench.getItems().clear();
        } else {
            nameBench = new TableColumn<>();
        }
        addPitch.disableProperty().bind(Bindings.isEmpty(tableFullPlayers.getSelectionModel().getSelectedItems())); //
        removePitch.disableProperty().bind(Bindings.isEmpty(tablePlayersPitch.getSelectionModel().getSelectedItems())); //
        addBench.disableProperty().bind(Bindings.isEmpty(tableFullPlayers.getSelectionModel().getSelectedItems())); //
        removeBench.disableProperty().bind(Bindings.isEmpty(tablePlayersBench.getSelectionModel().getSelectedItems())); //

    }

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

        ///Differents options depends the kind of match///
        System.out.println(match.getKind() + "ESTE ESSSSS");
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

    public void transferData(Match match, PlayerList playerList, MatchList matchList) {
        this.match = match;
        this.matchList = matchList;
        this.playerList = playerList;
        PlayerListManager.updateTimeNoStop(match, playerList, matchList);
        initArrayPlayers();
    }

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

    public void PitchMoveToAll(ActionEvent e){
        Player player = (Player) tablePlayersPitch.getSelectionModel().getSelectedItem();
        pitchPlayers.remove(player.getPlayerId());
        availablePlayers.add(player.getPlayerId());
        updateTables();

    }

    public void BenchMoveToAll(ActionEvent e){
        Player player = (Player) tablePlayersBench.getSelectionModel().getSelectedItem();
        benchPlayers.remove(player.getPlayerId());
        availablePlayers.add(player.getPlayerId());
        updateTables();

    }

    public void done(ActionEvent e) {
        Stage stage = (Stage) done.getScene().getWindow();
        stage.close();
    }

}

