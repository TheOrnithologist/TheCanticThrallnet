package com.theornithologist.thecanticthrallnet.controllers;

import com.theornithologist.thecanticthrallnet.CanticThrallnet;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    Stage stage;
    Scene scene;

    public void setScene(ActionEvent e, String sceneString) throws IOException {
        Parent root = FXMLLoader.load(CanticThrallnet.class.getResource(sceneString));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root, 1000, 1000);
        scene.getStylesheets().add(CanticThrallnet.class.getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
