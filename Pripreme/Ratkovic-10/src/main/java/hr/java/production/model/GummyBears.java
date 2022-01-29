package hr.java.production.model;

import java.math.BigDecimal;
import java.util.Objects;


/**
 * Represents an edible article (Gummy Bears)
 */
public class GummyBears extends Item implements Edible {

    BigDecimal weight;
    public static final int CALORIES_PER_KILOGRAM = 3960;

    /**
     * Constructor for GummyBears
     *
     * @param name           Gummy bears name
     * @param category       Gummy bears category
     * @param width          Gummy bears width
     * @param height         Gummy bears height
     * @param length         Gummy bears length
     * @param productionCost Gummy bears production cost
     * @param sellingPrice   Gummy bears selling price
     * @param discount       Gummy bears discount percentage (0-1)
     * @param weight         Gummy bears weight
     */
    public GummyBears(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, BigDecimal weight, Long id) {
        super(name, category, width, height, length, productionCost, sellingPrice, discount, id);
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
        if (!(o instanceof GummyBears)) return false;
        if (!super.equals(o)) return false;
        GummyBears that = (GummyBears) o;
        return Objects.equals(weight, that.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), weight);
    }
}
