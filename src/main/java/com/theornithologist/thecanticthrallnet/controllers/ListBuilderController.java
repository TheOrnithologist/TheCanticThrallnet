package com.theornithologist.thecanticthrallnet.controllers;

import com.theornithologist.thecanticthrallnet.CanticThrallnet;
import com.theornithologist.thecanticthrallnet.datahandling.FactionIDConstants;
import com.theornithologist.thecanticthrallnet.datahandling.FactionQuery;
import com.theornithologist.thecanticthrallnet.datahandling.FileConstants;
import com.theornithologist.thecanticthrallnet.factionobjects.Roster;
import com.theornithologist.thecanticthrallnet.factionobjects.datasheets.Datasheet;
import com.theornithologist.thecanticthrallnet.factionobjects.detachments.Detachment;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListBuilderController {

    @FXML
    private Button backButton;
    @FXML
    private Button newButton;
    @FXML
    private TitledPane loadButton;
    @FXML
    private ListView<String> listOfRosters;
    @FXML
    private HBox listLoader;
    @FXML
    private VBox newRosterSelector;
    @FXML
    private TextField rosterName;
    @FXML
    private ComboBox<String> faction;
    @FXML
    private ComboBox<String> detachment;
    @FXML
    private ComboBox<String> battlePoints;
    @FXML
    private Button saveButton;
    @FXML
    private Label saveError;
    @FXML
    private BorderPane spinner;
    @FXML
    private Label activeListName;
    @FXML
    private BorderPane activeList;
    @FXML
    private StackPane listSelector;
    @FXML
    private ListView<Datasheet> datasheets;
    @FXML
    private ListView<Datasheet> datasheetsInRoster;
    @FXML
    private Label rosterFaction;
    @FXML
    private Label battleSize;
    @FXML
    private Label datasheetName;
    @FXML
    private VBox modelStats;
    @FXML
    private Label datasheetRole;
    @FXML
    private VBox wargear;
    @FXML
    private VBox dynamicDatasheetAbilities;
    @FXML
    private TitledPane leaderHeader;
    @FXML
    private VBox leaderBox;
    @FXML
    private WebView datasheetTransport;
    @FXML
    private WebView datasheetLeaderRule;
    @FXML
    private Label datasheetWoundsRange;
    @FXML
    private WebView datasheetWoundsRules;
    @FXML
    private VBox composition;
    @FXML
    private VBox options;
    @FXML
    private VBox cost;
    @FXML
    private GridPane keywordBox;
    @FXML
    private WebView datasheetLegend;
    @FXML
    private Button battleViewButton;
    @FXML
    private Button rosterSave;
    @FXML
    private Button addDatasheet;

    private Roster activeRoster;
    private List<Datasheet> activeDatasheets;
    private String url;
    private boolean listStarted = false;
    FactionQuery factionQuery = new FactionQuery();

    public void initialize() {
        Path rosterDirectory = new File(FileConstants.DataRoot() + "\\rosters").toPath().toAbsolutePath();
        boolean containsRosters = false;
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(rosterDirectory)) {
            containsRosters = dirStream.iterator().hasNext();
        } catch (Exception e) {
            System.err.println("Error checking for rosters");
        }
        if (!containsRosters) {
            loadButton.setDisable(true);
        }
        generateFactionList();
        battlePoints.getItems().addAll(
                "500",
                "750",
                "1000",
                "1500",
                "2000",
                "3000"
        );
        populateListOfRosters();
    }

    public void backButton(ActionEvent event) throws IOException {
        if (!listStarted) {
            SceneController sceneController = new SceneController();
            sceneController.buttonAction(event, "home.fxml");
        } else {
            listLoader.setVisible(true);
            listLoader.setManaged(true);
            newRosterSelector.setManaged(false);
            newRosterSelector.setVisible(false);
            activeList.setVisible(false);
            activeList.setManaged(false);
            Path rosterDirectory = new File(FileConstants.DataRoot() + "\\rosters").toPath().toAbsolutePath();
            boolean containsRosters = false;
            try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(rosterDirectory)) {
                containsRosters = dirStream.iterator().hasNext();
            } catch (Exception e) {
                System.err.println("Error checking for rosters");
            }
            if (!containsRosters) {
                loadButton.setDisable(true);
            }
            datasheets.getItems().clear();
            datasheetsInRoster.getItems().clear();
            listStarted = false;
        }
    }

    public void newRosterButton() {
        listLoader.setVisible(false);
        listLoader.setManaged(false);
        newRosterSelector.setVisible(true);
        newRosterSelector.setManaged(true);
        listStarted = true;
    }

    public void generateFactionList() {
        FactionIDConstants[] factionEnum = FactionIDConstants.values();
        for (FactionIDConstants f : factionEnum) {
            faction.getItems().add(f.value);
        }
    }

    public void factionSelect() throws SQLException {
        String item = faction.getValue();
        detachment.getItems().clear();
        System.out.println(item);
        ResultSet rs = factionQuery.getDetachment(FactionIDConstants.fromValue(item).toString());
        while(rs.next()) {
            detachment.getItems().add(rs.getString("detachment"));
        }
    }

    public void saveButton() throws SQLException {
        if (faction.getValue() == null || detachment.getValue() == null || rosterName.getText().isEmpty()) {
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            saveError.setVisible(true);
            pause.setOnFinished(event -> saveError.setVisible(false));
            pause.play();
        } else {
            System.out.println(faction.getValue());
            initializeNewRoster();
            setNewRosterData();
            ResultSet rs = loadRoster(rosterName.getText() + ".db");
            activeRoster = new Roster(rs.getString("name"), rs.getString("army"), Integer.parseInt(rs.getString("size")), new Detachment(rs.getString("detachment")));
            populateHeader();
            populateAvailableDatasheets();
            newRosterSelector.setVisible(false);
            newRosterSelector.setManaged(false);
            activeList.setVisible(true);
            activeList.setManaged(true);
            listStarted = true;
        }

    }

//    Need to add datasheet handling
    public void initializeNewRoster() {
        url = "jdbc:sqlite:" + FileConstants.DataRoot() + "\\rosters\\" + rosterName.getText() + ".db";
        try (var conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                var meta = conn.getMetaData();
                var stmt = conn.createStatement();
                stmt.execute("CREATE TABLE IF NOT EXISTS general(name TEXT, army TEXT, detachment TEXT, size TEXT);");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

//    Need to add datasheet handling
    public void setNewRosterData() {
        url = "jdbc:sqlite:" + FileConstants.DataRoot() + "\\rosters\\" + rosterName.getText() + ".db";
        String factionValue;
        if (faction.getValue().equals("T'au Empire")) {
            factionValue = "T''au Empire";
        } else {
            factionValue = faction.getValue();
        }
        String generalSQL = "INSERT INTO general(name, army, detachment, size) VALUES ('" + rosterName.getText() + "', '"  + factionValue + "', '" +
                detachment.getValue() + "', " + battlePoints.getValue() +");";
        System.out.println(generalSQL);
        try (var conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                var stmt = conn.createStatement();
                stmt.execute(generalSQL);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void populateListOfRosters() {
        File dir = new File(FileConstants.DataRoot() + "\\rosters");
        File[] fileList = dir.listFiles();
        List<String> rosterList = new ArrayList<>();
        assert fileList != null;
        for (File file : fileList) {
            rosterList.add(file.getName());
        }
        listOfRosters.getItems().setAll(rosterList);
    }

    public ResultSet loadRoster(String fileName) {
        url = "jdbc:sqlite:" +FileConstants.DataRoot() + "\\rosters\\" + fileName;
        String sql = "SELECT name, army, detachment, size FROM general";
        try {
            var conn = DriverManager.getConnection(url);
            var stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException er) {
            System.out.println(er.getMessage());
        }
        return null;
    }

    public void setActiveRoster(MouseEvent e) throws SQLException, IOException {
        ListView<String> lv = (ListView<String>)e.getSource();
        ResultSet rs = loadRoster(lv.getSelectionModel().getSelectedItem());
        activeRoster = new Roster(rs.getString("name"), rs.getString("army"), Integer.parseInt(rs.getString("size")), new Detachment(rs.getString("detachment")));
        populateHeader();
        populateAvailableDatasheets();
        listLoader.setManaged(false);
        listLoader.setVisible(false);
        activeList.setVisible(true);
        activeList.setManaged(true);
        datasheets.getSelectionModel().selectFirst();
        datasheetListSelect();
        listStarted = true;
    }

    public void populateAvailableDatasheets() throws SQLException {
        ResultSet rs = factionQuery.getDatasheet(FactionIDConstants.fromValue(activeRoster.getFactionName()).toString());
        activeDatasheets = new ArrayList<>();
        while (rs.next()) {
            activeDatasheets.add(new Datasheet(rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("legend"),
                    rs.getString("role"),
                    rs.getString("loadout"),
                    rs.getString("transport"),
                    rs.getString("virtual"),
                    rs.getString("leader_footer"),
                    rs.getString("damaged_w"),
                    rs.getString("damaged_description")));
        }
        datasheets.getItems().addAll(activeDatasheets);
    }

    public void populateHeader() {
        activeListName.setText(activeRoster.getRosterName());
        rosterFaction.setText(activeRoster.getFactionName());
        battleSize.setText(String.valueOf(activeRoster.getBattleSize()));
    }

    public void datasheetListSelect() throws IOException {
        Datasheet item = datasheets.getSelectionModel().getSelectedItem();
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
            if (datasheet.getDatasheetModels().size() > 1) {
                controller.getModelName().setText(datasheet.getDatasheetModels().get(i).getName());
            }
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

    public void addDatasheet() {
        Datasheet item = datasheets.getSelectionModel().getSelectedItem();

    }
}
