package com.group.basicinventory.controller;

import com.group.basicinventory.model.Inventory;
import com.group.basicinventory.model.Part;
import com.group.basicinventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controls the main form.
 * @author Nick Pantuso
 */
public class MainController implements Initializable {

    private Stage stage;
    private Scene scene;
    private FXMLLoader loader;
    private Part selectedPart;
    private Product selectedProduct;
    @FXML private TextField partSearch = new TextField();
    @FXML private TableView<Part> partsTable = new TableView<>();
    @FXML private TableColumn<Part, Integer> partsID = new TableColumn<>();
    @FXML private TableColumn<Part, String> partsName = new TableColumn<>();
    @FXML private TableColumn<Part, Integer> partsLvl = new TableColumn<>();
    @FXML private TableColumn<Part, Double> partsPrice = new TableColumn<>();
    @FXML private TextField productSearch = new TextField();
    @FXML private TableView<Product> productsTable = new TableView<>();
    @FXML private TableColumn<Product, Integer> productsID = new TableColumn<>();
    @FXML private TableColumn<Product, String> productsName = new TableColumn<>();
    @FXML private TableColumn<Product, Integer> productsLvl = new TableColumn<>();
    @FXML private TableColumn<Product, Double> productsPrice = new TableColumn<>();
    @FXML private TableColumn<Product, Boolean> productsAssociated = new TableColumn<>();
    @FXML private Label partError = new Label();
    @FXML private Label productError = new Label();

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
     * Opens the add part form or modify part form depending on what button is pressed.
     * If the user hasn't selected a part from the table, the modify button will display an error.
     * RUNTIME ERROR: (IllegalStateException) Location not set, wrong class trying to access the resource.
     * @param event the event to retrieve a source from
     * @throws IOException
     */
    @FXML protected void openPartForm(ActionEvent event) throws IOException {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/PartForm.fxml"));
        loader.load();

        selectedPart = partsTable.getSelectionModel().getSelectedItem();
        Button b = (Button) event.getSource();
        String bId = b.getId();
        PartController pc = loader.getController();
        if (bId.equals("addPart")) {
            pc.makeAddPartForm();
        } else if(bId.equals("modifyPart")) {
            if(selectedPart == null) {
                partError.setText("You need to select a part to modify first!");
                return;
            } else {
                pc.makeModifyPartForm(selectedPart);
            }
        }

        stage = (Stage)(b).getScene().getWindow();
        Parent root = loader.getRoot();
        scene = new Scene(root, 600, 628);
        stage.setTitle("Part Form");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Opens the add product form or modify product form depending on what button is pressed.
     * If the user hasn't selected a product from the table, the modify button will display an error.
     * RUNTIME ERROR: (IllegalStateException) Location not set, I named the file "productform," but was trying to find "productForm".
     * @param event the event to retrieve a source from
     * @throws IOException
     */
    @FXML protected void openProductForm(ActionEvent event) throws IOException {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ProductForm.fxml"));
        loader.load();

        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        Button b = (Button) event.getSource();
        String bId = b.getId();
        ProductController pc = loader.getController();
        if (bId.equals("addProduct")) {
            pc.makeAddProductForm();
        } else if(bId.equals("modifyProduct")) {
            if(selectedProduct == null) {
                productError.setText("You need to select a product to modify first!");
                return;
            } else {
                pc.makeModifyProductForm(selectedProduct);
            }
        }

        stage = (Stage)(b).getScene().getWindow();
        Parent root = loader.getRoot();
        scene = new Scene(root, 871, 527);
        stage.setTitle("Product Form");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Searches the partsTable based on what's in the partSearch TextField.
     * If partSearch is empty, repopulates partsTable with entire inventory.
     * RUNTIME ERROR: (NullPointerException) Deleting a searched part and then searching for the part that was just deleted created an exception.
     * Solved by changing how the CellValueFactory is set in the initialize method.
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
     * Searches the productsTable based on what's in the productSearch TextField.
     * If productSearch is empty, repopulates productsTable with entire inventory.
     */
    @FXML protected void searchProducts() {
        productError.setText("");
        String txt = productSearch.getText();
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        if (txt.equals("")) {
            productsTable.setItems(Inventory.getAllProducts());
            return;
        }
        if (isNumber(txt)) {
            if (Inventory.lookupProduct(Integer.parseInt(txt)) != null) {
                productsFound.add(Inventory.lookupProduct(Integer.parseInt(txt)));
            }
        } else {
            productsFound.addAll(Inventory.lookupProduct(txt));
        }
        if (productsFound.isEmpty()) {
            productError.setText("No products found.");
        }
        productsTable.setItems(productsFound);
    }

    /**
     * When the delete button is clicked, opens a dialogue box confirming the deletion.
     * If the user confirms, the selected part is deleted from the partsTable and parts inventory.
     * If no part is selected when the delete button is clicked, notifies the user that nothing was changed.
     */
    @FXML protected void deleteAPart() {
        selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            partError.setText("You need to select a part to delete first!");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setContentText("Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                partError.setText("");
                Inventory.deletePart(selectedPart);
                partsTable.setItems(Inventory.getAllParts());
            }
        }
    }

    /**
     * When the delete button is clicked, opens a dialogue box confirming the deletion.
     * If the user confirms, the selected product is deleted from the productsTable and products inventory.
     * If no product is selected when the delete button is clicked, notifies the user that nothing was changed.
     */
    @FXML protected void deleteAProduct() {
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) {
            productError.setText("You need to select a product to delete first!");
        } else if(selectedProduct.getAllAssociatedParts().size() != 0) {
            productError.setText("Can't delete products that have associated parts.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setContentText("Are you sure you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                productError.setText("");
                Inventory.deleteProduct(selectedProduct);
                productsTable.setItems(Inventory.getAllProducts());
            }
        }
    }

    /**
     * Exits the application.
     */
    @FXML protected void onExitClick() {
        javafx.application.Platform.exit();
    }

    /**
     * Sets up both tables and resets error text when the main form is loaded.
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
        productsTable.setItems(Inventory.getAllProducts());
        productsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsAssociated.setCellValueFactory(new PropertyValueFactory<>("hasAssociated"));
        partError.setText("");
        productError.setText("");

    }
}