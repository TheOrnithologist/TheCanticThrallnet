package com.theornithologist.thecanticthrallnet.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.web.WebView;

public class DatasheetAbilityController {

    @FXML
    TitledPane abilityName;
    @FXML
    Label abilityParameter;
    @FXML
    WebView abilityDescription;

    public TitledPane getAbilityName() {
        return abilityName;
    }

    public Label getAbilityParameter() {
        return abilityParameter;
    }

    public WebView getAbilityDescription() {
        return abilityDescription;
    }
}
