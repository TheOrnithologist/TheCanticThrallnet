package com.theornithologist.thecanticthrallnet.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.web.WebView;

public class DatasheetWargearController {

    @FXML
    TitledPane weaponName;
    @FXML
    Label range;
    @FXML
    Label attacks;
    @FXML
    Label skill;
    @FXML
    Label strength;
    @FXML
    Label armorPiercing;
    @FXML
    Label damage;
    @FXML
    WebView modifiers;

    public TitledPane getWeaponName() {
        return weaponName;
    }

    public Label getRange() {
        return range;
    }

    public Label getAttacks() {
        return attacks;
    }

    public Label getSkill() {
        return skill;
    }

    public Label getStrength() {
        return strength;
    }

    public Label getArmorPiercing() {
        return armorPiercing;
    }

    public Label getDamage() {
        return damage;
    }

    public WebView getModifiers() {
        return modifiers;
    }
}
