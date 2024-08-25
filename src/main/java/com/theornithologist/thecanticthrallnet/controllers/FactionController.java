package com.theornithologist.thecanticthrallnet.controllers;

import com.theornithologist.thecanticthrallnet.factionobjects.ActiveFaction;
import com.theornithologist.thecanticthrallnet.factionobjects.Detachment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;

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
    ListView<Detachment> detachmentList;
    @FXML
    Label armyRuleName;
    @FXML
    TextArea armyRuleBox;
    @FXML
    Label factionLabel;
    @FXML
    VBox armyView;
    @FXML
    BorderPane detachmentView;
    ActiveFaction activeFaction = ActiveFaction.getInstance();

    public void initialize() throws SQLException {
        factionLabel.setText(activeFaction.getFactionName());
        activeFaction.updateData();
        armyRuleName.setText(activeFaction.getArmyRuleName());
        armyRuleBox.setText(activeFaction.getArmyRule());
        addDetachments();
    }

    SceneController sceneController = new SceneController();

    public void backButton(ActionEvent e) throws IOException {
        sceneController.buttonAction(e, "home.fxml");
    }

    public void detachmentButton(ActionEvent e) {
        armyView.setVisible(false);
        detachmentView.setVisible(true);
    }

    public void armyRuleButton(ActionEvent e) {
        detachmentView.setVisible(false);
        armyView.setVisible(true);
    }

    public void addDetachments() {
        detachmentList.getItems().setAll(activeFaction.getDetachments());
    }
}
