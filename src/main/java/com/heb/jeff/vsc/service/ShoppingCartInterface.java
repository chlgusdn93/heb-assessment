package com.heb.jeff.vsc.service;

import com.heb.jeff.vsc.model.Cart;
import com.heb.jeff.vsc.model.Receipt;

public interface ShoppingCartInterface {
    Receipt calculateReceipt(Cart shoppingCart);
}
