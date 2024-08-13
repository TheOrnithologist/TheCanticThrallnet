module com.theornithologist.thecanticthrallnet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.theornithologist.thecanticthrallnet to javafx.fxml;
    exports com.theornithologist.thecanticthrallnet;
}