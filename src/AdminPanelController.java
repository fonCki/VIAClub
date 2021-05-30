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

/**
 * This class is the controller of the Main Stage of the system.
 * includes the control to the left Admin Panel and the three panels (Home, Matches, and Players)
 * @author @alfonsoridao
 * @version 3.1
 */

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


    /**
     * Suppose any change is made in the list.
     * This method changes the status to a variable and
     * gives the option to the user to save the changes.
     */
    private void changesMade() {
        changesMade = true;
        save.setDisable(!changesMade);
    }


    /**
     * initialize the menu options to available/disable.
     */
    private void initMenu() {
        save.setDisable(!changesMade);
        exportSelected.setDisable(true);
    }

    /**
     * Initialize the GUI, charge the information from the file,
     * and set the visibility of the panels. The Home panel is the starter.
     */
    public void initialize(){
        searchBox.setVisible(false); // Version 3.2 ???
        this.playerList = ControlManager.getPlayerListFromFile();
        this.matchList = ControlManager.getMatchListFromFile(); // Get the data from the file
        matchList.updateRelation(playerList);
        homePane.setVisible(true);
        startHomePane();
        matchesPane.setVisible(false);
        playersPane.setVisible(false);
        initMenu();
    }

    /**
     * This handle event activates or deactivates the differents three pannels and their options.
     * @param e the button clicked.
     */
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

    /**
     * Initialize the match panel, the empty table, and the buttons.
     */
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

    /**
     * Clear the table, and then reload with the information about the match.
     */
    private void updateMatchTable() {
        matchTable.getItems().clear();
        matchTable.getItems().addAll(matchList.getMatchList());
    }

    /**
     * This method is called to visualize a match in the view mode.
     * This means that open a new window with the current format.
     * @param match the match to be viewed.
     */
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
        secondStage.setTitle("Match");
        secondStage.setScene(new Scene(root, 500, 600));
        ///////////////////////

        //Change the modality of the fist main to disable
        Stage fistStage = (Stage) mainAnchorPane.getScene().getWindow(); // I get the first stage.
        secondStage.initOwner(fistStage);
        secondStage.initModality(Modality.WINDOW_MODAL);
        secondStage.setResizable(false);
        mainAnchorPane.setDisable(true);
        ///////////////////////////////////////////////////
        secondStage.showAndWait();

        //Once the second stage was closed
        mainAnchorPane.setDisable(false);

    }

    /**
     * This action event control if the view button was selected, or double click made in the table,
     * and shoot the view matchMethod(match).
     * @param e the button clicked.
     */
    public void viewMatch(ActionEvent e) {
        Match match = matchTable.getSelectionModel().getSelectedItem();
        if (match != null) {
            viewMatch(match);
        }
    }

    /**
     * This action event is shooted when the "Add Match" button or "Edith Match" button is pressed.
     * Start the new match stage.
     * @param match the match to be modified. is Null if the option is "Add Match".
     * @param action the action to realize "add" or "edit".
     */
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
        System.out.println(matchControl);
        matchControl.transferData(match, matchList, action, playerList); // share the selection, the whole match list, the action and the player list
        ////////////////////////////////

        // Start the new Stage
        secondStage.setTitle("Match");
        secondStage.setScene(new Scene(root, 724, 600));
        secondStage.setResizable(false);
        ///////////////////////

        //Change the modality of the fist main to disable
        Stage fistStage = (Stage) mainAnchorPane.getScene().getWindow(); // I get the first stage.
        secondStage.initOwner(fistStage);
        secondStage.initModality(Modality.WINDOW_MODAL);
        mainAnchorPane.setDisable(true);
        ///////////////////////////////////////////////////

        secondStage.showAndWait();

        //Once the second stage was closed
        updateMatchTable();
        startHomePane();
        mainAnchorPane.setDisable(false);

    }

    /**
     * This action event control if the add button, or edit button was selected and shoot the match
     * stage actionMatch(match, action) with the action
     * @param e the button clicked, could be edit or add.
     */
    public void actionMatch(ActionEvent e)  {
        String action = (e.getSource() == editMatch) ? "edit" : "add"; // Recognise the action
        Match match = matchTable.getSelectionModel().getSelectedItem();
        System.out.println(match + "1");
        actionMatch(match, action);
    }

    /**
     * This method delete a selected match in the table.
     * @param match the selected match
     */
    private void deleteMatch(Match match){
        if (AlertControl.confirmationBox("You are deleting the match selected do you want to continue?", "Delete")) {
            matchList.deleteMatch(match);
            changesMade();
            updateMatchTable();
        }
    }

    /**
     * This method is shooted once the delete button is pressed after a match is selected in the table.
     * @param e the button pressed.
     */
    public void deleteMatch(ActionEvent e) {
        deleteMatch(matchTable.getSelectionModel().getSelectedItem());
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////PLAYERS FUNCTIONS/////////////////////////////////////////////////

    /**
     * Initialize the players panel, the empty table, and the buttons.
     */
    public void startPlayersPane(){
        //PLAYERS TAB DATA INITIALIZE//

        //Players table
        //Players data columns
        if (this.name != null) {
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

    /**
     * Clear the table, and then reload with the information about the players.
     */
    private void updatePlayersTable() {
        playersTable.getItems().clear();
        for (Player player: playerList.getPlayersList()) {
            if (!(player.systemStatus.isDeleted()))
            playersTable.getItems().add(player);
        }
    }

    /**
     * This action event is shooted when the "Add Player" button or "Edith Player" button is pressed,
     * or when double click is made on the table.
     * Start the new player stage.
     * @param player the match to be modified. is Null if the option is "Add Match".
     * @param action the action to realize "add" or "edit".
     */
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
        PlayerController playControl = fxmlLoader.getController();
        playControl.transferData(player, playerList, action); // share the selection, the whole player list, and the action
        //////////////////////////////////

        // Start the new Stage
        secondStage.setTitle("Player");
        secondStage.setScene(new Scene(root, 400, 600));
        secondStage.setResizable(false);
        ///////////////////////

        //Change the modality of the fist main to disable
        Stage fistStage = (Stage) mainAnchorPane.getScene().getWindow(); // I get the first stage.
        secondStage.initOwner(fistStage);
        secondStage.initModality(Modality.WINDOW_MODAL);
        mainAnchorPane.setDisable(true);
        ///////////////////////////////////////////////////

        secondStage.showAndWait();

        //Once the second stage was closed
        updatePlayersTable();
        startHomePane();
        mainAnchorPane.setDisable(false);
    }

    /**
     * This action event control if the add button, or edit button was selected and shoot the player
     * stage actionPlayer(player, action) with the action
     * @param e the button clicked, could be edit or add.
     */
    public void actionPlayer(ActionEvent e) {
        String action = (e.getSource() == editPlayer) ? "edit" : "add"; // Recognise the action
        Player player = playersTable.getSelectionModel().getSelectedItem();
        actionPlayer(player, action);
    }


    /**
     * This method delete a selected player in the table.
     * @param player the selected Player
     */
    public void deletePlayer(Player player) {
        if (AlertControl.confirmationBox("The player will be eliminated from the system " +
                "even if he/she is on the list for future or past matches. \n \n" +
                "Do you wish to continue?\n", "Delete")) {
            int playerIndex = playerList.getPlayerID(player);
            playerList.deletePlayer(player);
            matchList.updateBenchAndPitchArrays(playerIndex);
            changesMade();
            updatePlayersTable();
        }

    }

    /**
     * This method is shooted once the delete button is pressed after a player is selected in the table.
     * @param e the button pressed.
     */
    public void deletePlayer(ActionEvent e) {
        deletePlayer(playersTable.getSelectionModel().getSelectedItem());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////HOME FUNCTIONS/////////////////////////////////////////////////

    /**
     * This method initializes the Home Panel. This one includes a welcome with the day and eight different cards.
     * Each card only appears if there is some important information to show about the players and matches.
     */
    public void startHomePane() {
        ///////////////SET WELCOME MESSAGE////////////
        int hourNow = LocalDateTime.now().getHour();
        if (hourNow < 10) {
            regards.setText("God Morgen");
        } else if (hourNow >= 19) {
            regards.setText("Godaften");
        } else {
            regards.setText("God Dag");
        }

        ///////////////////SET DATE//////////////////
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

        //////////////SET NUMBERS OF PLAYERS IN THE SYSTEM//////////
        playersSystem.setText(playerList.getNumberOfPlayers() + "");

        //////////////SET DAYS PLAYER WITHOUT REST//////////
        Player mostPlayed = playerList.mostPlayedPlayer(matchList);
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
        HashSet<Integer> hashSetNeverPlayed = playerList.neverPlayed(matchList);
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

        //////////////SET TOTAL PLAYERS UNAVAILABLE//////////
        int unavailablePlayers = 0;
        for (int i=0; i < playerList.getSize(); i++) {
            if (playerList.getPlayerByPlayerId(i).systemStatus.isUnavailable()) {
                unavailablePlayers++;
            }
        }

        if (unavailablePlayers == 0) {
            totalPlayersCard.setVisible(false);
            totalPlayersCard.setManaged(false);
        } else {
            totalPlayersCard.setVisible(true);
            totalPlayersCard.setManaged(true);
            totalPlayers.setText(unavailablePlayers + "");
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////MENU FUNCTIONS////////////////////////////////////////////////////////

    /**
     * This function re-writes both files with the new information.
     * And then disable the save option in the menu.
     */
    private void save() {
        ControlManager.writeMatchesInFile(matchList);
        ControlManager.writePlayersInFile(playerList);
        changesMade = false;
        save.setDisable(!changesMade);
    }


    /**
     * action handler when the save menu is selected. Shoot the save() function.
     * @param e the event made
     */
    public void write(ActionEvent e) {
        save();
    }

    /**
     * Close the program. Not before verify if there was any change made,
     * and as if we want to save the changes.
     * @param e the event made
     */
    public void quit(ActionEvent e) {
        if (changesMade) {
            if(AlertControl.confirmationBox("Looks like you made some changes\n" +
                                            "\n Would you like to save them?", "Save")) {
                save();
            }
        }
        Stage stage = (Stage) mainAnchorPane.getScene().getWindow();
        stage.close();
    }

    /**
     *This event is open a new information box with the "about" information.
     * @param e the event.
     */
    public void about(ActionEvent e) {
        AlertControl.infoBox("Curiosity about life in all of its aspects, I think, is still the secret of great creative people.\n\nLeo Burnett", "About Life");
    }

    /**
     * this event launch the createXML method, with the match selected in the table.
     */
    public void exportXML(){
        Match match = matchTable.getSelectionModel().getSelectedItem();
        ControlManager.createXML(match, playerList);
    }

    /**
     * this event launch the createText method, with the match selected in the table.
     */
    public void exportText(){
        Match match = matchTable.getSelectionModel().getSelectedItem();
        ControlManager.createText(match, playerList);
    }

    /**
     * This method, after the previous confirmation, shoot createXML
     * and createTEXT to create both text and xml files for all the matches in the system.
     */
    public void exportAll(){
        if (AlertControl.confirmationBox("You are exporting the whole list of matches in text and web format.\n" +
                                                            "Probably, many files will be modified.\n" +
                                                            "I hope you know what are you doing \n\n" +
                                                            "Would you like to continue?", "Confirmation")) {
            for (int i =0; i < matchList.getSize(); i++) {
                ControlManager.createXML(matchList.getMatchByIndex(i),playerList);
                ControlManager.createText(matchList.getMatchByIndex(i),playerList);
        }
        }
    }

    /**
     * this funtion launch createXMLForPage to share the information with the web users.
     */
    public void exportContentToWebPage(){
        ControlManager.createStringForWebsite(matchList);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////GESTURE FUNCTIONS////////////////////////////////////////////////////////

    /**
     * This method is called after press the "DELETE" key in any table and shoot the deleteMatch,
     * or deletePlayer depends on which table was selected.
     * @param keyEvent the key pressed
     */
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

    /**
     * This method is called after double click any content in any table
     * and shoot the View if the table is a match table, or edit if the table is a player table.
     * @param mouseEvent the mouse event.
     */
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

