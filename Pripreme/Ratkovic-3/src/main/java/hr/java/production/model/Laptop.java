package hr.java.production.model;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public non-sealed class Laptop extends Item implements Technical {

    int warranty;
    private static final Logger logger = LoggerFactory.getLogger(Category.class);

    public Laptop(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, int warranty) {
        super(name, category, width, height, length, productionCost, sellingPrice, discount);
        this.warranty = warranty;
        logger.info("Object of class Laptop was created");
    }

    @Override
    public int getWarranty() {
        return warranty;
    }
}
