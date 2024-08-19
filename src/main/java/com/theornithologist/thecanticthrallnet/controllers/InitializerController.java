package com.theornithologist.thecanticthrallnet.controllers;

import com.theornithologist.thecanticthrallnet.datahandling.DataParser;
import com.theornithologist.thecanticthrallnet.datahandling.DatabaseUpdater;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

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
                databaseUpdater.generateDB();
                databaseUpdater.initializeTable();
                dataParser.trimCSV();
                databaseUpdater.populateData();
                return null;
            }
        };

        Task<Void> progress = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                  for (int i = 1; i <= 100; i++) {
                      updateProgress(i, 100);
                      Thread.sleep(1800);
                      if (i > 20 && !initialize.isRunning()) {
                          updateProgress(100, 100);
                          break;
                      }
                  }
                return null;
            }
        };
        progressBar.progressProperty().bind(progress.progressProperty());
        new Thread(progress).start();
        new Thread(initialize).start();
    }
}