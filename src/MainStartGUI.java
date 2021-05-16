import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainStartGUI extends Application {

    public void start(Stage mainStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        mainStage.setScene(new Scene(root, 862, 728));
        mainStage.setTitle("VIA Club");
        mainStage.show();
    }
}