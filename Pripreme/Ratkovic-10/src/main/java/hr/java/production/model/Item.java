package hr.java.production.model;

import java.math.BigDecimal;
import java.util.Objects;


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
    public Item(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, Long id) {
        super(name, id);
        this.category = category;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
    }

    public Item(String name, Long id, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice) {
        super(name, id);
        this.category = category;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.sellingPrice = sellingPrice;
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
     * Calculates article volume (width×height×length)
     *
     * @return
     */
    public BigDecimal getVolume() {
        return this.width.multiply(this.height.multiply(this.length));
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        if (!super.equals(o)) return false;
        Item item = (Item) o;
        return Objects.equals(category, item.category) && Objects.equals(width, item.width) && Objects.equals(height, item.height) && Objects.equals(length, item.length) && Objects.equals(productionCost, item.productionCost) && Objects.equals(sellingPrice, item.sellingPrice) && Objects.equals(discount, item.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), category, width, height, length, productionCost, sellingPrice, discount);
    }

    @Override
    public String toString() {
        return "Item{" +
                "category=" + category +
                ", width=" + width +
                ", height=" + height +
                ", length=" + length +
                ", productionCost=" + productionCost +
                ", sellingPrice=" + sellingPrice +
                ", discount=" + discount +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}