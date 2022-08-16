package com.heb.jeff.vsc.model;

import java.math.BigDecimal;

public class Receipt {
    private BigDecimal subTotal;
    private BigDecimal discountTotal;
    private BigDecimal subTotalAfterDiscounts;
    private BigDecimal taxableSubTotalAfterDiscounts;
    private BigDecimal taxTotal;
    private BigDecimal grandTotal;

    public Receipt() {
    }

    public Receipt(BigDecimal subTotal, BigDecimal discountTotal, BigDecimal subTotalAfterDiscounts,
                   BigDecimal taxableSubTotalAfterDiscounts, BigDecimal taxTotal, BigDecimal grandTotal) {
        this.subTotal = subTotal;
        this.discountTotal = discountTotal;
        this.subTotalAfterDiscounts = subTotalAfterDiscounts;
        this.taxableSubTotalAfterDiscounts = taxableSubTotalAfterDiscounts;
        this.taxTotal = taxTotal;
        this.grandTotal = grandTotal;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(BigDecimal discountTotal) {
        this.discountTotal = discountTotal;
    }

    public BigDecimal getSubTotalAfterDiscounts() {
        return subTotalAfterDiscounts;
    }

    public void setSubTotalAfterDiscounts(BigDecimal subTotalAfterDiscounts) {
        this.subTotalAfterDiscounts = subTotalAfterDiscounts;
    }

    public BigDecimal getTaxableSubTotalAfterDiscounts() {
        return taxableSubTotalAfterDiscounts;
    }

    public void setTaxableSubTotalAfterDiscounts(BigDecimal taxableSubTotalAfterDiscounts) {
        this.taxableSubTotalAfterDiscounts = taxableSubTotalAfterDiscounts;
    }

    public BigDecimal getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(BigDecimal taxTotal) {
        this.taxTotal = taxTotal;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }
}
