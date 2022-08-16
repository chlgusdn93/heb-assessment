package com.heb.jeff.vsc.controller;

import com.heb.jeff.vsc.model.Cart;
import com.heb.jeff.vsc.model.Receipt;
import com.heb.jeff.vsc.service.ShoppingCartInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    private final ShoppingCartInterface shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartInterface shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping(value = "/checkout")
    public Receipt checkout(@RequestBody Cart ShoppingCart) {
        return shoppingCartService.calculateReceipt(ShoppingCart);
    }
}
