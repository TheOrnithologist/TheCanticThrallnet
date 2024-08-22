package com.theornithologist.thecanticthrallnet.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class FactionController {

    @FXML
    Button backButton;
    @FXML
    Button armyRuleButton;
    @FXML
    Button detachmentButton;
    @FXML
    Button datasheetButton;
    @FXML
    ListView listView;
    @FXML
    TextArea armyRuleBox;
    @FXML
    Label factionLabel;

    SceneController sceneController = new SceneController();

    public void backButton(ActionEvent e) throws IOException {
        sceneController.setScene(e, "home.fxml");
    }
}
