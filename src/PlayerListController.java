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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class PlayerListController {

    @FXML private TableView<Player> playersTable;
    @FXML private TableColumn<Player, String> name;
    @FXML private TableColumn<Player, String> lastName;
    @FXML private TableColumn<Player, Integer> number;
    @FXML private TableColumn<Player, String> position;
    @FXML private TableColumn<Player, String> status;
    @FXML private Button deletePlayer;
    @FXML private Button addPlayer;
    @FXML private Button editPlayer;
    @FXML private AnchorPane anchorPane;


    @FXML private Button launch;
    @FXML private TextField tfield;

    private PlayerList playerList;

    private void updateTable() {
        playersTable.getItems().clear();
        for (Player player: playerList.getPlayersList()) {
            if (!(player.systemStatus.isDeleted()))
                playersTable.getItems().add(player);
        }
    }

    public void initialize() {
        //// Inicio el playerList
        playerList = PlayerListManager.getPlayerListFromFile();


        //Inicio las columnas)
        name.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
        number.setCellValueFactory(new PropertyValueFactory<Player, Integer>("number"));
        position.setCellValueFactory(new PropertyValueFactory<Player, String>("position"));
        status.setCellValueFactory(new PropertyValueFactory<Player, String>("status"));
        updateTable();

        editPlayer.disableProperty().bind(Bindings.isEmpty(playersTable.getSelectionModel().getSelectedItems())); // Button is only available if a match is selected!!
        deletePlayer.disableProperty().bind(Bindings.isEmpty(playersTable.getSelectionModel().getSelectedItems()));



    }


    public void actionPlayer(ActionEvent e) throws IOException {

        String action = (e.getSource() == editPlayer) ? "edit" : "add"; // Create the action

        Stage secondStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane root = fxmlLoader.load(getClass().getResource("player.fxml").openStream());

        /// SHARING DATA ///

        PlayerControler playControl = fxmlLoader.getController();
        playControl.transferData(playersTable.getSelectionModel().getSelectedItem(), playerList, action); // Comparto si una esta seleccionada, y tambien el playerList
        //////////////////////

        secondStage.setTitle("Player info");
        secondStage.setScene(new Scene(root, 450, 655));


//        anchorPane.setDisable(true);

        secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                anchorPane.setDisable(false);
            }
        });


        secondStage.showAndWait();
        updateTable();
//        anchorPane.setDisable(false);


    }

    public void deletePlayer(ActionEvent e) {
        playerList.deletePlayer(playersTable.getSelectionModel().getSelectedItem());
        updateTable();
    }

    public void refresh(ActionEvent e) {
        updateTable();
    }

    public void write(ActionEvent e) {
        PlayerListManager.writeInFile(playerList);
    }

}
