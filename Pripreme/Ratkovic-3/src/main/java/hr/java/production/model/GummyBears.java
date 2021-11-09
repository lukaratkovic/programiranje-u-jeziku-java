package hr.java.production.model;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GummyBears extends Item implements Edible {

    BigDecimal weight;
    public static final int CALORIES_PER_KILOGRAM = 3960;
    private static final Logger logger = LoggerFactory.getLogger(GummyBears.class);

    public GummyBears(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, BigDecimal weight) {
        super(name, category, width, height, length, productionCost, sellingPrice, discount);
        this.weight = weight;
        logger.info("Object of class GummyBears was created");
    }

    @Override
    public int kcal() {
        return CALORIES_PER_KILOGRAM;
    }

    @Override
    public BigDecimal calculatePrice() {
        if (weight != null && sellingPrice != null)
            return weight.multiply(sellingPrice).multiply(new BigDecimal(1).subtract(discount.discountAmount()));
        else return new BigDecimal(0);
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal totalKCAL() {
        if (weight != null) return weight.multiply(new BigDecimal(kcal()));
        else return new BigDecimal(0);
    }

    public BigDecimal totalPrice() {
        if (weight != null && sellingPrice != null)
            return weight.multiply(sellingPrice);
        else return new BigDecimal(0);
    }
}
