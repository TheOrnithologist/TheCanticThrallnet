package com.theornithologist.thecanticthrallnet.controllers;

import com.theornithologist.thecanticthrallnet.CanticThrallnet;
import com.theornithologist.thecanticthrallnet.datahandling.DataParser;
import com.theornithologist.thecanticthrallnet.datahandling.DatabaseUpdater;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class InitializerController {
    @FXML
    private Button intializeButton;
    @FXML
    private ProgressBar progressBar;

    @FXML
    protected void onButtonClick() throws IOException, SQLException {
        DataParser dataParser = new DataParser();
        DatabaseUpdater databaseUpdater = new DatabaseUpdater();
        intializeButton.setVisible(false);
        intializeButton.setDisable(true);
        progressBar.setVisible(true);
        Task<Void> initialize = new Task<>() {
            @Override
            protected Void call() throws Exception {
                databaseUpdater.fileUpdate();
                System.out.println("Files downloaded");
                databaseUpdater.generateDB();
                System.out.println("db made");
                databaseUpdater.initializeTable();
                System.out.println("tables made");
                dataParser.trimCSV();
                System.out.println("CSVs trimmer");
                databaseUpdater.populateUpdateTime();
                System.out.println("update time updated");
                databaseUpdater.populateData();
                System.out.println("data populated");
                File firstLaunch = new File("launchCheck.txt");
                firstLaunch.createNewFile();
                System.out.println("file created");
                return null;
            }
        };

        Task<Void> progress = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                  for (int i = 1; i <= 100; i++) {
                      updateProgress(i, 100);
                      Thread.sleep(2000);
                  }
                return null;
            }
        };
        progressBar.progressProperty().bind(progress.progressProperty());
        new Thread(progress).start();
        new Thread(initialize).start();
        Parent root = FXMLLoader.load(CanticThrallnet.class.getResource("home.fxml"));
        Stage stage = new Stage();
        Scene scene;
        scene = new Scene(root, 1000, 1000);
        scene.getStylesheets().add(CanticThrallnet.class.getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}