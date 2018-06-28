package Gui;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

public class FieldEntry extends Pane {

    public FieldEntry(List<String> fields) {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this);

        try {
            Pane pane = fxmlLoader.load(getClass().getResourceAsStream("/GuiLayout/FieldEntry.fxml"));

            this.setPrefWidth(341);
            this.setPrefHeight(75);

            this.setWidth(341);
            this.setHeight(75);

            this.setMinWidth(341);
            this.setMinHeight(75);

            this.setMaxWidth(341);
            this.setMaxHeight(75);

            ObservableList<Node> children = pane.getChildren();

            ChoiceBox choiceBox = (ChoiceBox)children.get(1);

            children.remove(1);

            choiceBox.setItems(FXCollections.observableArrayList(fields));

            children.add(choiceBox);

            this.getChildren().addAll(children);

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
