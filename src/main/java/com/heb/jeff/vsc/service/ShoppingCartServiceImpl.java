package com.heb.jeff.vsc.service;

import com.heb.jeff.vsc.model.Cart;
import com.heb.jeff.vsc.model.Item;
import com.heb.jeff.vsc.model.Receipt;
import com.heb.jeff.vsc.util.CartValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartInterface {
    final public BigDecimal TAX_RATE = BigDecimal.valueOf(0.0825);
    //If I were to build this feature so that it is connected to a database, I would
    //have a list of Coupon objects that I could query from the database inside a method. I would then
    //convert the list to a map utilizing Collections, and the outcome would be similar to COUPON_MAP below.
    //Since the coupons are constant for this feature, I am just initializing it here
    final public Map<Integer, BigDecimal> COUPON_MAP = Map.of(85294241, new BigDecimal("0.79"),
            61411728, new BigDecimal("1.01"),
            30532705, new BigDecimal("1.83"));

    @Override
    public Receipt calculateReceipt(Cart shoppingCart) {
        CartValidator.validateCart(shoppingCart);

        BigDecimal subTotal = BigDecimal.ZERO;
        BigDecimal taxableSubTotal = BigDecimal.ZERO;
        BigDecimal discountTotal = BigDecimal.ZERO;
        BigDecimal subTotalAfterDiscounts = BigDecimal.ZERO;

        for (Item item : shoppingCart.getItems()) {
            CartValidator.validateItemPrice(item.getPrice());
            subTotal = subTotal.add(item.getPrice());
            subTotalAfterDiscounts = subTotalAfterDiscounts.add(item.getPrice());
            taxableSubTotal = taxableSubTotal.add(item.isTaxable() ? item.getPrice() : BigDecimal.ZERO);
            BigDecimal itemDiscountAmount = COUPON_MAP.get(item.getSku());

            if (itemDiscountAmount != null) {
                BigDecimal discountAmount = item.getPrice().subtract(itemDiscountAmount)
                        .compareTo(BigDecimal.ZERO) < 0 ? item.getPrice() : itemDiscountAmount;
                discountTotal = discountTotal.add(discountAmount);
                subTotalAfterDiscounts = subTotalAfterDiscounts.subtract(discountAmount);
                taxableSubTotal = taxableSubTotal.subtract(item.isTaxable() ? discountAmount : BigDecimal.ZERO);
            }
        }

        //RoundingMode.HALF_EVEN represents banker's rounding which has the last bias
        BigDecimal taxTotal = taxableSubTotal.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal grandTotal = subTotalAfterDiscounts.add(taxTotal);

        return new Receipt(subTotal, discountTotal, subTotalAfterDiscounts, taxableSubTotal, taxTotal, grandTotal);
    }
}
