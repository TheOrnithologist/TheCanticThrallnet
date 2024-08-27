package com.theornithologist.thecanticthrallnet.controllers;

import com.theornithologist.thecanticthrallnet.CanticThrallnet;
import com.theornithologist.thecanticthrallnet.factionobjects.ActiveFaction;
import com.theornithologist.thecanticthrallnet.factionobjects.detachments.Detachment;
import com.theornithologist.thecanticthrallnet.factionobjects.detachments.Enhancement;
import com.theornithologist.thecanticthrallnet.factionobjects.detachments.Stratagem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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
    @FXML
    Label detachmentLabel;
    @FXML
    VBox dynamicVBox;
    @FXML
    Button detachmentRules;
    @FXML
    Button detachmentStratagems;
    @FXML
    StackPane detachmentRulesView;
    @FXML
    StackPane detachmentStratagemView;
    @FXML
    ListView<Stratagem> stratagemList;
    @FXML
    StackPane enhancementView;
    @FXML
    Label stratagemID;
    @FXML
    Label stratagemType;
    @FXML
    Label stratagemCost;
    @FXML
    Label stratagemPhase;
    @FXML
    TextArea stratagemRules;
    @FXML
    ListView<Enhancement> enhancementList;
    @FXML
    Label enhancementName;
    @FXML
    Label enhancementCost;
    @FXML
    TextArea enhancementRules;
    @FXML
    TextArea enhancementLegend;
    @FXML
    TextArea stratagemLegend;

    ActiveFaction activeFaction = ActiveFaction.getInstance();

    public void initialize() throws SQLException, IOException {
        factionLabel.setText(activeFaction.getFactionName());
        activeFaction.updateData();
        armyRuleName.setText(activeFaction.getArmyRuleName());
        armyRuleBox.setText(activeFaction.getArmyRule());
        showDetachments();
        showStratagems();
        showEnhancements();
    }

    SceneController sceneController = new SceneController();

    public void backButton(ActionEvent e) throws IOException {
        sceneController.buttonAction(e, "home.fxml");
    }

    public void detachmentListSelect() throws IOException {
        Detachment item = detachmentList.getSelectionModel().getSelectedItem();
        detachmentLabel.setText(item.getDetachmentName());
        dynamicVBox.getChildren().clear();
        for (int i = 0; i < item.getAbilities().size(); i++) {
            FXMLLoader loader = new FXMLLoader(CanticThrallnet.class.getResource("detachment.fxml"));
            Parent element = loader.load();
            DetachmentController detachmentController = loader.getController();
            detachmentController.getFactionAbilityName().setText(item.getAbilities().get(i).getName());
            detachmentController.getFactionAbilityText().setText(item.getAbilities().get(i).getDescription());
            detachmentController.getFactionAbilityLegend().setText(item.getAbilities().get(i).getLegend());
            dynamicVBox.getChildren().add(element);
        }
        showStratagems();
        showEnhancements();
    }

    public void stratagemListSelect() throws IOException {
        Stratagem item = stratagemList.getSelectionModel().getSelectedItem();
        stratagemID.setText(item.getName());
        stratagemType.setText(item.getType());
        stratagemCost.setText("Cost: " + item.getCp_cost() + "CP");
        stratagemPhase.setText(item.getPhase());
        stratagemRules.setText(item.getDescription());
        stratagemLegend.setText(item.getLegend());
    }

    public void enhancementListSelect() throws IOException {
        Enhancement item = enhancementList.getSelectionModel().getSelectedItem();
        enhancementName.setText(item.getName());
        enhancementCost.setText("Cost: " + item.getCost() + "pts");
        enhancementRules.setText(item.getDescription());
        stratagemLegend.setText(item.getLegend());
    }

    public void showStratagems() throws IOException {
        Detachment detachment = detachmentList.getSelectionModel().getSelectedItem();
        stratagemList.getItems().setAll(detachment.getStratagems());
        stratagemList.getSelectionModel().selectFirst();
        stratagemListSelect();
    }

    public void showEnhancements() throws IOException {
        Detachment detachment = detachmentList.getSelectionModel().getSelectedItem();
        enhancementList.getItems().setAll(detachment.getEnhancements());
        enhancementList.getSelectionModel().selectFirst();
        enhancementListSelect();
    }

    public void detachmentButton(ActionEvent e) {
        armyView.setVisible(false);
        detachmentView.setVisible(true);
    }

    public void armyRuleButton(ActionEvent e) {
        detachmentView.setVisible(false);
        armyView.setVisible(true);
    }

    public void showDetachments() throws IOException {
        detachmentList.getItems().setAll(activeFaction.getDetachments());
        detachmentList.getSelectionModel().selectFirst();
        detachmentListSelect();
    }

    public void detachmentRulesButton() {
        detachmentRulesView.setVisible(true);
        detachmentStratagemView.setVisible(false);
        enhancementView.setVisible(false);
        detachmentRulesView.toFront();
    }

    public void detachmentStratagemButton() {
        detachmentRulesView.setVisible(false);
        detachmentStratagemView.setVisible(true);
        enhancementView.setVisible(false);
        detachmentStratagemView.toFront();
    }

    public void detachmentEnhancementButton() {
        detachmentRulesView.setVisible(false);
        detachmentStratagemView.setVisible(false);
        enhancementView.setVisible(true);
        enhancementView.toFront();
    }
}
