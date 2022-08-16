package com.heb.jeff.vsc.util;

import com.heb.jeff.vsc.model.Cart;

import java.math.BigDecimal;

public class CartValidator {
    public static void validateCart(Cart shoppingCart) {
        if (shoppingCart.getItems() == null || shoppingCart.getItems().size() == 0) {
            throw new IllegalArgumentException("There are no items in the cart.");
        }
    }

    public static void validateItemPrice(BigDecimal itemPrice) {
        if (itemPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price can not be less than 0.");
        }
    }
}
