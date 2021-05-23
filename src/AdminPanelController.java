import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import utils.AlertControl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;

import static java.time.temporal.ChronoUnit.DAYS;

public class AdminPanelController {

    @FXML private AnchorPane mainAnchorPane; // The root pane

    /////THREE MAIN PANELS///////////////////////////////////////
    @FXML private AnchorPane playersPane;
    @FXML private AnchorPane matchesPane;
    @FXML private AnchorPane homePane;
    /////////////////////////////////////////////////////////////

    ////////ADMIN PANE OBJECTS///////////////////////////////////
    @FXML private JFXButton homeButtonAdmin;
    @FXML private JFXButton playersButtonAdmin;
    @FXML private JFXButton matchesButtonAdmin;
    @FXML private HBox searchBox;
    ////////////////////////////////////////////////////////////

    ////////HOME PANE OBJECTS///////////////////////////////////
    @FXML private Label today;
    @FXML private Label regards;
    @FXML private Label daysNextMatch;
    @FXML private VBox daysCard;
    @FXML private Label playersSystem;
    @FXML private Label daysWithoutRest;
    @FXML private Label playerNeedsRest;
    @FXML private VBox restingCard;
    @FXML private VBox neverPlayedCard;
    @FXML private Label neverPlayedName;
    @FXML private Text othersNeverPlayed;
    @FXML private Label playersNeverPlayed;
    @FXML private VBox matchPlayed;
    @FXML private VBox upcomingMatches;
    @FXML private Label totalMatchesPlayed;
    @FXML private Label upcomingMatchesNumber;
    @FXML private Label kindOfMatchAvg;
    @FXML private VBox kindOfMatchPanel;
    @FXML private Label kindOfMatch;
    @FXML private Label totalPlayers;
    @FXML private VBox totalPlayersCard;
    /////////////////////////////////////////////////////////////

    ////////MATCH PANE OBJECTS///////////////////////////////////
    @FXML private TableView<Match> matchTable;
    @FXML private TableColumn<Match, LocalDate> matchDate;
    @FXML private TableColumn<Match, String> opponent;
    @FXML private TableColumn<Match, String> place;
    @FXML private TableColumn<Match, String> kind;

    @FXML private JFXButton addMatch;
    @FXML private JFXButton deleteMatch;
    @FXML private JFXButton editMatch;
    @FXML private JFXButton viewMatch;
    ////////////////////////////////////////////////////////////

    ////// PLAYERS TAB OBJECTS//////////////////////////////////
    @FXML private TableView<Player> playersTable;
    @FXML private TableColumn<Player, String> name;
    @FXML private TableColumn<Player, String> lastName;
    @FXML private TableColumn<Player, Integer> number;
    @FXML private TableColumn<Player, String> position;
    @FXML private TableColumn<Player, String> status;
    @FXML private JFXButton editPlayer;
    @FXML private JFXButton deletePlayer;
    /////////////////////////////////////////////////////////////

    ////// MENU OBJECTS//////////////////////////////////
    @FXML private MenuItem save;
    @FXML private MenuItem quit;
    @FXML private Menu exportSelected;

    /////////////////////////////////////////////////////////////

    private PlayerList playerList;
    private MatchList matchList;
    private boolean changesMade = false;



    /////////////////////////INITIALIZE//////////////////////////////////////

    private void changesMade() {
        changesMade = true;
        save.setDisable(!changesMade);
    }

    private void initMenu() {
        save.setDisable(!changesMade);
        exportSelected.setDisable(true);
    }

    public void initialize(){
        searchBox.setVisible(false); // DELETE BOX, OR MAKE IT WORK!!!!
        this.playerList = PlayerListManager.getPlayerListFromFile();
        this.matchList = MatchListManager.getMatchListFromFile(); // Get the data from the file
        homePane.setVisible(true);
        startHomePane();
        matchesPane.setVisible(false);
        playersPane.setVisible(false);
        initMenu();
    }

    //First Options available

    public void handleEvent(ActionEvent e) {
        if (e.getSource() == homeButtonAdmin) {
            homePane.setVisible(true);
            matchesPane.setVisible(false);
            playersPane.setVisible(false);
            startHomePane();
        } else if (e.getSource() == matchesButtonAdmin) {
            homePane.setVisible(false);
            matchesPane.setVisible(true);
            playersPane.setVisible(false);
            startMatchPane();
        } else if (e.getSource() == playersButtonAdmin) {
            homePane.setVisible(false);
            matchesPane.setVisible(false);
            playersPane.setVisible(true);
            startPlayersPane();
        }
    }




    /////////////////////////////////////////////MATCH FUNCTIONS/////////////////////////////////////////////////
    public void startMatchPane(){

        ///MATCH TAB DATA INITIALIZE//

        //model.Match data columns
        matchDate.setCellValueFactory(new PropertyValueFactory<Match, LocalDate>("date"));
        opponent.setCellValueFactory(new PropertyValueFactory<Match, String>("opponent"));
        place.setCellValueFactory(new PropertyValueFactory<Match, String>("place"));
        kind.setCellValueFactory(new PropertyValueFactory<Match, String>("kind"));
        updateMatchTable();

        //model.Match buttons
        editMatch.disableProperty().bind(Bindings.isEmpty(matchTable.getSelectionModel().getSelectedItems())); // Button is only available if a match is selected!!
        deleteMatch.disableProperty().bind(Bindings.isEmpty(matchTable.getSelectionModel().getSelectedItems()));// Button is only available if a match is selected!!
        viewMatch.disableProperty().bind(Bindings.isEmpty(matchTable.getSelectionModel().getSelectedItems()));// Button is only available if a match is selected!!
        exportSelected.disableProperty().bind(Bindings.isEmpty(matchTable.getSelectionModel().getSelectedItems()));
        /////////////////////////////////////////////////////////////////////////

    }

    private void updateMatchTable() {
        matchTable.getItems().clear();
        matchTable.getItems().addAll(matchList.getMatchList());
    }

    private void viewMatch(Match match) {

        //Create the new Stage match
        Stage secondStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane root = null;
        try {
            root = fxmlLoader.load(getClass().getResource("listView.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        /////////////////////////////////

        /// SHARING DATA /////////////////
        ListViewController listView = fxmlLoader.getController();
        listView.transferData(match, playerList); //
        ////////////////////////////////

        // Start the new Stage
        secondStage.setTitle("model.Match");
        secondStage.setScene(new Scene(root, 500, 600));
        ///////////////////////

        //Change the modality of the fist main to disable
        Stage fistStage = (Stage) mainAnchorPane.getScene().getWindow(); // I get the first stage.
        secondStage.initOwner(fistStage);
        secondStage.initModality(Modality.WINDOW_MODAL);
        mainAnchorPane.setDisable(true);
        ///////////////////////////////////////////////////
        secondStage.showAndWait();

        //Once the second stage was closed
        mainAnchorPane.setDisable(false);


    }


    public void viewMatch(ActionEvent e) {
        Match match = matchTable.getSelectionModel().getSelectedItem();
        if (match != null) {
            viewMatch(match);
        }
    }

    public void actionMatch(Match match, String action) {
        changesMade();

        //Create the new Stage match
        Stage secondStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane root = null;
        try {
            root = fxmlLoader.load(getClass().getResource("match.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        /////////////////////////////////

        /// SHARING DATA /////////////////
        MatchController matchControl = fxmlLoader.getController();
        matchControl.transferData(match, matchList, action, playerList); // share the selection, the whole match list, the action and the player list
        ////////////////////////////////

        // Start the new Stage
        secondStage.setTitle("model.Match");
        secondStage.setScene(new Scene(root, 724, 600));
        ///////////////////////

        //Change the modality of the fist main to disable
        Stage fistStage = (Stage) mainAnchorPane.getScene().getWindow(); // I get the first stage.
        secondStage.initOwner(fistStage);
        secondStage.initModality(Modality.WINDOW_MODAL);
        mainAnchorPane.setDisable(true);
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
        startHomePane();
        mainAnchorPane.setDisable(false);

    }

    public void actionMatch(ActionEvent e) throws IOException {
        String action = (e.getSource() == editMatch) ? "edit" : "add"; // Recognise the action
        Match match = matchTable.getSelectionModel().getSelectedItem();
        actionMatch(match, action);
    }

    private void deleteMatch(Match match){
        if (AlertControl.confirmationBox("You are deleting the match selected do you want to continue?", "Delete")) {
            MatchListManager.deleteMatch(matchList, match);
            changesMade();
            updateMatchTable();
        }
    }
    public void deleteMatch(ActionEvent e) {
        deleteMatch(matchTable.getSelectionModel().getSelectedItem());
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////PLAYERS FUNCTIONS/////////////////////////////////////////////////

    public void startPlayersPane(){
        //PLAYERS TAB DATA INITIALIZE//

        //Players table
        //Players data columns
        if (this.name != null) { //CAMBIAR ESTO O VER PORQUE PASA!@!!
            name.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
            lastName.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
            number.setCellValueFactory(new PropertyValueFactory<Player, Integer>("number"));
            position.setCellValueFactory(new PropertyValueFactory<Player, String>("position"));
            status.setCellValueFactory(new PropertyValueFactory<Player, String>("status"));
            updatePlayersTable();
        }
        //Players buttons
        editPlayer.disableProperty().bind(Bindings.isEmpty(playersTable.getSelectionModel().getSelectedItems())); // Button is only available if a player is selected!!
        deletePlayer.disableProperty().bind(Bindings.isEmpty(playersTable.getSelectionModel().getSelectedItems())); // Button is only available if a player is selected!!

        /////////////////////////////////////////////////////////////////////////
    }

    private void updatePlayersTable() {
        playersTable.getItems().clear();
        for (Player player: playerList.getPlayersList()) {
            if (!(player.systemStatus.isDeleted()))
            playersTable.getItems().add(player);
        }
    }

    private void actionPlayer(Player player, String action) {
        changesMade();

        //Create the new Stage player
        Stage secondStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane root = null;
        try {
            root = fxmlLoader.load(getClass().getResource("player.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        /////////////////////////////////

        /// SHARING DATA (In this case always sharing data. if is nothing selected too, in that case I can get the playerList in the second stage)///
        PlayerControler playControl = fxmlLoader.getController();
        playControl.transferData(player, playerList, action); // share the selection, the whole player list, and the action
        //////////////////////////////////

        // Start the new Stage
        secondStage.setTitle("model.Player");
        secondStage.setScene(new Scene(root, 400, 600));
        ///////////////////////

        //Change the modality of the fist main to disable
        Stage fistStage = (Stage) mainAnchorPane.getScene().getWindow(); // I get the first stage.
        secondStage.initOwner(fistStage);
        secondStage.initModality(Modality.WINDOW_MODAL);
        mainAnchorPane.setDisable(true);
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
        startHomePane();
        mainAnchorPane.setDisable(false);

    }

    public void actionPlayer(ActionEvent e) throws IOException {
        String action = (e.getSource() == editPlayer) ? "edit" : "add"; // Recognise the action
        Player player = playersTable.getSelectionModel().getSelectedItem();
        actionPlayer(player, action);
    }


    public void deletePlayer(Player player) {
        if (AlertControl.confirmationBox("The player will be eliminated from the system " +
                "even if he/she is on the list for future or past matches. \n \n" +
                "Do you wish to continue?\n", "Delete")) {
            int playerIndex = playerList.getPosition(player);
            playerList.deletePlayer(player);
            matchList.updateBenchAndPitchArrays(playerIndex);
            changesMade();
            updatePlayersTable();
        }

    }

    public void deletePlayer(ActionEvent e) {
        deletePlayer(playersTable.getSelectionModel().getSelectedItem());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////HOME FUNCTIONS/////////////////////////////////////////////////

    public void startHomePane() {
        //////////////SET WELCOME MESSAGE//////////
        int hourNow = LocalDateTime.now().getHour();
        if (hourNow < 10) {
            regards.setText("God Morgen");
        } else if (hourNow >= 19) {
            regards.setText("Godaften");
        } else {
            regards.setText("God Dag");
        }
        //////////////SET DATE//////////
        today.setText(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)) + ", ");

        //////////////SET DAYS TO NEXT MATCH//////////
        if (matchList.nextMatch() == null) {
            daysCard.setManaged(false);
            daysCard.setVisible(false);
        } else {
            daysCard.setManaged(true);
            daysCard.setVisible(true);
            daysNextMatch.setText(DAYS.between(LocalDate.now(), matchList.nextMatch().getDate()) + "");
        }

        //////////////SET DAYS TO NEXT MATCH//////////
        playersSystem.setText(playerList.getNumberOfPlayers() + "");

        //////////////SET DAYS PLAYER WITHOUT REST//////////
        Player mostPlayed = PlayerListManager.mostPlayedPlayer(playerList, matchList);
        if (mostPlayed == null || mostPlayed.getTimesNoStop() == 0) {
            restingCard.setManaged(false);
            restingCard.setVisible(false);
        } else {
            restingCard.setManaged(true);
            restingCard.setVisible(true);
            playerNeedsRest.setText(mostPlayed.getName() + " " + mostPlayed.getLastName());
            daysWithoutRest.setText(mostPlayed.getTimesNoStop() + "");
        }

        //////////////SET PLAYERS DID NOT PLAY//////////
        HashSet<Integer> hashSetNeverPlayed = PlayerListManager.neverPlayed(playerList, matchList);
        if (hashSetNeverPlayed.isEmpty()) {
            neverPlayedCard.setVisible(false);
            neverPlayedCard.setManaged(false);
        } else {
            neverPlayedCard.setManaged(true);
            neverPlayedCard.setVisible(true);
            Player neverPlayedPlayer = playerList.getPlayerByPlayerId(hashSetNeverPlayed.iterator().next());
            neverPlayedName.setText(neverPlayedPlayer.getName() + " " + neverPlayedPlayer.getLastName());
            if (hashSetNeverPlayed.size() > 1) {
                othersNeverPlayed.setManaged(true);
                othersNeverPlayed.setVisible(true);
                playersNeverPlayed.setManaged(true);
                playersNeverPlayed.setVisible(true);
                playersNeverPlayed.setText(hashSetNeverPlayed.size() - 1 + "");
            } else {
                othersNeverPlayed.setManaged(false);
                othersNeverPlayed.setVisible(false);
                playersNeverPlayed.setManaged(false);
                playersNeverPlayed.setVisible(false);
            }
        }

        //////////////SET TOTAL MATCHES PLAYED//////////
        int matchPlayedInt = (matchList.pastMatches() == null) ? 0: matchList.pastMatches().size();
        if (matchPlayedInt == 0) {
            matchPlayed.setVisible(false);
            matchPlayed.setManaged(false);
        } else {
            matchPlayed.setVisible(true);
            matchPlayed.setManaged(true);
            totalMatchesPlayed.setText(matchPlayedInt +"");
        }

        //////////////SET TOTAL UPCOMING MATCHES//////////
        int upComingMatches = (matchList.getSize() - matchPlayedInt);
        if (upComingMatches == 0) {
            upcomingMatches.setVisible(false);
            upcomingMatches.setManaged(false);
        } else {
            upcomingMatches.setVisible(true);
            upcomingMatches.setManaged(true);
            upcomingMatchesNumber.setText(upComingMatches +"");
        }

        //////////////SET TOTAL AVERAGE KIND OF MATCH//////////
        if (matchList.getSize() == 0) {
            kindOfMatchPanel.setVisible(false);
            kindOfMatchPanel.setManaged(false);
        } else {
            kindOfMatchPanel.setVisible(true);
            kindOfMatchPanel.setManaged(true);
            int numberOfFriendlyMatches = matchList.numbersOfFriendly() * 100 / matchList.getSize();
            int numberOfLeagueMatches = matchList.numbersOfLeague()* 100 / matchList.getSize();
            int numberOfCupMatches = matchList.numbersOfCup()* 100 / matchList.getSize();

            if (numberOfFriendlyMatches >= numberOfCupMatches) {
                if (numberOfFriendlyMatches >= numberOfLeagueMatches) {
                    kindOfMatchAvg.setText(numberOfFriendlyMatches + "%");
                    kindOfMatch.setText("Friendly");
                } else {
                    kindOfMatchAvg.setText(numberOfLeagueMatches + "%");
                    kindOfMatch.setText("League");
                }
            } else {
                kindOfMatchAvg.setText(numberOfCupMatches + "%");
                kindOfMatch.setText("Cup");
            }
        }

        //////////////SET TOTAL PLAYERS IN THE SYSTEM//////////
        if (playerList.getNumberOfPlayers() == 0) {
            totalPlayersCard.setVisible(false);
            totalPlayersCard.setManaged(false);
        } else {
            totalPlayersCard.setVisible(true);
            totalPlayersCard.setManaged(true);
            totalPlayers.setText(playerList.getNumberOfPlayers() + "");
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////MENU FUNCTIONS////////////////////////////////////////////////////////

    private void save() {
        MatchListManager.writeInFile(matchList);
        PlayerListManager.writeInFile(playerList);
        changesMade = false;
        save.setDisable(!changesMade);
    }


    public void write(ActionEvent e) {
        save();
    }

    public void quit(ActionEvent e) {
        if (changesMade) {
            if(AlertControl.confirmationBox("Looks like you made some changes\n" +
                                            "\n Would you like to save them?", "Save")) {
                save();
            }
        }
        Stage stage = (Stage) mainAnchorPane.getScene().getWindow(); // CAMBIAR URGENTE!
        stage.close();
    }

    public void about(ActionEvent e) {
        AlertControl.infoBox("'Curiosity about life in all of its aspects, I think, is still the secret of great creative people.' \n\nLeo Burnett", "About Life");
    }

    public void exportXML(){
        Match match = matchTable.getSelectionModel().getSelectedItem();
        MatchListManager.createXML(match, playerList);
    }

    public void exportText(){
        Match match = matchTable.getSelectionModel().getSelectedItem();
        MatchListManager.createText(match, playerList);
    }

    public void exportAll(){
        if (AlertControl.confirmationBox("You are exporting the whole list of matches in text and web format.\n" +
                                                            "Probably, many files will be modified.\n" +
                                                            "I hope you know what are you doing \n\n" +
                                                            "Would you like to continue?", "Confirmation")) {
            for (int i =0; i < matchList.getSize(); i++) {
                MatchListManager.createXML(matchList.getMatchByIndex(i),playerList);
                MatchListManager.createText(matchList.getMatchByIndex(i),playerList);
        }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////GESTURE FUNCTIONS////////////////////////////////////////////////////////

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            Object selectedObject = ((TableView) keyEvent.getSource()).getSelectionModel().getSelectedItem();
            if (selectedObject != null) {
                if (selectedObject instanceof Match) {
                    deleteMatch((Match) selectedObject);
                } else if (selectedObject instanceof Player) {
                    deletePlayer((Player) selectedObject);
                }
            }
        }
    }

    public void mouseEvent(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            Object selectedObject = ((TableView) mouseEvent.getSource()).getSelectionModel().getSelectedItem();
            if (selectedObject != null) {
                if (selectedObject instanceof Player) {
                    actionPlayer((Player) selectedObject, "edit");
                } else if (selectedObject instanceof Match) {
                    viewMatch((Match) selectedObject);
                }
            }
        }
    }

}

