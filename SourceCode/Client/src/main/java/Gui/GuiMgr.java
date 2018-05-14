package Gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class GuiMgr extends Application {

    @FXML
    private Pane fieldPane;

    @FXML
    private VBox fieldsContainer;

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

    @FXML
    public void exitButtonClick(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void searchClick(ActionEvent event) {

    }

    @FXML
    public void exportResultsClick(ActionEvent event) {

    }

    @FXML
    public void newFieldClick(ActionEvent event) {

        Pane pane = new Pane();
        Button btn = new Button();

        pane.getChildren().addAll(btn);

        fieldsContainer.getChildren().addAll(fieldPane);
    }

    @FXML
    public void deleteFieldClick(ActionEvent event) {

    }
}
