package com.theornithologist.thecanticthrallnet.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class FactionController {

    @FXML
    Button backButton;

    SceneController sceneController = new SceneController();

    public void backButton(ActionEvent e) throws IOException {
        sceneController.setScene(e, "home.fxml");
    }
}
