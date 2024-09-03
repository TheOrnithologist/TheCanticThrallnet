package com.theornithologist.thecanticthrallnet.controllers;

import com.theornithologist.thecanticthrallnet.CanticThrallnet;
import com.theornithologist.thecanticthrallnet.factionobjects.ActiveFaction;
import com.theornithologist.thecanticthrallnet.factionobjects.datasheets.Datasheet;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

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
    @FXML
    BorderPane datasheetView;
    @FXML
    ListView<Datasheet> datasheetList;
    @FXML
    Label datasheetName;
    @FXML
    WebView datasheetLegend;
    @FXML
    Label datasheetRole;
    @FXML
    WebView datasheetTransport;
    @FXML
    WebView datasheetLeaderRule;
    @FXML
    Label datasheetWoundsRange;
    @FXML
    WebView datasheetWoundsRules;
    @FXML
    VBox dynamicDatasheetAbilities;
    @FXML
    HBox keywordBox;
    @FXML
    HBox leaderBox;
    @FXML
    Label leaderHeader;
    @FXML
    VBox modelStats;
    @FXML
    VBox cost;
    @FXML
    VBox options;
    @FXML
    VBox composition;
    @FXML
    VBox wargear;

    ActiveFaction activeFaction = ActiveFaction.getInstance();
    SceneController sceneController = new SceneController();

    public void initialize() throws SQLException, IOException {
        factionLabel.setText(activeFaction.getFactionName());
        activeFaction.updateData();
        armyRuleName.setText(activeFaction.getArmyRuleName());
        armyRuleBox.setText(activeFaction.getArmyRule());
        showDetachments();
        showStratagems();
        showEnhancements();
        showDatasheets();
    }

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

    public void stratagemListSelect() {
        Stratagem item = stratagemList.getSelectionModel().getSelectedItem();
        stratagemID.setText(item.getName());
        stratagemType.setText(item.getType());
        stratagemCost.setText("Cost: " + item.getCp_cost() + "CP");
        stratagemPhase.setText(item.getPhase());
        stratagemRules.setText(item.getDescription());
        stratagemLegend.setText(item.getLegend());
    }

    public void enhancementListSelect() {
        Enhancement item = enhancementList.getSelectionModel().getSelectedItem();
        enhancementName.setText(item.getName());
        enhancementCost.setText("Cost: " + item.getCost() + "pts");
        enhancementRules.setText(item.getDescription());
        stratagemLegend.setText(item.getLegend());
    }

    public void datasheetListSelect() throws IOException {
        Datasheet item = datasheetList.getSelectionModel().getSelectedItem();
        datasheetName.setText(item.getName());
        datasheetLegend.getEngine().loadContent(item.getLegend());
        datasheetRole.setText("Role: " + item.getRole());
        populateDatasheetAbilities(item);
        if (!item.getTransport().equals("")) {
            datasheetTransport.setVisible(true);
            datasheetTransport.getEngine().loadContent(item.getTransport());
        } else {
            datasheetTransport.setVisible(false);
        }
        if (!item.getLeaderFooter().equals("")) {
            datasheetLeaderRule.setVisible(true);
            datasheetLeaderRule.getEngine().loadContent(item.getLeaderFooter());
        } else {
            datasheetLeaderRule.setVisible(false);
        }
        if (!item.getDamagedw().equals("")) {
            datasheetWoundsRange.setVisible(true);
            datasheetWoundsRange.setText("Wounds " + item.getDamagedw());
        } else {
            datasheetWoundsRange.setVisible(false);
        }
        if (!item.getDamagedDescription().equals("")) {
            datasheetWoundsRules.setVisible(true);
            datasheetWoundsRules.getEngine().loadContent(item.getDamagedDescription());
        } else {
            datasheetWoundsRules.setVisible(false);
        }
        populateDatasheetKeywords(item);
        populateDatasheetLeader(item);
        populateDatasheetModels(item);
        populateDatasheetCost(item);
        populateDatasheetOptions(item);
        populateDatasheetComposition(item);
        populateDatasheetWargear(item);
    }

    public void populateDatasheetWargear(Datasheet datasheet) throws IOException {
        wargear.getChildren().clear();
        for (int i = 0; i < datasheet.getDatasheetWargears().size(); i++) {
            FXMLLoader loader = new FXMLLoader(CanticThrallnet.class.getResource("datasheetWargear.fxml"));
            Parent element = loader.load();
            DatasheetWargearController controller = loader.getController();
            controller.getWeaponName().setText(datasheet.getDatasheetWargears().get(i).getName());
            controller.getRange().setText(datasheet.getDatasheetWargears().get(i).getRange());
            controller.getAttacks().setText(datasheet.getDatasheetWargears().get(i).getAttacks());
            controller.getSkill().setText(datasheet.getDatasheetWargears().get(i).getAttackSkill());
            controller.getStrength().setText(datasheet.getDatasheetWargears().get(i).getStrength());
            controller.getArmorPiercing().setText(datasheet.getDatasheetWargears().get(i).getArmorPiercing());
            controller.getDamage().setText(datasheet.getDatasheetWargears().get(i).getDamage());
            controller.getModifiers().getEngine().loadContent(datasheet.getDatasheetWargears().get(i).getDescription());
            wargear.getChildren().add(element);
        }
    }

    public void populateDatasheetComposition(Datasheet datasheet) throws IOException {
        composition.getChildren().clear();
        for (int i = 0; i < datasheet.getDatasheetUnitCompositions().size(); i++) {
            FXMLLoader loader = new FXMLLoader(CanticThrallnet.class.getResource("datasheetUnitComposition.fxml"));
            Parent element = loader.load();
            DatasheetUnitCompositionController controller = loader.getController();
            controller.getCompositionOption().setText("-" + datasheet.getDatasheetUnitCompositions().get(i).getDescription());
            composition.getChildren().add(element);
        }
    }

    public void populateDatasheetOptions(Datasheet datasheet) throws IOException {
        options.getChildren().clear();
        for (int i = 0; i < datasheet.getDatasheetOptions().size(); i++) {
            FXMLLoader loader = new FXMLLoader(CanticThrallnet.class.getResource("datasheetOptions.fxml"));
            Parent element = loader.load();
            DatasheetOptionsController controller = loader.getController();
            controller.getButton().setText(datasheet.getDatasheetOptions().get(i).getButton());
            controller.getOptionDescription().setText(datasheet.getDatasheetOptions().get(i).getDescription());
            options.getChildren().add(element);
        }
    }

    public void populateDatasheetCost(Datasheet datasheet) throws IOException {
        cost.getChildren().clear();
        for (int i = 0; i < datasheet.getDatasheetModelCost().size(); i++) {
            FXMLLoader loader = new FXMLLoader(CanticThrallnet.class.getResource("datasheetModelCost.fxml"));
            Parent element = loader.load();
            DatasheetModelCostController controller = loader.getController();
            controller.getModelCount().setText(datasheet.getDatasheetModelCost().get(i).getDescription());
            controller.getModelCost().setText(datasheet.getDatasheetModelCost().get(i).getCost());
            cost.getChildren().add(element);
        }
    }

    public void populateDatasheetModels(Datasheet datasheet) throws IOException {
        modelStats.getChildren().clear();
        for (int i = 0; i < datasheet.getDatasheetModels().size(); i++) {
            FXMLLoader loader = new FXMLLoader(CanticThrallnet.class.getResource("datasheetModels.fxml"));
            Parent element = loader.load();
            DatasheetModelController controller = loader.getController();
            controller.getMovement().setText(datasheet.getDatasheetModels().get(i).getMovement());
            controller.getToughness().setText(datasheet.getDatasheetModels().get(i).getToughness());
            controller.getSave().setText(datasheet.getDatasheetModels().get(i).getSave());
            controller.getWounds().setText(datasheet.getDatasheetModels().get(i).getWounds());
            controller.getLeadership().setText(datasheet.getDatasheetModels().get(i).getLeadership());
            controller.getObjectiveControl().setText(datasheet.getDatasheetModels().get(i).getObjectiveControl());
            controller.getInvSave().setText(datasheet.getDatasheetModels().get(i).getInvulnerableSave());
            controller.getSaveDescription().setText(datasheet.getDatasheetModels().get(i).getInvulnerableSaveDesc());
            modelStats.getChildren().add(element);
        }
    }

    public void populateDatasheetLeader(Datasheet datasheet) throws IOException {
        leaderBox.getChildren().clear();
        if (datasheet.getDatasheetLeaders() == null) {
            leaderBox.setVisible(false);
            leaderHeader.setVisible(false);
        } else {
            leaderHeader.setVisible(true);
            leaderBox.setVisible(true);
            for (int i = 0; i < datasheet.getDatasheetLeaders().size(); i++) {
                FXMLLoader loader = new FXMLLoader(CanticThrallnet.class.getResource("datasheetLeaders.fxml"));
                Parent element = loader.load();
                DatasheetLeadersController controller = loader.getController();
                controller.getUnitName().setText(datasheet.getDatasheetLeaders().get(i).getName());
                leaderBox.getChildren().add(element);
            }
        }
    }

    public void populateDatasheetKeywords(Datasheet datasheet) throws IOException {
        keywordBox.getChildren().clear();
        for (int i = 0; i < datasheet.getDatasheetKeywords().size(); i++) {
            FXMLLoader loader = new FXMLLoader(CanticThrallnet.class.getResource("datasheetKeywords.fxml"));
            Parent element = loader.load();
            DatasheetKeywordController controller = loader.getController();
            controller.getKeyword().setText(datasheet.getDatasheetKeywords().get(i).getKeyword());
            keywordBox.getChildren().add(element);
        }
    }

    public void populateDatasheetAbilities(Datasheet datasheet) throws IOException {
        dynamicDatasheetAbilities.getChildren().clear();
        for (int i = 0; i < datasheet.getDatasheetAbilities().size(); i++) {
            FXMLLoader loader = new FXMLLoader(CanticThrallnet.class.getResource("datasheetAbilities.fxml"));
            Parent element = loader.load();
            DatasheetAbilityController controller = loader.getController();
            controller.getAbilityName().setText(datasheet.getDatasheetAbilities().get(i).getName());
            if (!datasheet.getDatasheetAbilities().get(i).getParameter().equals("")) {
                controller.getAbilityParameter().setText(datasheet.getDatasheetAbilities().get(i).getParameter());
                controller.abilityParameter.setVisible(true);
            }
            controller.getAbilityDescription().getEngine().loadContent(datasheet.getDatasheetAbilities().get(i).getDescription());
            dynamicDatasheetAbilities.getChildren().add(element);
        }
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
        datasheetView.setVisible(false);
    }

    public void armyRuleButton(ActionEvent e) {
        detachmentView.setVisible(false);
        armyView.setVisible(true);
        datasheetView.setVisible(false);
    }

    public void datasheetButton(ActionEvent e) {
        detachmentView.setVisible(false);
        armyView.setVisible(false);
        datasheetView.setVisible(true);
    }

    public void showDetachments() throws IOException {
        detachmentList.getItems().setAll(activeFaction.getDetachments());
        detachmentList.getSelectionModel().selectFirst();
        detachmentListSelect();
    }

    public void showDatasheets() throws IOException {
        datasheetList.getItems().setAll((activeFaction.getDatasheets()));
        datasheetList.getSelectionModel().selectFirst();
        datasheetListSelect();
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
