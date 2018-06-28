package Gui;

import APIs.SolrAPI;
import Commands.IndexNowCommand;
import Exceptions.IncorrectParameterException;
import Executors.Executor;
import Factories.CommandFactory;
import Interfaces.Command;
import Interfaces.Query;
import Utils.StringsMapping;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GuiManager extends Application {

    private List<String> searchFields;

    @FXML
    private Button executeButton;

    @FXML
    private Button triggerIndexJob;

    @FXML
    private TextArea resultsBox;

    @FXML
    private VBox fieldsContainer;

    @FXML
    private ChoiceBox resultsOrdering;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private TextField timeFrom;

    @FXML
    private TextField timeTo;

    @FXML
    private Label statusBox;

    private Stage internalStage;

    private void initializeFields() {
        searchFields = new ArrayList<>();

        try {
            searchFields = SolrAPI.getAllFields();
        }catch (SolrServerException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void initializeOrderBox() {

        List<String> orderDirections = new ArrayList<>();

        orderDirections.add("ASC");
        orderDirections.add("DESC");

        ObservableList list = FXCollections.observableArrayList(orderDirections);

        resultsOrdering.setItems(list);
    }

    @FXML
    public void initialize() {
        initializeOrderBox();

        statusBox.setText("Ready");
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL url = getClass().getResource("/GuiLayout/UI.fxml");

        Parent root = FXMLLoader.load(url);

        Scene scene = new Scene(root, 1117, 607);

        primaryStage.setTitle("loGrep");
        primaryStage.setScene(scene);
        primaryStage.show();

        internalStage = primaryStage;
    }

    @FXML
    public void exitButtonClick(ActionEvent event) {
        Platform.exit();
    }

    private String[] getRawQuery() {
        ObservableList<Node> fieldEntries = fieldsContainer.getChildren();

        int i = 0;
        String[] rawQuery = null;

        if(fieldEntries.size() != 0)
            rawQuery = new String[fieldEntries.size()];

        for(Node node : fieldEntries) {
            Object selectedFieldObject = ((ChoiceBox)((FieldEntry)node).getChildren().get(3)).getSelectionModel().getSelectedItem();
            Object valueObject = ((TextField)((FieldEntry)node).getChildren().get(2)).getText();

            if(selectedFieldObject != null && valueObject != null) {
                if(valueObject != "")
                    rawQuery[i] = selectedFieldObject + "=" + valueObject;
            }

            i++;
        }

        return rawQuery;
    }

    private String[] getRawDateInterval() {
        String[] result = null;

        LocalDate localDateFrom = dateFrom.getValue();
        LocalDate localDateTo = dateTo.getValue();

        if(localDateFrom != null && localDateTo != null) {
            result = new String[2];

            result[0] = localDateFrom.getYear() + "-" + localDateFrom.getMonthValue() + "-" + localDateFrom.getDayOfMonth();
            result[1] = localDateTo.getYear() + "-" + localDateTo.getMonthValue() + "-" + localDateTo.getDayOfMonth();
        }

        return result;
    }

    private String[] getRawTimeInterval() {
        String[] result = null;

        String timeFromText = timeFrom.getText();
        String timeToText = timeTo.getText();

        if(!timeFromText.equals("") && !timeToText.equals("")) {

            result = new String[2];

            result[0] = timeFrom.getText();
            result[1] = timeTo.getText();
        }

        return result;
    }

    private String[] getRawOrdering() {
        String[] result = null;

        Object object = resultsOrdering.getSelectionModel().getSelectedItem();

        if(object != null) {
            result = new String[1];
            result[0] = object.toString();
        }

        return result;
    }

    private void accumulateQueries(List<Query> queries, CommandFactory factory) {

        String[] rawQuery = getRawQuery();
        String[] rawDateInterval = getRawDateInterval();
        String[] rawTimeInterval = getRawTimeInterval();
        String[] rawOrdering = getRawOrdering();

        try {

            if(rawQuery != null) {
                Query query = factory.getQuery(StringsMapping.queryShort);

                query.setParams(rawQuery);
                queries.add(query);
            }

            if(rawDateInterval != null) {
                Query query = factory.getQuery(StringsMapping.dateIntervalShort);

                query.setParams(rawDateInterval);
                queries.add(query);
            }

            if(rawTimeInterval != null) {
                Query query = factory.getQuery(StringsMapping.timeIntervalShort);

                query.setParams(rawTimeInterval);
                queries.add(query);
            }

            if(rawOrdering != null) {
                Query query = factory.getQuery(StringsMapping.orderByTimeStampShort);

                query.setParams(rawOrdering);
                queries.add(query);
            }

        } catch (IncorrectParameterException exception) {
            exception.printStackTrace();
        }
    }

    /*
    private String[] getRawIndexNow() {
        if(indexNow.isSelected())
            return new String[1];
        return null;
    }
    */

    private void accumulateCommands(List<Command> commands, CommandFactory factory) throws IncorrectParameterException{

        //String[] rawIndexInterval = getRawIndexInterval();
        //String[] rawIndexNow = getRawIndexNow();

        /*
        if(rawIndexInterval != null) {
            Command command = factory.getCommand(StringsMapping.indexIntervalShort);

            command.setParams(rawIndexInterval);
            commands.add(command);
        }
        */

        /*if(rawIndexNow != null) {
            Command command = factory.getCommand(StringsMapping.indexNowShort);
            commands.add(command);
        }*/

    }

    private void getResults() {
        String results = Executor.getMoreResults();

        if(results != null) {
            resultsBox.appendText(results);
            statusBox.setText("the results were displayed successfully");
        } else {
            executeButton.setText("Execute");
            statusBox.setText("no more results to display");
        }
    }

    private void performSearch() {
        List<Query> queries = new ArrayList<>();
        List<Command> commands = new ArrayList<>();

        CommandFactory factory = new CommandFactory();

        try {

            accumulateQueries(queries, factory);
            accumulateCommands(commands, factory);

            if(commands.size() > 0) {
                Executor.executeCommands(commands);
            }

            else if(queries.size() > 0) {
                Executor.executeQueries(queries);

                resultsBox.setText("");
                getResults();

                executeButton.setText("Show more");
            }
        } catch (SolrServerException | IncorrectParameterException exception) {
            exception.printStackTrace();
        }
    }

    private void showMoreResults() {
        getResults();
    }

    @FXML
    public void searchClick(ActionEvent event) {
        if(executeButton.getText().equals("Execute")) {
            performSearch();
        } else if(executeButton.getText().equals("Show more")) {
            showMoreResults();
        }
    }

    @FXML
    public void triggerIndexJob(ActionEvent event) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        triggerIndexJob.setText("Index job in progress");
                        triggerIndexJob.setDisable(true);
                    }
                });

                IndexNowCommand command = new IndexNowCommand();

                command.execute();

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        triggerIndexJob.setText("Trigger Index Job");
                        triggerIndexJob.setDisable(false);
                    }
                });
            }
        });

        t.start();
    }

    @FXML
    public void clearBoxClick(ActionEvent event) {
        resultsBox.setText("");
        executeButton.setText("Execute");
        statusBox.setText("Results box cleared!");
    }

    @FXML
    public void exportResultsClick(ActionEvent event) {
        if(resultsBox.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Results not present");
            alert.setContentText("There are no results to export. Please run a query to get some results");

            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {}
            });
        } else {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(internalStage);

            if(file != null){
                SaveFile(file);
            }
        }
    }

    private void SaveFile(File file) {
        try {

            FileWriter fileWriter = null;

            fileWriter = new FileWriter(file);
            fileWriter.write(resultsBox.getText());

            fileWriter.close();

            statusBox.setText("the results were exported successfully");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<String> getRemainingFields(ObservableList<Node> children) {
        ArrayList<String> result = new ArrayList<>();

        initializeFields();

        result.addAll(searchFields);

        for(Node child : children) {

            Object selectedItem = ((ChoiceBox)((FieldEntry)child).getChildren().get(3)).getSelectionModel().getSelectedItem();

            if(selectedItem != null) {
                result.remove(selectedItem.toString());
            } else {
                return null;
            }
        }

        return result;
    }

    @FXML
    public void newFieldClick(ActionEvent event) {
        ArrayList<String> remainingFields = getRemainingFields(fieldsContainer.getChildren());

        if(remainingFields == null)  {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Field not selected");
            alert.setContentText("You need to select a field to search on before adding a new field");

            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {}
            });
        } else {

            if(remainingFields.size() != 0) {
                FieldEntry entry = new FieldEntry(remainingFields);

                fieldsContainer.getChildren().add(entry);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("There are no more fields to search on");
                alert.setContentText("You cannot add more fields to search on because you selected all the fields that were available");

                alert.showAndWait().ifPresent(rs -> {});
            }
        }
    }

    @FXML
    public void deleteFieldClick(ActionEvent event) {
        fieldsContainer.getChildren().clear();
        statusBox.setText("All fields cleared");
    }
}
