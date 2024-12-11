package com.theornithologist.thecanticthrallnet.controllers;

import com.theornithologist.thecanticthrallnet.CanticThrallnet;
import com.theornithologist.thecanticthrallnet.factionobjects.ActiveFaction;
import com.theornithologist.thecanticthrallnet.factionobjects.datasheets.Datasheet;
import com.theornithologist.thecanticthrallnet.factionobjects.detachments.Detachment;
import com.theornithologist.thecanticthrallnet.factionobjects.detachments.Enhancement;
import com.theornithologist.thecanticthrallnet.factionobjects.detachments.Stratagem;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;

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
    ListView<Detachment> detachmentList;
    @FXML
    Label armyRuleName;
    @FXML
    WebView armyRuleBox;
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
    WebView stratagemRules;
    @FXML
    ListView<Enhancement> enhancementList;
    @FXML
    Label enhancementName;
    @FXML
    Label enhancementCost;
    @FXML
    WebView enhancementRules;
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
    GridPane keywordBox;
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
    @FXML
    ImageView thrallnetImage;
    @FXML
    VBox stratagemHeader;
    @FXML
    VBox enhancementHeader;
    @FXML
    BorderPane spinner;

    ActiveFaction activeFaction = ActiveFaction.getInstance();
    SceneController sceneController = new SceneController();

    public void initialize() {
        spinner.setVisible(true);
        Task<Void> initTask = new Task<>() {
            @Override
            protected Void call() {
                try {
                    factionLabel.setText(activeFaction.getFactionName());
                    activeFaction.updateData();
                    armyRuleName.setText(activeFaction.getArmyRuleName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        };
        initTask.setOnSucceeded(event -> {
            try {
                armyRuleBox.getEngine().loadContent(activeFaction.getArmyRule());
                showDetachments();
                showStratagems();
                showEnhancements();
                showDatasheets();
            } catch (Exception e) {
                e.printStackTrace();
            }
            spinner.setVisible(false);
        });
        new Thread(initTask).start();
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
            detachmentController.getFactionAbilityText().getEngine().loadContent(item.getAbilities().get(i).getDescription());
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
        stratagemRules.getEngine().loadContent(item.getDescription());
        stratagemLegend.setText(item.getLegend());
    }

    public void enhancementListSelect() {
        Enhancement item = enhancementList.getSelectionModel().getSelectedItem();
        enhancementName.setText(item.getName());
        enhancementCost.setText("Cost: " + item.getCost() + "pts");
        enhancementRules.getEngine().loadContent(item.getDescription());
        enhancementLegend.setText(item.getLegend());
    }

    public void datasheetListSelect() throws IOException {
        Datasheet item = datasheetList.getSelectionModel().getSelectedItem();
        datasheetName.setText(item.getName());
        datasheetLegend.getEngine().loadContent(item.getLegend());
        datasheetRole.setText("Role: " + item.getRole());
        populateDatasheetAbilities(item);
        if (!item.getTransport().isEmpty()) {
            datasheetTransport.setVisible(true);
            datasheetTransport.setManaged(true);
            datasheetTransport.getEngine().loadContent(item.getTransport());
        } else {
            datasheetTransport.setVisible(false);
            datasheetTransport.setManaged(false);
        }
        if (!item.getLeaderFooter().isEmpty()) {
            datasheetLeaderRule.setVisible(true);
            datasheetLeaderRule.setManaged(true);
            datasheetLeaderRule.getEngine().loadContent(item.getLeaderFooter());
        } else {
            datasheetLeaderRule.setVisible(false);
            datasheetLeaderRule.setManaged(false);
        }
        if (!item.getDamagedw().isEmpty()) {
            datasheetWoundsRange.setVisible(true);
            datasheetWoundsRange.setManaged(true);
            datasheetWoundsRange.setText("Wounds " + item.getDamagedw());
        } else {
            datasheetWoundsRange.setVisible(false);
            datasheetWoundsRange.setManaged(false);
        }
        if (!item.getDamagedDescription().isEmpty()) {
            datasheetWoundsRules.setVisible(true);
            datasheetWoundsRules.setManaged(true);
            datasheetWoundsRules.getEngine().loadContent(item.getDamagedDescription());
        } else {
            datasheetWoundsRules.setVisible(false);
            datasheetWoundsRules.setManaged(false);
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
            if (datasheet.getDatasheetWargears().get(i).getDescription().isEmpty()) {
                controller.getModifiers().setManaged(false);
                controller.getModifiers().setVisible(false);
            } else {
                controller.getModifiers().setManaged(true);
                controller.getModifiers().setVisible(true);
                controller.getModifiers().getEngine().loadContent(datasheet.getDatasheetWargears().get(i).getDescription());
            }
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
            controller.getModelCount().setText(datasheet.getDatasheetModelCost().get(i).getDescription() + " ");
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
        if (datasheet.getDatasheetLeaders().isEmpty()) {
            leaderBox.setVisible(false);
            leaderBox.setManaged(false);
            leaderHeader.setVisible(false);
            leaderHeader.setManaged(false);
        } else {
            leaderHeader.setVisible(true);
            leaderHeader.setManaged(true);
            leaderBox.setVisible(true);
            leaderBox.setManaged(true);
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
            if (i < 5) {
                controller.getKeyword().setText(datasheet.getDatasheetKeywords().get(i).getKeyword());
                keywordBox.add(element, i, 0);
            } else {
                controller.getKeyword().setText(datasheet.getDatasheetKeywords().get(i).getKeyword());
                keywordBox.add(element, i - 5, 1);
            }

        }
    }

    public void populateDatasheetAbilities(Datasheet datasheet) throws IOException {
        dynamicDatasheetAbilities.getChildren().clear();
        for (int i = 0; i < datasheet.getDatasheetAbilities().size(); i++) {
            FXMLLoader loader = new FXMLLoader(CanticThrallnet.class.getResource("datasheetAbilities.fxml"));
            Parent element = loader.load();
            DatasheetAbilityController controller = loader.getController();
            controller.getAbilityName().setText(datasheet.getDatasheetAbilities().get(i).getName());
            if (!datasheet.getDatasheetAbilities().get(i).getParameter().isEmpty()) {
                controller.getAbilityParameter().setText("Parameter: " + datasheet.getDatasheetAbilities().get(i).getParameter());
                controller.abilityParameter.setVisible(true);
            }
            controller.getAbilityDescription().getEngine().loadContent(datasheet.getDatasheetAbilities().get(i).getDescription());
            dynamicDatasheetAbilities.getChildren().add(element);
        }
    }

    public void showStratagems() {
        Detachment detachment = detachmentList.getSelectionModel().getSelectedItem();
        stratagemList.getItems().setAll(detachment.getStratagems());
        stratagemList.getSelectionModel().selectFirst();
        stratagemListSelect();
    }

    public void showEnhancements() {
        Detachment detachment = detachmentList.getSelectionModel().getSelectedItem();
        enhancementList.getItems().setAll(detachment.getEnhancements());
        enhancementList.getSelectionModel().selectFirst();
        enhancementListSelect();
    }

    public void detachmentButton() {
        armyView.setVisible(false);
        detachmentView.setVisible(true);
        datasheetView.setVisible(false);
    }

    public void armyRuleButton() {
        detachmentView.setVisible(false);
        armyView.setVisible(true);
        datasheetView.setVisible(false);
    }

    public void datasheetButton() {
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
        stratagemHeader.setVisible(false);
        stratagemHeader.setManaged(false);
        enhancementView.setVisible(false);
        enhancementHeader.setVisible(false);
        enhancementHeader.setManaged(false);
        thrallnetImage.setVisible(true);
        thrallnetImage.setManaged(true);
        detachmentRulesView.toFront();
    }

    public void detachmentStratagemButton() {
        detachmentRulesView.setVisible(false);
        detachmentRulesView.setManaged(false);
        detachmentStratagemView.setVisible(true);
        stratagemHeader.setVisible(true);
        stratagemHeader.setManaged(true);
        enhancementView.setVisible(false);
        enhancementHeader.setVisible(false);
        enhancementHeader.setManaged(false);
        thrallnetImage.setVisible(false);
        thrallnetImage.setManaged(false);
        detachmentStratagemView.toFront();
    }

    public void detachmentEnhancementButton() {
        detachmentRulesView.setVisible(false);
        detachmentStratagemView.setVisible(false);
        stratagemHeader.setVisible(false);
        stratagemHeader.setManaged(false);
        enhancementView.setVisible(true);
        enhancementHeader.setVisible(true);
        enhancementHeader.setManaged(true);
        thrallnetImage.setVisible(false);
        thrallnetImage.setManaged(false);
        enhancementView.toFront();
    }
}
