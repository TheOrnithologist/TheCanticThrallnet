package com.theornithologist.thecanticthrallnet.controllers;

import com.theornithologist.thecanticthrallnet.datahandling.ActiveFaction;
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
    Label armyRuleName;
    @FXML
    TextArea armyRuleBox;
    @FXML
    Label factionLabel;

    public void initialize() {
        ActiveFaction activeFaction = ActiveFaction.getInstance();
        factionLabel.setText(activeFaction.getFactionName());
        activeFaction.updateData();
        armyRuleName.setText(activeFaction.getArmyRuleName());
        armyRuleBox.setText(activeFaction.getArmyRule());
    }

    SceneController sceneController = new SceneController();

    public void backButton(ActionEvent e) throws IOException {
        sceneController.buttonAction(e, "home.fxml");
    }
}
