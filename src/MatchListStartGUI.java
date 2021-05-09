import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MatchListStartGUI extends Application {

    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("matchList.fxml"));
        primaryStage.setScene(new Scene(root, 800, 875));
        primaryStage.show();

    }
}
