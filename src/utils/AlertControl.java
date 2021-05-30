package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;


/**
 * This class has all the alert controls for the system.
 * Includes four kinds of alert window: (INFORMATION, ERROR, CONFIRMATION, and WARNING).
 * All the methods are static.
 * @author @alfonsoridao.
 * @version 3.1
 */
public class AlertControl {


    ///////////////////INFORMATION BOX//////////////////////////////////////////////////

    /**
     * Open an information box with the message and a title, set the header as null.
     * @param infoMessage the message to be included.
     * @param titleBar the title of the box.
     */
        public static void infoBox(String infoMessage, String titleBar) {
            infoBox(infoMessage, titleBar, null);
        }

    /**
     * Open an information box with the message, a title and a header message.
     * @param infoMessage the message to be included.
     * @param titleBar the title of the box.
     * @param headerMessage the header of the box
     */
        public static void infoBox(String infoMessage, String titleBar, String headerMessage) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(titleBar);
            alert.setHeaderText(headerMessage);
            alert.setContentText(infoMessage);
            alert.showAndWait();

        }
        ////////////////////////////////////////////////////////////////////////////////////

        ///////////////////ERROR BOX////////////////////////////////////////////////////////

    /**
     * Open an error box with the message and a title, set the header as null.
     * @param errorMessage the message to be included.
     * @param titleBar the title of the box.
     */
        public static void errorBox(String errorMessage, String titleBar) {
            errorBox(errorMessage, titleBar, null);
        }

    /**
     * Open an error box with the message, a title and a header message.
     * @param errorMessage the message to be included.
     * @param titleBar the title of the box.
     * @param headerMessage the header of the box
     */
        public static void errorBox(String errorMessage, String titleBar, String headerMessage) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(titleBar);
            alert.setHeaderText(headerMessage);
            alert.setContentText(errorMessage);
            alert.showAndWait();

        }
       ////////////////////////////////////////////////////////////////////////////////////

        ///////////////////CONFIRMATION BOX//////////////////////////////////////////////////

    /**
     * Open a confirmation box with the message and a title, set the header as null, and 2 options to response.
     * @param confirmationMessage the message to be included.
     * @param titleBar the title of the box.
     * @return true, if the selection is "Ok", false if the selection is "Cancel".
     */
        public static boolean confirmationBox(String confirmationMessage, String titleBar) {
            return confirmationBox(confirmationMessage, titleBar, null);
        }

    /**
     * Open a confirmation box with the message, a title, a header message, and 2 options to response.
     * @param confirmationMessage the message to be included.
     * @param titleBar the title of the box.
     * @param headerMessage the header of the box
     * @return true, if the selection is "Ok", false if the selection is "Cancel".
     */
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

        ///////////////////WARNING BOX//////////////////////////////////////////////////

    /**
     * Open an warning box with the message and a title, set the header as null.
     * @param warningMessage the message to be included.
     * @param titleBar the title of the box.
     */
        public static void warningBox(String warningMessage, String titleBar) {
            warningBox(warningMessage, titleBar, null);
        }

    /**
     * Open an warning box with the message, a title and a header message.
     * @param warningMessage the message to be included.
     * @param titleBar the title of the box.
     * @param headerMessage the header of the box
     */
        public static void warningBox(String warningMessage, String titleBar, String headerMessage) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle(titleBar);
            alert.setContentText(warningMessage);
            alert.setHeaderText(headerMessage);
            alert.showAndWait();
        }
        ////////////////////////////////////////////////////////////////////////////////////


}

