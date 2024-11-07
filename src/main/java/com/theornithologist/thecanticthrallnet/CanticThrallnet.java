package com.theornithologist.thecanticthrallnet;

import com.theornithologist.thecanticthrallnet.datahandling.FileConstants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.sql.SQLException;


public class CanticThrallnet extends Application {

    String sceneRoot;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        File dir = new File(FileConstants.DATA_ROOT.value);
        if (!dir.exists()) {
            dir.mkdir();
        }
        try {
            PrintStream errorStream = new PrintStream(new FileOutputStream(FileConstants.DATA_ROOT.value + "log.txt"));
            System.setErr(errorStream);
            System.setOut(errorStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File firstLaunch = new File(FileConstants.DATA_ROOT.value + "launchCheck.txt");
        if (firstLaunch.exists()) {
            sceneRoot = "home.fxml";
        } else {
            sceneRoot = "initializer.fxml";
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(sceneRoot));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        URL url = getClass().getResource("icons/CanticThrallnet256.png");
        Image icon = new Image(String.valueOf(url));
        scene.getStylesheets().add(CanticThrallnet.class.getResource("styles.css").toExternalForm());
        stage.getIcons().add(icon);
        scene.setFill(Color.SLATEGRAY);
        stage.setTitle("Cantic Thrallnet");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}