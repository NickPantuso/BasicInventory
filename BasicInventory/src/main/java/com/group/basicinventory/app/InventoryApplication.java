package com.group.basicinventory.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Starts the application.
 * @author Nick Pantuso
 */
public class InventoryApplication extends Application {

    /**
     * Opens the main form.
     * @param stage the stage to show
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("/MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 996, 400);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * UPDATE: The products table in the main form now has an extra column that displays true/false
     * depending on if the product has an any associated parts. This way,
     * you don't have to try deleting or modifying a product to see if there are associated parts.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}