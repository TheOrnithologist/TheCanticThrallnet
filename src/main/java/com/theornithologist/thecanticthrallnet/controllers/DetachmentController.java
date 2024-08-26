package com.theornithologist.thecanticthrallnet.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class DetachmentController {

    @FXML
    Label factionAbilityName;
    @FXML
    TextArea factionAbilityText;
    @FXML
    TextArea factionAbilityLegend;

    public Label getFactionAbilityName() {
        return factionAbilityName;
    }

    public TextArea getFactionAbilityText() {
        return factionAbilityText;
    }

    public TextArea getFactionAbilityLegend() {
        return factionAbilityLegend;
    }
}
