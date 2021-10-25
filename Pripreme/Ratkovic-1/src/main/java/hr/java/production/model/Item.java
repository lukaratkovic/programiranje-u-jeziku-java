package hr.java.production.model;

import java.math.BigDecimal;

public class Item {
    String name;
    Category category;
    BigDecimal width, height, length, productionCost, sellingPrice;
    Double poreznaStopa;

    public Item(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Double poreznaStopa) {
        this.name = name;
        this.category = category;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.sellingPrice = sellingPrice;
        this.poreznaStopa = poreznaStopa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return new BigDecimal(poreznaStopa).multiply(sellingPrice);
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Double getPoreznaStopa() {
        return poreznaStopa;
    }

    public void setPoreznaStopa(Double poreznaStopa) {
        this.poreznaStopa = poreznaStopa;
    }

    public BigDecimal getVolume() {
        return this.width.multiply(this.height.multiply(this.length));
    }
}