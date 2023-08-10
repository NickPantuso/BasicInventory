module com.group.basicinventory {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.group.basicinventory.model;
    opens com.group.basicinventory.model to javafx.fxml;
    exports com.group.basicinventory.controller;
    opens com.group.basicinventory.controller to javafx.fxml;
    exports com.group.basicinventory.app;
    opens com.group.basicinventory.app to javafx.fxml;
}