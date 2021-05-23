import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Player;
import model.PlayerList;
import model.PlayerListManager;
import utils.AlertControl;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;


public class PlayerControler {
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


    public void initialize() {

    }


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

    public void cancel(ActionEvent e) {
        if (e.getSource() == cancel) {
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();
        }
    }

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
