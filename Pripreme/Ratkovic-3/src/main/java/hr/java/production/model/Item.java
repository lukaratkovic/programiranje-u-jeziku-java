package hr.java.production.model;

import java.math.BigDecimal;


/**
 * Represents an article
 */
public class Item extends NamedEntity {
    Category category;
    BigDecimal width, height, length, productionCost, sellingPrice;
    Discount discount;

    /**
     * Constructor for Item
     *
     * @param name           Item name
     * @param category       Item category
     * @param width          Item width
     * @param height         Item height
     * @param length         Item length
     * @param productionCost Item production cost
     * @param sellingPrice   Item selling price
     * @param discount       Item discount
     */
    public Item(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount) {
        super(name);
        this.category = category;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(BigDecimal productionCost) {
        this.productionCost = productionCost;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    /**
     * Calculates article volume (width??height??length)
     *
     * @return
     */
    public BigDecimal getVolume() {
        return this.width.multiply(this.height.multiply(this.length));
    }
}