

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlayerListStartGUI extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("playerList.fxml"));
        primaryStage.setTitle("VIA Club");
        primaryStage.setScene(new Scene(root, 600, 675));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
