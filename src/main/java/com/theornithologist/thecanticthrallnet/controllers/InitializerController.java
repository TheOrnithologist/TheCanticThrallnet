package com.theornithologist.thecanticthrallnet.controllers;

import com.theornithologist.thecanticthrallnet.CanticThrallnet;
import com.theornithologist.thecanticthrallnet.datahandling.DataParser;
import com.theornithologist.thecanticthrallnet.datahandling.DatabaseUpdater;
import com.theornithologist.thecanticthrallnet.datahandling.FileConstants;
import javafx.application.Platform;
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
import java.util.Objects;

public class InitializerController {
    @FXML
    private Button intializeButton;
    @FXML
    private ProgressBar progressBar;

    @FXML
    protected void onButtonClick() {
        DataParser dataParser = new DataParser();
        DatabaseUpdater databaseUpdater = new DatabaseUpdater();
        intializeButton.setVisible(false);
        intializeButton.setDisable(true);
        progressBar.setVisible(true);
        Task<Void> initialize = new Task<>() {
            @Override
            protected Void call() throws Exception {
                databaseUpdater.fileUpdate();
                dataParser.trimCSV();
                databaseUpdater.generateDB();
                databaseUpdater.initializeTable();
                databaseUpdater.populateUpdateTime();
                databaseUpdater.populateData();
                File firstLaunch = new File(FileConstants.DataRoot() + "\\launchCheck.txt");
                boolean created = firstLaunch.createNewFile();
                if (!created) {
                    System.err.println("Launch check file not created.");
                }
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                Platform.runLater(() -> {
                    try {
                        Parent root = FXMLLoader.load(Objects.requireNonNull(CanticThrallnet.class.getResource("home.fxml")));
                        Stage stage = (Stage) (progressBar.getScene().getWindow());
                        Scene scene;
                        scene = new Scene(root, 1600, 900);
                        scene.getStylesheets().add(Objects.requireNonNull(CanticThrallnet.class.getResource("styles.css")).toExternalForm());
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void failed() {
                super.failed();
                Throwable exception = getException();
                exception.printStackTrace();
            }
        };


        Task<Void> progress = new Task<>() {
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
    }
}