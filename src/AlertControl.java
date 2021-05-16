import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertControl {

         ///////////////////INFORMATION BOX//////////////////////////////////////////////////

         //By specifying a null headerMessage String, we cause the dialog to not have a header
        public static void infoBox(String infoMessage, String titleBar) {
            infoBox(infoMessage, titleBar, null);
        }

        public static void infoBox(String infoMessage, String titleBar, String headerMessage) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(titleBar);
            alert.setHeaderText(headerMessage);
            alert.setContentText(infoMessage);
            alert.showAndWait();
        }
        ////////////////////////////////////////////////////////////////////////////////////

        ///////////////////ERROR BOX////////////////////////////////////////////////////////
        public static void errorBox(String errorMessage, String titleBar) {
            errorBox(errorMessage, titleBar, null);
        }

        public static void errorBox(String errorMessage, String titleBar, String headerMessage) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(titleBar);
            alert.setHeaderText(headerMessage);
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
       ////////////////////////////////////////////////////////////////////////////////////

        ///////////////////CONFIRMATION BOX//////////////////////////////////////////////////
        public static boolean confirmationBox(String confirmationMessage, String titleBar) {
            return confirmationBox(confirmationMessage, titleBar, null);
        }

        public static boolean confirmationBox(String confirmationMessage, String titleBar, String headerMessage) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(titleBar);
            alert.setHeaderText(headerMessage);
            alert.setContentText(confirmationMessage);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                return true;
            } else
                return false;
        }
        ////////////////////////////////////////////////////////////////////////////////////

        ///////////////////INFORMATION BOX//////////////////////////////////////////////////
        public static void warningBox(String warningMessage, String titleBar) {
            warningBox(warningMessage, titleBar, null);
        }

        public static void warningBox(String warningMessage, String titleBar, String headerMessage) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle(titleBar);
            alert.setHeaderText(headerMessage);
            alert.setContentText(warningMessage);
            alert.showAndWait();
        }
        ////////////////////////////////////////////////////////////////////////////////////
}

