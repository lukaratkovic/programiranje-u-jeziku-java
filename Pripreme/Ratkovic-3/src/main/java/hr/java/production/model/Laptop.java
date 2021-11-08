package hr.java.production.model;

import java.math.BigDecimal;

public non-sealed class Laptop extends Item implements Technical {

    int warranty;

    public Laptop(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, int warranty) {
        super(name, category, width, height, length, productionCost, sellingPrice, discount);
        this.warranty = warranty;
    }

    @Override
    public int getWarranty() {
        return warranty;
    }
}
