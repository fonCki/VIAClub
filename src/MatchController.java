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
    @FXML Button addPlayers;

    private int uid;
    private MatchList matchList;
    private Match match;
    private PlayerList playerList;

    public void initialize() {
        this.playerList = PlayerListManager.getPlayerListFromFile();

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

    public void transferData(Match match, MatchList matchList, String action) {
        this.matchList = matchList;
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



            // kind // FINISH THIS PART
            //Cargo los playersPich
            /*
            for (Player player: match.getPlayersPitch()) {
                playersPitch.getItems().add(player);
            }
            //Cargo los playersBench
            for (Player player: match.getPlayersBench()) {
                playersBench.getItems().add(player);
            }

             */
        }
    }

    public void createMatch() {

    }

    public void save(ActionEvent e) throws IOException {
        ///Creo el match///
        if (match == null) {
            match = new Match(opponent.getText(), date.getValue(), place.getText(), ((RadioButton) kind.getSelectedToggle()).getText());
        }
        System.out.println("Este es el match " + match);
        MatchListManager.saveMatch(matchList, match, uid);
        //////////////////
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();

    }

    public void addPlayers(ActionEvent e) throws IOException {
        ///Creo el match///
        if (match == null) {
            match = new Match(opponent.getText(), date.getValue(), place.getText(), ((RadioButton) kind.getSelectedToggle()).getText());
        }
        MatchListManager.saveMatch(matchList, match, uid);


        Stage thirdStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane root = fxmlLoader.load(getClass().getResource("includePlayers.fxml").openStream());

        IncludePlayersController includePlayersController = fxmlLoader.getController();

        includePlayersController.transferData(match, playerList); // Comparto


        thirdStage.setTitle("VIA Club");
        thirdStage.setScene(new Scene(root, 850, 855));

        thirdStage.showAndWait();

        updateTables();
    }


}



