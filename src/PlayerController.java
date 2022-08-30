import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Player;
import model.PlayerList;
import model.ControlManager;
import utils.AlertControl;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;


/**
 * This class is the controller for the Player stage and
 * allows to create or edit any player
 * @author @alfonsoridao
 * @version 3.1.
 */

public class PlayerController {
    @FXML private Label uid;
    @FXML private TextField nameField;
    @FXML private TextField lastNameField;
    @FXML private TextField age;
    @FXML private DatePicker dateOfBirthField;
    @FXML private ComboBox<Integer> number;
    @FXML private CheckBox goalkeeper;
    @FXML private CheckBox defender;
    @FXML private CheckBox midfielder;
    @FXML private CheckBox forward;
    @FXML private JFXButton save;
    @FXML private JFXButton cancel;
    @FXML private RadioButton available;
    @FXML private RadioButton unavailable;
    @FXML private RadioButton suspended;
    @FXML private RadioButton injured;
    @FXML private ToggleGroup status;

    private PlayerList playerList;
    private int playerID;


    /**
     * Receiving the information from the AdminPanel, initialize the player.
     * If the player is null, all the fields are blank and create a new player.
     * Otherwise, the fields are completed with the player info.
     * @param player the player to edit. if is null, is add a player.
     * @param playerList the list of all the players.
     * @param action an String. could be "edit" or "add".
     */
    public void transferData(Player player, PlayerList playerList, String action) {
        this.playerList = playerList;
        HashSet<Integer> availableNumbers = playerList.getAvailableNumbers(); // Create an array of available numbers
        number.getItems().addAll(availableNumbers);

        if (action.equals("add")) {
            number.getSelectionModel().selectFirst();
            playerID = playerList.getSize();

        } else if (action.equals("edit") && player != null) {
                nameField.setText(player.getName());
                lastNameField.setText(player.getLastName());
                dateOfBirthField.setValue(player.getDateOfBirth());
                number.setValue(player.getNumber());
                int actualAge = Period.between(dateOfBirthField.getValue(), LocalDate.now()).getYears();
                age.setText(actualAge +" y/o");
                playerID = playerList.getPlayerID(player);

                    if (player.getPosition().contains("Goalkeeper")) goalkeeper.setSelected(true);
                    if (player.getPosition().contains("Defender")) defender.setSelected(true);
                    if (player.getPosition().contains("Midfielder")) midfielder.setSelected(true);
                    if (player.getPosition().contains("Forward")) forward.setSelected(true);

                switch (player.getStatus()) {
                    case "Available" : {
                        status.selectToggle(available);
                        break;
                    }
                    case "Unavailable" : {
                        status.selectToggle(unavailable);
                        break;
                    }
                    case "Suspended" : {
                        status.selectToggle(suspended);
                        break;
                    }
                    case "Injured" : {
                        status.selectToggle(injured);
                        break;
                    }
                }

            }
        uid.setText("#ID:" + playerID);
    }

    /**
     * Action event to pressing the button cancel. close the player panel.
     * @param e the action, and the button pressed.
     */
    public void cancel(ActionEvent e) {
        if (e.getSource() == cancel) {
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();
        }
    }


    /**
     * Action event to pressing the button save.
     * Creates or modifies the player first and inserts it in the player list.
     * Then closes the match panel.
     * @param e the action, and the button pressed.
     */
    public void save(ActionEvent e) {

        if (e.getSource() == save) {

            if (nameField.getText() == "") {
                AlertControl.warningBox("You must insert a Name", "Error");
            }
            else if (lastNameField.getText() == "") {
                AlertControl.warningBox("You must insert a Last Name", "Error");
            }
            else if (dateOfBirthField.getValue() == null) {
                AlertControl.warningBox("I'm getting really angry 😡", "Error");
            }
            else if (!goalkeeper.isSelected() && !defender.isSelected() &&
                    !midfielder.isSelected() && !forward.isSelected()) {
                AlertControl.warningBox("You must select at least one position trained.", "Error");
            } else { // Finally I add the player

                ///// Create the player///////
                Player player = new Player(playerID);
                player.setName(nameField.getText());
                player.setLastName(lastNameField.getText());
                player.setDateOfBirth(dateOfBirthField.getValue());
                player.setNumber(number.getValue());
                if (goalkeeper.isSelected()) player.setPosition("Goalkeeper");
                if (defender.isSelected()) player.setPosition("Defender");
                if (midfielder.isSelected()) player.setPosition("Midfielder");
                if (forward.isSelected()) player.setPosition("Forward");
                RadioButton selectedToggle = ((RadioButton) status.getSelectedToggle());
                player.setStatus(selectedToggle.getText());
                player.systemStatus.setStatus(selectedToggle.getText());

                ///////
                playerList.updatePlayerList(playerID, player);
                Stage stage = (Stage) save.getScene().getWindow();
                stage.close();

            }
        }
    }
}
