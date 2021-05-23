import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;


public class MatchController {
    @FXML private AnchorPane mainAnchorPane;
    @FXML private Label matchID;
    @FXML private TextField opponent;
    @FXML private DatePicker date;
    @FXML private TextField place;
    @FXML private ToggleGroup kind;
    @FXML private RadioButton cup;
    @FXML private RadioButton league;
    @FXML private RadioButton friendly;
    @FXML private TableView<Player> playersPitch;
    @FXML private TableView<Player> playersBench;
    @FXML private TableColumn<Player, String> namePitch;
    @FXML private TableColumn<Player, String> lastNamePitch;
    @FXML private TableColumn<Player, String> positionPitch;
    @FXML private TableColumn<Player, Integer> numberPitch;
    @FXML private TableColumn<Player, String> nameBench;
    @FXML private TableColumn<Player, String> lastNameBench;
    @FXML private TableColumn<Player, String> positionBench;
    @FXML private TableColumn<Player, Integer> numberBench;
    @FXML private JFXButton save;
    @FXML private JFXButton cancel;
    @FXML private JFXButton addPlayers;

    private int uid;
    private MatchList matchList;
    private Match match, newMatch;
    private PlayerList playerList;

    public void initialize() {
       // this.playerList = PlayerListManager.getPlayerListFromFile();
        //Inicio las columnas)
        if (numberPitch != null) { //Change This
            namePitch.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
            lastNamePitch.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
            positionPitch.setCellValueFactory(new PropertyValueFactory<Player, String>("position"));
            numberPitch.setCellValueFactory(new PropertyValueFactory<Player, Integer>("number"));
            playersPitch.getItems().clear();
            playersBench.getItems().clear();
        } else {
            namePitch = new TableColumn<>();
        }

        if (numberBench != null) { // CVAMBIARRR
            nameBench.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
            lastNameBench.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
            positionBench.setCellValueFactory(new PropertyValueFactory<Player, String>("position"));
            numberBench.setCellValueFactory(new PropertyValueFactory<Player, Integer>("number"));
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
                newMatch.setMatchUID(match.getMatchUID()); // If is an update
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
            includePlayersController.transferData(match, playerList, matchList); // Comparto

            thirdStage.setTitle("Players Control");
            thirdStage.setScene(new Scene(root, 1024, 600));

            //Change the modality of the fist main to disable
            Stage  secondStage= (Stage) mainAnchorPane.getScene().getWindow(); // I get the first stage.
            thirdStage.initOwner(secondStage);
            thirdStage.initModality(Modality.WINDOW_MODAL);
            mainAnchorPane.setDisable(true);
            ///////////////////////////////////////////////////

            thirdStage.showAndWait();
            mainAnchorPane.setDisable(false);
            updateTables();


        }

    }


}



