package com.theornithologist.thecanticthrallnet.controllers;

import com.theornithologist.thecanticthrallnet.datahandling.DataParser;
import com.theornithologist.thecanticthrallnet.datahandling.DatabaseUpdater;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.SQLException;

public class HomeController {
    @FXML
    private Button updateButton;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private HBox topBar;

    SceneController sceneController = new SceneController();

    public void onUpdate() throws SQLException, IOException {
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
                System.out.println("files updates");
                dataParser.trimCSV();
                System.out.println("CSV trimmed");
                databaseUpdater.populateUpdateTime();
                System.out.println("update time populated");
                databaseUpdater.populateData();
                System.out.println("data populated");
                updateButton.setDisable(false);
                progressBar.setVisible(false);
                topBar.setDisable(false);
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
    }

    public void factionButton(ActionEvent e) throws IOException {
        sceneController.factionSelect(e);
        sceneController.buttonAction(e, "faction.fxml");
    }
}
