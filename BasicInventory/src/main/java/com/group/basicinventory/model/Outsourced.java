package com.group.basicinventory.model;

/**
 * Subclass of Part.java
 * An Outsourced part contains an additional field, the company name.
 * @author Nick Pantuso
 */
public class Outsourced extends Part {

    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     *
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }
}
