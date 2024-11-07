module com.theornithologist.thecanticthrallnet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.commons.csv;
    requires java.desktop;
    requires javafx.web;
    requires org.apache.commons.io;


    opens com.theornithologist.thecanticthrallnet to javafx.fxml;
    exports com.theornithologist.thecanticthrallnet;
    exports com.theornithologist.thecanticthrallnet.controllers;
    opens com.theornithologist.thecanticthrallnet.controllers to javafx.fxml;
}