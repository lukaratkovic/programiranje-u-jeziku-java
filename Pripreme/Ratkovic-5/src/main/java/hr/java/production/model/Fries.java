package hr.java.production.model;

import java.math.BigDecimal;
import java.util.Objects;


/**
 * Represents an edible article (Fries)
 */
public class Fries extends Item implements Edible {
    BigDecimal weight;
    public static final int CALORIES_PER_KILOGRAM = 2740;

    /**
     * Constructor for Fries
     *
     * @param name           Fries name
     * @param category       Fries category
     * @param width          Fries width
     * @param height         Fries height
     * @param length         Fries length
     * @param productionCost Fries production cost
     * @param sellingPrice   Fries selling price
     * @param discount       Fries discount
     * @param weight         Fries weight
     */
    public Fries(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, BigDecimal weight) {
        super(name, category, width, height, length, productionCost, sellingPrice, discount);
        this.weight = weight;
    }

    /**
     * Retrieves number of kilocalories
     *
     * @return kilocalories
     */
    @Override
    public int kcal() {
        return CALORIES_PER_KILOGRAM;
    }

    /**
     * Retrieves total calculated price
     *
     * @return calculated price
     */
    @Override
    public BigDecimal calculatePrice() {
        return weight.multiply(sellingPrice).multiply(new BigDecimal(1).subtract(discount.discountAmount()));
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * Calculates total amount of kilocalories based on weight
     *
     * @return total kilocalories
     */
    public BigDecimal totalKCAL() {
        return weight.multiply(new BigDecimal(kcal()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fries)) return false;
        if (!super.equals(o)) return false;
        Fries fries = (Fries) o;
        return Objects.equals(weight, fries.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), weight);
    }
}
