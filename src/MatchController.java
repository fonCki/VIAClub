import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;


public class MatchController {
    @FXML Label matchID;
    @FXML TextField opponent;
    @FXML DatePicker date;
    @FXML TextField place;
    @FXML ToggleGroup kind;
    @FXML RadioButton cup;
    @FXML RadioButton league;
    @FXML RadioButton friendly;
    @FXML TableView<Player> playersPitch;
    @FXML TableView<Player> playersBench;
    @FXML TableColumn<Player, String> namePitch;
    @FXML TableColumn<Player, String> lastNamePitch;
    @FXML TableColumn<Player, String> positionPitch;
    @FXML TableColumn<Player, String> statusPitch;
    @FXML TableColumn<Player, String> nameBench;
    @FXML TableColumn<Player, String> lastNameBench;
    @FXML TableColumn<Player, String> positionBench;
    @FXML TableColumn<Player, String> statusBench;
    @FXML Button save;
    @FXML Button cancel;
    @FXML Button addPlayers;

    private int uid;
    private MatchList matchList;
    private Match match, newMatch;
    private PlayerList playerList;

    public void initialize() {
       // this.playerList = PlayerListManager.getPlayerListFromFile();
        //Inicio las columnas)
        if (namePitch != null) {
            namePitch.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
            lastNamePitch.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
            statusPitch.setCellValueFactory(new PropertyValueFactory<Player, String>("status"));
            positionPitch.setCellValueFactory(new PropertyValueFactory<Player, String>("position"));
            playersPitch.getItems().clear();
            playersBench.getItems().clear();
        } else {
            namePitch = new TableColumn<>();
        }

        if (nameBench != null) {
            nameBench.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
            lastNameBench.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
            statusBench.setCellValueFactory(new PropertyValueFactory<Player, String>("status"));
            positionBench.setCellValueFactory(new PropertyValueFactory<Player, String>("position"));
            playersBench.getItems().clear();
            playersBench.getItems().clear();
        } else {
            nameBench = new TableColumn<>();
        }
    }

    public void updateTables() {
        playersPitch.getItems().clear();
        for (int playerId: match.getPlayersPitch()) {
            playersPitch.getItems().add(playerList.getPlayerByPlayerId(playerId));
        }

        playersBench.getItems().clear();
        for (int playerId: match.getPlayersBench()) {
            playersBench.getItems().add(playerList.getPlayerByPlayerId(playerId));
        }
    }

    public void transferData(Match match, MatchList matchList, String action, PlayerList playerList) {
        this.matchList = matchList;
        this.playerList = playerList;
        if (action.equals("add")) {
            uid = MatchListManager.getNewPosition(matchList); // Creo un nuevo ID
        } else if (action.equals("edit") && match != null) {
            this.match = match;
            uid = MatchListManager.getPosition(matchList, match);
            matchID.setText("UID: " + uid);
            opponent.setText(match.getOpponent());
            date.setValue(match.getDate());
            place.setText(match.getPlace());
            RadioButton option = (RadioButton) kind.getSelectedToggle();
            switch (match.getKind().toLowerCase()) {
                case "cup" : {
                    kind.selectToggle(cup);
                    break;
                }
                case "league" : {
                    kind.selectToggle(league);
                    break;
                }
                case "friendly" : {
                    kind.selectToggle(friendly);
                    break;
                }
            }
            updateTables();
        }
    }

    private void updatePlayersMatch(Match newMatch, Match oldMatch) {
        newMatch.setPlayersPitch(oldMatch.getPlayersPitch());
        if (oldMatch.getPlayersBench().size() > newMatch.getMaxPlayersBench()) {
            if (AlertControl.confirmationBox("You have more players on the bench than the new kind of match accept. " +
                    "If you continue, they will be reduced to fit into the new specifications. \n \n" +
                    "Do you wish to continue?\n", "Kind conflict")) {
                ArrayList<Integer> tempBenchArray = new ArrayList<Integer>(newMatch.getMaxPlayersBench());
                HashSet<Integer> tempBenchHash = new HashSet<Integer>(newMatch.getMaxPlayersBench());
                for (int PlayerById : oldMatch.getPlayersBench())
                    tempBenchArray.add(PlayerById);
                for (int i=0; i < newMatch.getMaxPlayersBench(); i++) {
                    tempBenchHash.add(tempBenchArray.get(i));
                }
                newMatch.setPlayersBench(tempBenchHash);
            } else { // else, cancel Alert Control.
                newMatch.setPlayersBench(oldMatch.getPlayersBench());
                newMatch.setKind("Friendly"); // Set the kind to friendly to avoid conflicts
            }
        } else {
            newMatch.setPlayersBench(oldMatch.getPlayersBench());
        }
    }

    private boolean createOrEditMatch() {
        if (opponent.getText() == "") {
            AlertControl.warningBox("You must insert an opponent", "Error");
        } else if (date.getValue() == null) {
            AlertControl.warningBox("You must insert a date", "Error");
        } else { // After control blank fields, the match is created
            newMatch = new Match(opponent.getText(),
                                 date.getValue(),
                                (place.getText() == "" ? "Via Stadium" : place.getText()),
                                ((RadioButton) kind.getSelectedToggle()).getText());
            if (match != null) { // Copy the players if the action is an update
                updatePlayersMatch(newMatch, match);
            }
            MatchListManager.saveMatch(matchList, newMatch, uid);
            match = newMatch;
            return true;
        }
        return false;
    }

    public void cancel(ActionEvent e) {
        if (e.getSource() == cancel) {
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();
        }
    }

    public void save(ActionEvent e) throws IOException {
        if (createOrEditMatch()) {
            Stage stage = (Stage) save.getScene().getWindow();
            stage.close();
        }
    }

    public void addPlayers(ActionEvent e) throws IOException {
        if (createOrEditMatch()) {

            //Starting the third stage//
            Stage thirdStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane root = fxmlLoader.load(getClass().getResource("includePlayers.fxml").openStream());

            //Sharing data!
            IncludePlayersController includePlayersController = fxmlLoader.getController();
            includePlayersController.transferData(match, playerList); // Comparto



            thirdStage.setTitle("Players");
            thirdStage.setScene(new Scene(root, 850, 855));

            thirdStage.showAndWait();

            updateTables();

        }

    }


}



