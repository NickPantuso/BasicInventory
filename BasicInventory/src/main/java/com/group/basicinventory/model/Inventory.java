package com.group.basicinventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * An inventory holds an ObservableList of Parts and Products.
 * You can add, lookup, update, delete, and return parts and products from an inventory.
 * @author Nick Pantuso
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a newPart to allParts.
     * @param newPart the new part to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a newProduct to allProducts.
     * @param newProduct the new product to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Finds a Part in allParts based on a partId.
     * @param partId the search criteria
     * @return the part found, otherwise null
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Finds a Product in allProducts based on a productId.
     * @param productId the search criteria
     * @return the product found, otherwise null
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Finds a list of parts in allParts based on a partName.
     * @param partName the search criteria
     * @return an ObservableList of parts found
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                partsFound.add(part);
            }
        }
        return partsFound;
    }
    /**
     * Finds a list of products in allProducts based on a productName.
     * @param productName the search criteria
     * @return an ObservableList of products found
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                productsFound.add(product);
            }
        }
        return productsFound;
    }

    /**
     * Replaces a Part in allParts at the desired index with a newPart.
     * @param index the index to replace a part at
     * @param newPart the new part to replace the old part
     */
    public static void updatePart(int index, Part newPart) {
        for(int i = 0; i < allParts.size(); i++) {
            if(allParts.get(i).getId() == index) {
                allParts.set(i, newPart);
                return;
            }
        }
    }

    /**
     * Replaces a Product in allProducts at the desired index with a newProduct.
     * @param index the index to replace a product at
     * @param newProduct the new product to replace the old product
     */
    public static void updateProduct(int index, Product newProduct) {
        for(int i = 0; i < allProducts.size(); i++) {
            if(allProducts.get(i).getId() == index) {
                allProducts.set(i, newProduct);
                return;
            }
        }
    }

    /**
     * If selectedPart is found in allParts, it's removed.
     * @param selectedPart the part to delete
     * @return true if the selectedPart was removed, false if it was not
     */
    public static boolean deletePart(Part selectedPart) {  //WHAT TO DO IF WRONG?
        return allParts.remove(selectedPart);
    }

    /**
     * If selectedProduct is found in allProducts, it's removed.
     * @param selectedProduct the product to delete
     * @return true if the selectedProduct was removed, false if it was not
     */
    public static boolean deleteProduct(Product selectedProduct) {  //WHAT TO DO IF WRONG?
        return allProducts.remove(selectedProduct);
    }

    /**
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() { //IS THIS RIGHT?
        return allParts;
    }

    /**
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
