package com.heb.jeff.vsc.model;

import java.math.BigDecimal;

public class Item {
    private String itemName;
    private Integer sku;
    private boolean isTaxable;
    private boolean ownBrand;
    private BigDecimal price;

    public Item() {
    }

    public Item(String itemName, Integer sku, boolean isTaxable, boolean ownBrand, BigDecimal price) {
        this.itemName = itemName;
        this.sku = sku;
        this.isTaxable = isTaxable;
        this.ownBrand = ownBrand;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getSku() {
        return sku;
    }

    public void setSku(Integer sku) {
        this.sku = sku;
    }

    public boolean isTaxable() {
        return isTaxable;
    }

    public void setIsTaxable(boolean isTaxable) {
        this.isTaxable = isTaxable;
    }

    public boolean isOwnBrand() {
        return ownBrand;
    }

    public void setOwnBrand(boolean ownBrand) {
        this.ownBrand = ownBrand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
