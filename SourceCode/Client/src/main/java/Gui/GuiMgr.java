package Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class GuiMgr extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException{
        URL url = getClass().getResource("/GuiLayout/UI.fxml");

        Parent root = FXMLLoader.load(url);

        Scene scene = new Scene(root, 933, 618);

        primaryStage.setTitle("LoGrep");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
