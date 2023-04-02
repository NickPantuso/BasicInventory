package com.group.controller;

import com.group.model.*;
import com.group.qkm2app.QKM2Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controls the product form.
 * @author Nick Pantuso
 */
public class ProductController implements Initializable {

    private Part selectedPart;
    private static int productIdCounter = 999;
    @FXML private Label productLabel = new Label();
    @FXML private TextField idText = new TextField();
    @FXML private TextField nameText = new TextField();
    @FXML private TextField invText = new TextField();
    @FXML private TextField priceText = new TextField();
    @FXML private TextField maxText = new TextField();
    @FXML private TextField minText = new TextField();
    @FXML private TextField partSearch = new TextField();
    @FXML private Label partError = new Label();
    @FXML private Label associatedError = new Label();
    @FXML private Label invError = new Label();
    @FXML private Label priceError = new Label();
    @FXML private Label maxMinError = new Label();
    @FXML private Label emptyError = new Label();
    @FXML private TableView<Part> partsTable = new TableView<>();
    @FXML private TableColumn<Part, Integer> partsID = new TableColumn<>("Part ID");
    @FXML private TableColumn<Part, String> partsName = new TableColumn<>("Part Name");
    @FXML private TableColumn<Part, Integer> partsLvl = new TableColumn<>("Inventory Level");
    @FXML private TableColumn<Part, Double> partsPrice = new TableColumn<>("Price/Cost per Unit");
    @FXML private TableView<Part> associatedTable = new TableView<>();
    @FXML private TableColumn<Part, Integer> associatedID = new TableColumn<>("Part ID");
    @FXML private TableColumn<Part, String> associatedName = new TableColumn<>("Part Name");
    @FXML private TableColumn<Part, Integer> associatedLvl = new TableColumn<>("Inventory Level");
    @FXML private TableColumn<Part, Double> associatedPrice = new TableColumn<>("Price/Cost per Unit");

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
        emptyError.setText("");
        partError.setText("");
        associatedError.setText("");
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
        if(nameText.getText().equals("") || invText.getText().equals("") || priceText.getText().equals("") || maxText.getText().equals("") || minText.getText().equals("")) {
            emptyError.setText("Make sure to fill out all text fields before submitting.");
            pass = false;
        }
        return pass;
    }

    /**
     * Opens the main form.
     * @param event the event to retrieve a source from
     * @throws IOException
     */
    @FXML protected void openMainForm(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(QKM2Application.class.getResource("/MainForm.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 996, 400);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Changes the product form to an 'add product form' if the user clicked on the Add button in the main form.
     */
    @FXML protected void makeAddProductForm() {
        productLabel.setText("Add Product");
        idText.setText("Auto Gen - Disabled");
        nameText.setText("");
        invText.setText("");
        priceText.setText("");
        maxText.setText("");
        minText.setText("");
    }

    /**
     * Changes the product form to a 'modify product form' if the user clicked on the Modify button in the main form.
     * @param selectedProduct the product the data is taken from
     */
    @FXML protected void makeModifyProductForm(Product selectedProduct) {
        productLabel.setText("Modify Product");
        idText.setText(selectedProduct.getId() + "");
        nameText.setText(selectedProduct.getName() + "");
        invText.setText(selectedProduct.getStock() + "");
        priceText.setText(selectedProduct.getPrice() + "");
        maxText.setText(selectedProduct.getMax() + "");
        minText.setText(selectedProduct.getMin() + "");
        associatedTable.setItems(selectedProduct.getAllAssociatedParts());
    }

    /**
     * Searches the partsTable based on what's in the partSearch TextField.
     * If partSearch is empty, repopulates partsTable with entire inventory.
     */
    @FXML protected void searchParts() {
        partError.setText("");
        String txt = partSearch.getText();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        if (txt.equals("")) {
            partsTable.setItems(Inventory.getAllParts());
            return;
        }
        if(isNumber(txt)) {
            if(Inventory.lookupPart(Integer.parseInt(txt)) != null) {
                partsFound.add(Inventory.lookupPart(Integer.parseInt(txt)));
            }
        } else {
            partsFound.addAll(Inventory.lookupPart(txt));
        }
        if(partsFound.isEmpty()) {
            partError.setText("No parts found.");
        }
        partsTable.setItems(partsFound);
    }

    /**
     * Adds the selected part from the partsTable to associatedTable.
     * If there is no part selected, display an error.
     */
    @FXML protected void addAnAssociatedPart() {
        selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            partError.setText("You need to select a part to add first!");
        } else {
            partError.setText("");
            associatedTable.getItems().add(selectedPart);
        }

    }

    /**
     * When the remove button is clicked, opens a dialogue box confirming the deletion.
     * If the user confirms, the selected part is removed from the associatedTable.
     * If no part is selected when the remove button is clicked, notifies the user that nothing was changed.
     */
    @FXML protected void removeAnAssociatedPart() {
        selectedPart = associatedTable.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            associatedError.setText("You need to select a part to remove first!");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setContentText("Are you sure you want to remove this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                associatedError.setText("");
                associatedTable.getItems().remove(selectedPart);
            }
        }

    }

    /**
     * When the save button is clicked, checks whether a product is being updated or added.
     * Opens the main form after the product has been dealt with.
     * @param event the event to send to openMainForm()
     */
    @FXML protected void saveProduct(ActionEvent event) {
        resetErrors();
        boolean pass = true;
        if(errorCheck(pass)) {
            if(productLabel.getText().equals("Add Product")) {
                addSavedProduct();
            }
            if(productLabel.getText().equals("Modify Product")) {
                updateSavedProduct();
            }
            try {
                openMainForm(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Creates a new product to be added to inventory.
     * Adds all parts in the associatedTable to the associated parts list of the product.
     * ID is generated, not chosen by the user.
     */
    public void addSavedProduct() {
        productIdCounter++;
        Product product = new Product(productIdCounter, nameText.getText(), Double.parseDouble(priceText.getText()),
                Integer.parseInt(invText.getText()), Integer.parseInt(minText.getText()),
                Integer.parseInt(maxText.getText()));
        for(int i = 0; i < associatedTable.getItems().size(); i++) {
            product.addAssociatedPart(associatedTable.getItems().get(i));
        }
        Inventory.addProduct(product);
    }

    /**
     * Updates the changed product to the inventory.
     * Adds all parts in the associatedTable to the associated parts list of the product.
     * ID stays the same and cannot be changed.
     */
    public void updateSavedProduct() {
        Product product = new Product(Integer.parseInt(idText.getText()), nameText.getText(), Double.parseDouble(priceText.getText()),
                Integer.parseInt(invText.getText()), Integer.parseInt(minText.getText()),
                Integer.parseInt(maxText.getText()));
        for(int i = 0; i < associatedTable.getItems().size(); i++) {
            product.addAssociatedPart(associatedTable.getItems().get(i));
        }
        Inventory.updateProduct(Integer.parseInt(idText.getText()), product);
    }

    /**
     * Sets up both tables and resets error text when the product form is loaded.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());
        partsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        resetErrors();
    }
}
