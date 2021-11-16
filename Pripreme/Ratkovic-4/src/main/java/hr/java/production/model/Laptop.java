package hr.java.production.model;

import java.math.BigDecimal;


/**
 * Represents a Technical Item (Laptop)
 */
public non-sealed class Laptop extends Item implements Technical {

    int warranty;

    /**
     * Constructor for Laptop
     *
     * @param name           Laptop name
     * @param category       Laptop category
     * @param width          Laptop width
     * @param height         Laptop height
     * @param length         Laptop length
     * @param productionCost Laptop production cost
     * @param sellingPrice   Laptop selling price
     * @param discount       Laptop discount (0-1)
     * @param warranty       Laptop warranty in years
     */
    public Laptop(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, int warranty) {
        super(name, category, width, height, length, productionCost, sellingPrice, discount);
        this.warranty = warranty;
    }

    /**
     * Retrieves years of warranty
     *
     * @return years of warranty
     */
    @Override
    public int getWarranty() {
        return warranty;
    }
}
