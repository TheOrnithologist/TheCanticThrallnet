package com.theornithologist.thecanticthrallnet.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class DatasheetModelController {

    @FXML
    Label modelName;
    @FXML
    GridPane stats;
    @FXML
    Label saveDescription;
    @FXML
    Label movement;
    @FXML
    Label toughness;
    @FXML
    Label save;
    @FXML
    Label wounds;
    @FXML
    Label leadership;
    @FXML
    Label objectiveControl;
    @FXML
    Label invSave;

    public Label getModelName() {
        return modelName;
    }

    public GridPane getStats() {
        return stats;
    }

    public Label getSaveDescription() {
        return saveDescription;
    }

    public Label getMovement() {
        return movement;
    }

    public Label getToughness() {
        return toughness;
    }

    public Label getSave() {
        return save;
    }

    public Label getWounds() {
        return wounds;
    }

    public Label getLeadership() {
        return leadership;
    }

    public Label getObjectiveControl() {
        return objectiveControl;
    }

    public Label getInvSave() {
        return invSave;
    }
}
