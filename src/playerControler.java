import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;


public class playerControler {
    @FXML private Label uid;
    @FXML private TextField nameField;
    @FXML private TextField lastNameField;
    @FXML private DatePicker dateOfBirth;
    @FXML private ChoiceBox<Integer> numberField;
    @FXML private CheckBox goalKeeper;
    @FXML private CheckBox defender;
    @FXML private CheckBox midfielder;
    @FXML private CheckBox forward;
    @FXML private Button save;
    @FXML private Button cancel;


    public void initialize() {
        ArrayList<Integer> availableNumbers = new ArrayList<Integer>();
        availableNumbers.add(8);
        numberField.getItems().addAll(availableNumbers);
    }

    public void transferData(Player player) {
            nameField.setText(player.getName());
            lastNameField.setText(player.getLastName());
    }

    public void handle(ActionEvent e) {
        if (e.getSource() == cancel) {
        }
        if (e.getSource() == save) {

            if (nameField.getText() == "") {
                AlertControl.infoBox("Insert Name", "Error");
            }
            if (lastNameField.getText() == "") {
                AlertControl.infoBox("Insert lastName", "Error");
            }

        }
    }
}
