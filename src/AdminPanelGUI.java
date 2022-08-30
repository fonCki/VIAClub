import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class initializes the main stage.
 */
    public class AdminPanelGUI extends Application {

    /**
     * Launch the primary stage.
     * @param primaryStage the main stage.
     * @throws Exception the panel is not found
     */
    public void start(Stage primaryStage) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
            primaryStage.setScene(new Scene(root, 1024, 627));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
    }

