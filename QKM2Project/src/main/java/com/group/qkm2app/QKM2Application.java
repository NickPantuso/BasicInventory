package com.group.qkm2app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Starts the application.
 * @author Nick Pantuso
 */
public class QKM2Application extends Application {

    /**
     * Opens the main form.
     * @param stage the stage to show
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(QKM2Application.class.getResource("/MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 996, 400);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Javadoc folder located in Project/Javadoc
     * FUTURE ENHANCEMENT: I think the products table in the main form should have an extra column that shows true/false
     * depending on if the product has an any associated parts. If this was implemented,
     * you wouldn't have to try deleting or modifying the product to see if there are parts.
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}