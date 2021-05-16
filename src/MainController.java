import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.time.LocalDate;

public class MainController {

    @FXML private VBox mainVBoxPane;

    ////// MATCH TAB OBJECTS
    @FXML private TableView<Match> matchTable;
    @FXML private TableColumn<Match, LocalDate> matchDate;
    @FXML private TableColumn<Match, String> opponent;
    @FXML private TableColumn<Match, String> place;
    @FXML private TableColumn<Match, String> kind;
    @FXML private Button editMatch;
    @FXML private Button addMatch;
    @FXML private Button deleteMatch;

    @FXML private Button reloadMatch; //internal use DELETE AT THE END
    @FXML private Button writeMatch; // internal use DELETE AT THE END

    private MatchList matchList;

    //////////////////////////////

    ////// PLAYERS TAB OBJECTS
    @FXML private TableView<Player> playersTable;
    @FXML private TableColumn<Player, String> name;
    @FXML private TableColumn<Player, String> lastName;
    @FXML private TableColumn<Player, Integer> number;
    @FXML private TableColumn<Player, String> position;
    @FXML private TableColumn<Player, String> status;
    @FXML private Button editPlayer;
    @FXML private Button deletePlayer;
    @FXML private Button addPlayer;


    @FXML private Button reloadPlayers; //internal use DELETE AT THE END
    @FXML private Button writePlayers; // internal use DELETE AT THE END

    private PlayerList playerList;

    //////////////////////////////



    public void initialize() {
        ///MATCH TAB DATA INITIALIZE//
        // Match table
        matchList = MatchListManager.getMatchListFromFile(); // Get the data from the file

        //Match data columns
        matchDate.setCellValueFactory(new PropertyValueFactory<Match, LocalDate>("date"));
        opponent.setCellValueFactory(new PropertyValueFactory<Match, String>("opponent"));
        place.setCellValueFactory(new PropertyValueFactory<Match, String>("place"));
        kind.setCellValueFactory(new PropertyValueFactory<Match, String>("kind"));
        updateMatchTable();

        //Match buttons
        editMatch.disableProperty().bind(Bindings.isEmpty(matchTable.getSelectionModel().getSelectedItems())); // Button is only available if a match is selected!!
        deleteMatch.disableProperty().bind(Bindings.isEmpty(matchTable.getSelectionModel().getSelectedItems()));// Button is only available if a match is selected!!
        /////////////////////////////////////////////////////////////////////////

        //PLAYERS TAB DATA INITIALIZE//
        //Players table
        playerList = PlayerListManager.getPlayerListFromFile();

        //Players data columns
        name.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
        number.setCellValueFactory(new PropertyValueFactory<Player, Integer>("number"));
        position.setCellValueFactory(new PropertyValueFactory<Player, String>("position"));
        status.setCellValueFactory(new PropertyValueFactory<Player, String>("status"));
        updatePlayersTable();

        //Players butttons
        editPlayer.disableProperty().bind(Bindings.isEmpty(playersTable.getSelectionModel().getSelectedItems())); // Button is only available if a player is selected!!
        deletePlayer.disableProperty().bind(Bindings.isEmpty(playersTable.getSelectionModel().getSelectedItems())); // Button is only available if a player is selected!!

        /////////////////////////////////////////////////////////////////////////
    }



/////////////////////////////////////////////MATCH FUNCTIONS/////////////////////////////////////////////////


    private void updateMatchTable() {
        for (int i=0; i < matchList.getSize(); i++) // Para eliminar
            System.out.println(matchList.getMatchByIndex(i)); // para eliminar
        matchTable.getItems().clear();
        matchTable.getItems().addAll(matchList.getMatchList());
    }

    public void actionMatch(ActionEvent e) throws IOException {
        String action = (e.getSource() == editMatch) ? "edit" : "add"; // Recognise the action

        //Create the new Stage match
        Stage secondStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane root = fxmlLoader.load(getClass().getResource("match.fxml").openStream());
        /////////////////////////////////

        /// SHARING DATA (Just if the selection was edit, otherwise no data to share ///
        if (e.getSource() == editMatch ||  e.getSource() == addMatch) {
            MatchController matchControl = fxmlLoader.getController();
            matchControl.transferData(matchTable.getSelectionModel().getSelectedItem(), matchList, action, playerList); // share the selection, the whole match list, the action and the player list
        }
        //////////////////////

        // Start the new Stage
        secondStage.setTitle("Match");
        secondStage.setScene(new Scene(root, 820, 620));
        ///////////////////////

        //Change the modality of the fist main to disable
        Stage fistStage = (Stage) mainVBoxPane.getScene().getWindow(); // I get the first stage.
        secondStage.initOwner(fistStage);
        secondStage.initModality(Modality.WINDOW_MODAL);
        mainVBoxPane.setDisable(true);
        ///////////////////////////////////////////////////


        ////When someone close the window
        /*
            secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    anchorPane.setDisable(false);
                }
            });
        */
        secondStage.showAndWait();

        //Once the second stage was closed
        updateMatchTable();
        mainVBoxPane.setDisable(false);



    }

    public void deleteMatch(ActionEvent e) {
        if (AlertControl.confirmationBox("You are deleting the match selected do you want to continue?", "Delete")) {
            MatchListManager.deleteMatch(matchList, matchTable.getSelectionModel().getSelectedItem());
            updateMatchTable();
        }
    }

//////////JUST TEST! TO BE DELETED
    public void reloadMatch(ActionEvent e) {
        updateMatchTable();
    }

    public void writeMatch(ActionEvent e) {
        MatchListManager.writeInFile(matchList);
    }

    public void handler(ActionEvent e) {

    }
///////////////////////////////////////


//////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////PLAYERS FUNCTIONS/////////////////////////////////////////////////

    private void updatePlayersTable() {
        playersTable.getItems().clear();

        for (Player player: playerList.getPlayersList()) {
         //   if (!(player.systemStatus.isDeleted())) //Please activate at the end
                playersTable.getItems().add(player);
            System.out.println(player);
        }
    }

    public void actionPlayer(ActionEvent e) throws IOException {

        String action = (e.getSource() == editPlayer) ? "edit" : "add"; // Recognise the action

        //Create the new Stage player
        Stage secondStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane root = fxmlLoader.load(getClass().getResource("player.fxml").openStream());
        /////////////////////////////////

        /// SHARING DATA (In this case always sharing data. if is nothing selected too, in that case I can get the playerList in the second stage)///
        PlayerControler playControl = fxmlLoader.getController();
        playControl.transferData(playersTable.getSelectionModel().getSelectedItem(), playerList, action); // share the selection, the whole player list, and the action
        //////////////////////////////////

        // Start the new Stage
        secondStage.setTitle("Player");
        secondStage.setScene(new Scene(root, 450, 655));
        ///////////////////////

        //Change the modality of the fist main to disable
        Stage fistStage = (Stage) mainVBoxPane.getScene().getWindow(); // I get the first stage.
        secondStage.initOwner(fistStage);
        secondStage.initModality(Modality.WINDOW_MODAL);
        mainVBoxPane.setDisable(true);
        ///////////////////////////////////////////////////


        ////When someone close the window
        /*
            secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    anchorPane.setDisable(false);
                }
            });
        */
        secondStage.showAndWait();

        //Once the second stage was closed
        updatePlayersTable();
        mainVBoxPane.setDisable(false);

    }

    public void deletePlayer(ActionEvent e) {
        if (AlertControl.confirmationBox("The player will be eliminated from the system " +
                "even if he/she is on the list for future or past matches. \n \n" +
                "Do you wish to continue?\n", "Delete")) {
            int playerIndex = playerList.getPosition(playersTable.getSelectionModel().getSelectedItem());
            playerList.deletePlayer(playersTable.getSelectionModel().getSelectedItem());
            matchList.updateBenchAndPitchArrays(playerIndex);
            updatePlayersTable();
        }
    }

///////////////JUST TEST! TO BE DELETED/////////////////////
    public void reloadPlayers(ActionEvent e) {
        updatePlayersTable();
    }

    public void writePlayers(ActionEvent e) {
        PlayerListManager.writeInFile(playerList);
    }
/////////////////////////////////////////////////

}
