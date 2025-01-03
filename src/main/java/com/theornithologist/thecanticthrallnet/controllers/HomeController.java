package com.theornithologist.thecanticthrallnet.controllers;

import com.theornithologist.thecanticthrallnet.datahandling.DataParser;
import com.theornithologist.thecanticthrallnet.datahandling.DatabaseUpdater;
import com.theornithologist.thecanticthrallnet.datahandling.FileConstants;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;

public class HomeController {
    @FXML
    private Button updateButton;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private HBox topBar;
    @FXML
    private Button listButton;

    SceneController sceneController = new SceneController();

    public void onUpdate() {
        DatabaseUpdater databaseUpdater = new DatabaseUpdater();
        DataParser dataParser = new DataParser();
        databaseUpdater.clearTable();
        updateButton.setDisable(true);
        progressBar.setVisible(true);
        topBar.setDisable(true);
        Task<Void> initialize = new Task<>() {
            @Override
            protected Void call() throws Exception {
                databaseUpdater.fileUpdate();
                dataParser.trimCSV();
                databaseUpdater.populateUpdateTime();
                databaseUpdater.populateData();
                updateButton.setDisable(false);
                progressBar.setVisible(false);
                topBar.setDisable(false);
                return null;
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

    public void factionButton(ActionEvent e) throws IOException {
        sceneController.factionSelect(e);
        sceneController.buttonAction(e, "faction.fxml");
    }

    public void selectListButton(ActionEvent e) throws IOException {
        File listLocation = new File(FileConstants.DataRoot() + "\\rosters");
        boolean created = listLocation.mkdir();
        if (!created) {
            System.err.println("roster directory not created!");
        }
        sceneController.buttonAction(e, "listBuilder.fxml");
    }
}
