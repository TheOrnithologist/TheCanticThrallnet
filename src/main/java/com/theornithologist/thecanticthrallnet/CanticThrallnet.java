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
import java.util.Objects;


public class CanticThrallnet extends Application {

    String sceneRoot;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        File dir = new File(FileConstants.DataRoot());
        if (!dir.exists()) {
            boolean created = dir.mkdir();
            if (!created) {
                System.err.println("Directory creation failed.");
            }
        }
        try {
            PrintStream errorStream = new PrintStream(new FileOutputStream(FileConstants.DataRoot() + "log.txt"));
            System.setErr(errorStream);
            System.setOut(errorStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File firstLaunch = new File(FileConstants.DataRoot() + "launchCheck.txt");
        if (firstLaunch.exists()) {
            sceneRoot = "home.fxml";
        } else {
            sceneRoot = "initializer.fxml";
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(sceneRoot));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
        URL url = getClass().getResource("icons/CanticThrallnet256.png");
        Image icon = new Image(String.valueOf(url));
        scene.getStylesheets().add(Objects.requireNonNull(CanticThrallnet.class.getResource("styles.css")).toExternalForm());
        stage.setResizable(false);
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