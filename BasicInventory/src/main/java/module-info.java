module com.group.basicinventory {
    requires java.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires transitive javafx.base;
    
    exports com.group.basicinventory.model;
    exports com.group.basicinventory.controller;
    exports com.group.basicinventory.app;
    opens com.group.basicinventory.model to javafx.fxml, javafx.graphics;
    opens com.group.basicinventory.controller to javafx.fxml, javafx.graphics;
    opens com.group.basicinventory.app to javafx.fxml, javafx.graphics;
}