package hr.java.production.model;

import java.math.BigDecimal;

public class Fries extends Item implements Edible {
    BigDecimal weight;
    public static final int CALORIES_PER_KILOGRAM = 2740;

    public Fries(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, BigDecimal weight) {
        super(name, category, width, height, length, productionCost, sellingPrice);
        this.weight = weight;
    }

    @Override
    public int kcal() {
        return CALORIES_PER_KILOGRAM;
    }

    @Override
    public BigDecimal calculatePrice() {
        return getSellingPrice().multiply(weight);
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
