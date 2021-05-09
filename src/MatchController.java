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
    @FXML TableColumn<Player, String> name;
    @FXML TableColumn<Player, String> lastName;
    @FXML TableColumn<Player, Integer> number;
    @FXML TableColumn<Player, String> position;
    @FXML Button save;

    private int uid;
    private MatchList matchList;

    public void initialize() {
        //Inicio las columnas)
        if (name != null) {
            name.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
            lastName.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
            number.setCellValueFactory(new PropertyValueFactory<Player, Integer>("number"));
            position.setCellValueFactory(new PropertyValueFactory<Player, String>("position"));
            playersPitch.getItems().clear();
            playersBench.getItems().clear();
        } else {
            name = new TableColumn<>();
        }
    }

    public void transferData(Match match, MatchList matchList) {
        this.matchList = matchList;
        if (!(match == null)){
            System.out.println("Maradona +" + matchList);
            uid = MatchListManager.getPosition(matchList, match);
            matchID.setText("UID: " + uid);
            opponent.setText(match.getOpponent());
            date.setValue(match.getDate());
            place.setText(match.getPlace());

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
        } else {
            uid = MatchListManager.getNewPosition(matchList); // Creo un nuevo ID
            System.out.println("frances" + matchList);
        }
    }

    public void save(ActionEvent e) throws IOException {
        ////TEST!!!!!!!!!
        //ArrayList<Player> playersTest = new ArrayList<Player>();
        //playersTest.add(new Player());
        /////!!!!!!!!!!/1
        System.out.println("SAVED BABE!");
        Match match = new Match();
        match.setOpponent(opponent.getText());
        match.setDate(date.getValue());
        match.setPlace(place.getText());
        match.setKind("test mode");
        //match.setPlayersBench(playersTest);
        //match.setPlayersPitch(playersTest);
        MatchListManager.saveMatch(matchList, match, uid);

        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();





    }


}



