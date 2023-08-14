package com.group.basicinventory.controller;

import com.group.basicinventory.model.InHouse;
import com.group.basicinventory.model.Inventory;
import com.group.basicinventory.model.Outsourced;
import com.group.basicinventory.model.Part;
import com.group.basicinventory.app.InventoryApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the part form.
 * @author Nick Pantuso
 */
public class PartController implements Initializable {

    private static int partIdCounter = 0;
    @FXML private TextField idText = new TextField();
    @FXML private TextField nameText = new TextField();
    @FXML private TextField invText = new TextField();
    @FXML private TextField priceText = new TextField();
    @FXML private TextField maxText = new TextField();
    @FXML private TextField minText = new TextField();
    @FXML private TextField radioText = new TextField();
    @FXML private Label invError = new Label();
    @FXML private Label priceError = new Label();
    @FXML private Label maxMinError = new Label();
    @FXML private Label radioError = new Label();
    @FXML private Label emptyError = new Label();
    @FXML private Label partLabel = new Label();
    @FXML private RadioButton inHouseRadio = new RadioButton();
    @FXML private RadioButton outsourcedRadio = new RadioButton();
    @FXML private Label radioLabel = new Label();

    /**
     * Checks if a string is a number.
     * @param s the string to test
     * @return true if the string is a number, false if it is not.
     */
    public boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    /**
     * Resets the error labels to be blank.
     */
    public void resetErrors() {
        invError.setText("");
        maxMinError.setText("");
        priceError.setText("");
        radioError.setText("");
        emptyError.setText("");
    }

    /**
     * Checks error conditions for all TextFields.
     * @param pass is passed in as true
     * @return true if there were no errors, false otherwise
     */
    public boolean errorCheck(boolean pass) {
        if(isNumber(invText.getText()) && isNumber(maxText.getText()) && isNumber(minText.getText())) {
            int invVal = Integer.parseInt(invText.getText());
            int maxVal = Integer.parseInt(maxText.getText());
            int minVal = Integer.parseInt(minText.getText());
            if(minVal > maxVal) {
                maxMinError.setText("Min cannot be greater than Max, and Max cannot be smaller than Min.");
                pass = false;
            } else if(invVal > maxVal || invVal < minVal) {
                invError.setText("Inv must be between Max and Min.");
                pass = false;
            }
        } else {
            if(!isNumber(invText.getText())) {
                invError.setText("Inv must be a number.");
                pass = false;
            }
            if(!isNumber(maxText.getText()) || !isNumber(minText.getText())) {
                maxMinError.setText("Max and Min must be numbers.");
                pass = false;
            }
        }
        if(!isNumber(priceText.getText())) {
            priceError.setText("The Price must be a number.");
            pass = false;
        }
        if(inHouseRadio.isSelected()) {
            if(!isNumber(radioText.getText())) {
                radioError.setText("The Machine ID must be a number.");
                pass = false;
            }
        }
        if(nameText.getText().equals("") || invText.getText().equals("") || priceText.getText().equals("") || maxText.getText().equals("") || minText.getText().equals("") || radioText.getText().equals("")) {
            emptyError.setText("Make sure to fill out all text fields before submitting.");
            pass = false;
        }
        return pass;
    }

    /**
     * Changes the part form to an 'add part form' if the user clicked on the Add button in the main form.
     * RUNTIME ERROR: (NullPointerException) partLabel was null, forgot to instantiate.
     */
    @FXML protected void makeAddPartForm() {
        partLabel.setText("Add Part");
        idText.setText("Auto Gen - Disabled");
        nameText.setText("");
        invText.setText("");
        priceText.setText("");
        maxText.setText("");
        minText.setText("");
        radioText.setText("");
    }

    /**
     * Changes the part form to a 'modify part form' if the user clicked on the Modify button in the main form.
     * @param selectedPart the part the data is taken from
     */
    @FXML protected void makeModifyPartForm(Part selectedPart) {
        partLabel.setText("Modify Part");
        idText.setText(selectedPart.getId() + "");
        nameText.setText(selectedPart.getName() + "");
        invText.setText(selectedPart.getStock() + "");
        priceText.setText(selectedPart.getPrice() + "");
        maxText.setText(selectedPart.getMax() + "");
        minText.setText(selectedPart.getMin() + "");
        if(selectedPart instanceof InHouse) {
            inHouseRadio.setSelected(true);
            radioText.setText(((InHouse) selectedPart).getMachineId() + "");
        } else if(selectedPart instanceof Outsourced) {
            outsourcedRadio.setSelected(true);
            radioLabel.setText("Company Name");
            radioText.setText(((Outsourced) selectedPart).getCompanyName() + "");
        }
    }

    /**
     * Opens the main form.
     * @param event the event to retrieve a source from
     * @throws IOException
     */
    @FXML protected void openMainForm(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("/MainForm.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 996, 400);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * When the 'In-House' or 'Outsourced' radio button is selected, changes the bottom TextField in the part form.
     */
    @FXML protected void changeRadioLabel() {
        if(inHouseRadio.isSelected()) {
            radioLabel.setText("Machine ID");
        } else if(outsourcedRadio.isSelected()) {
            radioLabel.setText("Company Name");
        }
    }

    /**
     * When the save button is clicked, checks whether a part is being updated or added.
     * Opens the main form after the part has been dealt with.
     * @param event the event to send to openMainForm()
     */
    @FXML protected void savePart(ActionEvent event) {
        resetErrors();
        boolean pass = true;
        if(errorCheck(pass)) {
            if(partLabel.getText().equals("Add Part")) {
                addSaved();
            }
            if(partLabel.getText().equals("Modify Part")) {
                updateSaved();
            }
            try {
                openMainForm(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Creates a new part to be added to inventory.
     * ID is generated, not chosen by the user.
     * Creates either an In-House or an Outsourced part depending on what radio button is selected.
     * RUNTIME ERROR: (NumberFormatException) "Machine ID," one of the variables in the InHouse constructor was a label rather than the text from the text field.
     * RUNTIME ERROR: (NullPointerException) Inventory.allParts was null, forgot to instantiate.
     * RUNTIME ERROR: (NullPointerException) partsID was null, forgot to instantiate.
     */
    public void addSaved() {
        partIdCounter++;
        if(inHouseRadio.isSelected()) {
            InHouse ih = new InHouse(partIdCounter, nameText.getText(), Double.parseDouble(priceText.getText()),
                    Integer.parseInt(invText.getText()), Integer.parseInt(minText.getText()),
                    Integer.parseInt(maxText.getText()), Integer.parseInt(radioText.getText()));
            Inventory.addPart(ih);
        } else if(outsourcedRadio.isSelected()) {
            Outsourced os = new Outsourced(partIdCounter, nameText.getText(), Double.parseDouble(priceText.getText()),
                    Integer.parseInt(invText.getText()), Integer.parseInt(minText.getText()),
                    Integer.parseInt(maxText.getText()), radioText.getText());
            Inventory.addPart(os);
        }
    }

    /**
     * Updates the changed part to the inventory.
     * ID stays the same and cannot be changed.
     * Updates either an In-House or an Outsourced part depending on what radio button is selected.
     * RUNTIME ERROR: (NumberFormatException) Was using radioLabel and not radioText.
     */
    public void updateSaved() {
        if(inHouseRadio.isSelected()) {
            InHouse ih = new InHouse(Integer.parseInt(idText.getText()), nameText.getText(), Double.parseDouble(priceText.getText()),
                    Integer.parseInt(invText.getText()), Integer.parseInt(minText.getText()),
                    Integer.parseInt(maxText.getText()), Integer.parseInt(radioText.getText()));
            Inventory.updatePart(Integer.parseInt(idText.getText()), ih);
        } else if(outsourcedRadio.isSelected()) {
            Outsourced os = new Outsourced(Integer.parseInt(idText.getText()), nameText.getText(), Double.parseDouble(priceText.getText()),
                    Integer.parseInt(invText.getText()), Integer.parseInt(minText.getText()),
                    Integer.parseInt(maxText.getText()), radioText.getText());
            Inventory.updatePart(Integer.parseInt(idText.getText()), os);
        }
    }

    /**
     * Resets errors when the part form is first loaded.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetErrors();
    }
}
