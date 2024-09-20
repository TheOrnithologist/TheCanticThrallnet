package com.theornithologist.thecanticthrallnet.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;

public class DetachmentController {

    @FXML
    Label factionAbilityName;
    @FXML
    WebView factionAbilityText;
    @FXML
    TextArea factionAbilityLegend;

    public Label getFactionAbilityName() {
        return factionAbilityName;
    }

    public WebView getFactionAbilityText() {
        return factionAbilityText;
    }

    public TextArea getFactionAbilityLegend() {
        return factionAbilityLegend;
    }
}
