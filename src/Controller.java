import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML private TableView<Player> playersTable;
    @FXML private TableColumn<Player, String> name;
    @FXML private TableColumn<Player, String> lastName;
    @FXML private TableColumn<Player, Integer> number;
    @FXML private TableColumn<Player, String> position;
    @FXML private Button addPlayer;
    @FXML private Button editPlayer;


    @FXML private Button launch;
    @FXML private TextField tfield;


    public void initialize() {

        PlayerList playerList = new PlayerList();
        Player playerOne = new Player("Hugo", "Broglio", 37, 9, "Central");
        Player playerTwo = new Player("Mariano", "Cola", 38, 10, "forward");
        Player playerThree = new Player("Jose", "Pasuccho", 45, 5,"media");


        playerList.add(playerOne);
        playerList.add(playerTwo);
        playerList.add(playerThree);

        name.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
        number.setCellValueFactory(new PropertyValueFactory<Player, Integer>("number"));
        position.setCellValueFactory(new PropertyValueFactory<Player, String>("position"));

        playersTable.getItems().clear();


        for (Player player: playerList.getPlayersList()) {
           playersTable.getItems().add(player);
           System.out.println(player);
           }


    }


    public void launch(ActionEvent e) throws IOException {
        System.out.println("HAYYY" + playersTable.getSelectionModel().getSelectedItem());
        Stage secondStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane root = fxmlLoader.load(getClass().getResource("player.fxml").openStream());

        /// SHARING DATA ///
        if (e.getSource() == editPlayer) {
            playerControler playControl = fxmlLoader.getController();
            playControl.transferData(playersTable.getSelectionModel().getSelectedItem());
        }
        //////////////////////

        secondStage.setTitle("VIA Club");
        secondStage.setScene(new Scene(root, 600, 675));
        secondStage.showAndWait();
    }

    public void oneClick(ActionEvent e) throws IOException {
        System.out.println("Yeah!");
    }

}
