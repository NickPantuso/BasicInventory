module com.group {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.group.qkm2app to javafx.fxml;
    exports com.group.qkm2app;
    opens com.group.controller to javafx.fxml;
    exports com.group.controller;
    opens com.group.model to javafx.fxml;
    exports com.group.model;
}