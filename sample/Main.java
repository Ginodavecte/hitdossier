package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        DB.open();
        Parent root = FXMLLoader.load(getClass().getResource("UserInterface/main.fxml"));
        primaryStage.setTitle("Eindopdracht");
        primaryStage.setScene(new Scene(root, 500, 475));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DB.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
