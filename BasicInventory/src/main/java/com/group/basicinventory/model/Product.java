package com.group.basicinventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A Product consists of an ObservableList of associatedParts, an id, name, price, stock, min, and max.
 * You can add and delete from associatedParts, as well as return the list in its entirety.
 * @author Nick Pantuso
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private boolean hasAssociated;

    public Product(int id, String name, double price, int stock, int min, int max, boolean hasAssociated) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.hasAssociated = hasAssociated;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param hasAssociated the hasAssociated to set
     */
    public void setHasAssociated(boolean hasAssociated) {this.hasAssociated = hasAssociated;}

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * @return hasAssociated
     */
    public boolean getHasAssociated() {return hasAssociated;}

    /**
     * Adds a Part to associatedParts.
     * @param part the part to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * If selectedAssociatedPart is found in associatedParts, it's removed.
     * @param selectedAssociatedPart the part to remove
     * @return true if the selected part was removed, false if it was not
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }


}
