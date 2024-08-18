module com.theornithologist.thecanticthrallnet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.commons.csv;


    opens com.theornithologist.thecanticthrallnet to javafx.fxml;
    exports com.theornithologist.thecanticthrallnet;
}