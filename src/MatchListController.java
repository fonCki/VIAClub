import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.time.LocalDate;

public class MatchListController {

    ////// MATCH TAB OBJECTS
    @FXML private AnchorPane anchorPane;
    @FXML private TableView<Match> matchTable;
    @FXML private TableColumn<Match, LocalDate> date;
    @FXML private TableColumn<Match, String> opponent;
    @FXML private TableColumn<Match, String> place;
    @FXML private TableColumn<Match, String> kind;
    @FXML private Button editButton;
    @FXML private Button addMatch;
    @FXML private Button deleteMatch;
    @FXML private Button reload;
    @FXML private Button write;


    private MatchList matchList;
    private PlayerList playerList;

    ///// PlayersTabObject

    private void updateMatchTable() {
        matchTable.getItems().clear();
        for (Match match: matchList.getMatchList()) {
            matchTable.getItems().add(match);
            System.out.println(match);
        }

    }

    public void initialize() {
        matchList = MatchListManager.getMatchListFromFile();
        this.playerList = PlayerListManager.getPlayerListFromFile();

        date.setCellValueFactory(new PropertyValueFactory<Match, LocalDate>("date"));
        opponent.setCellValueFactory(new PropertyValueFactory<Match, String>("opponent"));
        place.setCellValueFactory(new PropertyValueFactory<Match, String>("place"));
        kind.setCellValueFactory(new PropertyValueFactory<Match, String>("kind"));
        updateMatchTable();
        editButton.disableProperty().bind(Bindings.isEmpty(matchTable.getSelectionModel().getSelectedItems())); // Button is only available if a match is selected!!
        deleteMatch.disableProperty().bind(Bindings.isEmpty(matchTable.getSelectionModel().getSelectedItems()));
    }





    public void actionMatch(ActionEvent e) throws IOException {
            String action = (e.getSource() == editButton) ? "edit" : "add";
            Stage secondStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane root = fxmlLoader.load(getClass().getResource("match.fxml").openStream());

            /// SHARING DATA ///
            if (e.getSource() == editButton ||  e.getSource() == addMatch) {
                MatchController matchControl = fxmlLoader.getController();
                matchControl.transferData(matchTable.getSelectionModel().getSelectedItem(), matchList, action, playerList); // Comparto si una esta seleccionada, y tambien el matchList
            }
            //////////////////////

            secondStage.setTitle("VIA Club");
            secondStage.setScene(new Scene(root, 850, 855));


         //   anchorPane.setDisable(true);
            /*
            secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    anchorPane.setDisable(false);
                }
            });
*/

            secondStage.showAndWait();
            updateMatchTable();
       //     anchorPane.setDisable(false);


    }

    public void deleteMatch(ActionEvent e) {
        System.out.println(matchTable.getSelectionModel().getSelectedItem());
        MatchListManager.deleteMatch(matchList, matchTable.getSelectionModel().getSelectedItem());
        updateMatchTable();
    }


    public void reload(ActionEvent e) {
        updateMatchTable();
    }

    public void write(ActionEvent e) {
        MatchListManager.writeInFile(matchList);
    }

}
