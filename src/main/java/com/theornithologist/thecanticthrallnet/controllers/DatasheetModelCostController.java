package com.theornithologist.thecanticthrallnet.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DatasheetModelCostController {

    @FXML
    Label modelCount;
    @FXML
    Label modelCost;

    public Label getModelCount() {
        return modelCount;
    }

    public Label getModelCost() {
        return modelCost;
    }
}
