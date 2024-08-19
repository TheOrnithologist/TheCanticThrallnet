package com.theornithologist.thecanticthrallnet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class CanticThrallnet extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(CanticThrallnet.class.getResource("initializer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 1000);
        URL url = getClass().getResource("icons/CanticThrallnet256.png");
        Image icon = new Image(String.valueOf(url));
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
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