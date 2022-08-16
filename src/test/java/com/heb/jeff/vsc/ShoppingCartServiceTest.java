package com.heb.jeff.vsc;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.heb.jeff.vsc.model.Cart;
import com.heb.jeff.vsc.model.Item;
import com.heb.jeff.vsc.model.Receipt;
import com.heb.jeff.vsc.service.ShoppingCartInterface;
import com.heb.jeff.vsc.service.ShoppingCartServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ShoppingCartServiceImpl.class})
public class ShoppingCartServiceTest {
    @Autowired
    private ShoppingCartInterface shoppingCartService;

    @Test(expected = IllegalArgumentException.class)
    public void nullCartTest() {
        shoppingCartService.calculateReceipt(new Cart());
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyCartTest() {
        shoppingCartService.calculateReceipt(new Cart(new ArrayList<>()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativePriceTest() {
        Item negativeItem = new Item("Negative Item", 1, false, false, new BigDecimal("-1"));
        Cart cart = new Cart(Arrays.asList(negativeItem));
        shoppingCartService.calculateReceipt(cart);
    }

    @Test
    public void featureOneTest() {
        Item itemOne = new Item("Test Item One", 1, false, false, new BigDecimal("12.12"));
        Item itemTwo = new Item("Test Item Two", 2, false, false, new BigDecimal("3.44"));
        Item itemThree = new Item("Test Item Three", 3, false, false, new BigDecimal("14.95"));
        Item itemFour = new Item("Test Item Four", 4, false, false, new BigDecimal("1.99"));
        Cart cart = new Cart(Arrays.asList(itemOne, itemTwo, itemThree, itemFour));
        Receipt receipt = shoppingCartService.calculateReceipt(cart);
        assertEquals(new BigDecimal("32.50"), receipt.getGrandTotal());
    }

    @Test
    public void featureTwoTest() {
        Item itemOne = new Item("Test Item One", 1, true, false, new BigDecimal("12.12"));
        Item itemTwo = new Item("Test Item Two", 2, true, false, new BigDecimal("3.44"));
        Item itemThree = new Item("Test Item Three", 3, true, false, new BigDecimal("14.95"));
        Item itemFour = new Item("Test Item Four", 4, true, false, new BigDecimal("1.99"));
        Cart cart = new Cart(Arrays.asList(itemOne, itemTwo, itemThree, itemFour));
        Receipt receipt = shoppingCartService.calculateReceipt(cart);
        assertEquals(new BigDecimal("32.50"), receipt.getSubTotal());
        assertEquals(new BigDecimal("2.68"), receipt.getTaxTotal());
        assertEquals(new BigDecimal("35.18"), receipt.getGrandTotal());
    }

    @Test
    public void featureThreeTest() {
        Item itemOne = new Item("Test Item One", 1, true, false, new BigDecimal("12.12"));
        Item itemTwo = new Item("Test Item Two", 2, false, false, new BigDecimal("3.44"));
        Item itemThree = new Item("Test Item Three", 3, true, false, new BigDecimal("14.95"));
        Item itemFour = new Item("Test Item Four", 4, false, false, new BigDecimal("1.99"));
        Cart cart = new Cart(Arrays.asList(itemOne, itemTwo, itemThree, itemFour));
        Receipt receipt = shoppingCartService.calculateReceipt(cart);
        assertEquals(new BigDecimal("32.50"), receipt.getSubTotal());
        // no discounts
        assertEquals(new BigDecimal("27.07"), receipt.getTaxableSubTotalAfterDiscounts());
        assertEquals(new BigDecimal("2.23"), receipt.getTaxTotal());
        assertEquals(new BigDecimal("34.73"), receipt.getGrandTotal());
    }

    @Test
    public void featureFourTest() {
        Item itemOne = new Item("Test Item One", 61411728, true, false, new BigDecimal("12.12"));
        Item itemTwo = new Item("Test Item Two", 2, false, false, new BigDecimal("3.44"));
        Item itemThree = new Item("Test Item Three", 3, true, false, new BigDecimal("14.95"));
        Item itemFour = new Item("Test Item Four", 4, false, false, new BigDecimal("1.99"));
        Cart cart = new Cart(Arrays.asList(itemOne, itemTwo, itemThree, itemFour));
        Receipt receipt = shoppingCartService.calculateReceipt(cart);
        assertEquals(new BigDecimal("32.50"), receipt.getSubTotal());
        assertEquals(new BigDecimal("31.49"), receipt.getSubTotalAfterDiscounts());
        assertEquals(new BigDecimal("26.06"), receipt.getTaxableSubTotalAfterDiscounts());
        assertEquals(new BigDecimal("2.15"), receipt.getTaxTotal());
        assertEquals(new BigDecimal("33.64"), receipt.getGrandTotal());
    }

    @Test
    public void largeDiscountTest() {
        // test where discount exceeds an item's price
        Item itemOne = new Item("Test Item One", 61411728, true, false, new BigDecimal("0.99"));
        Item itemTwo = new Item("Test Item Two", 2, false, false, new BigDecimal("3.44"));
        Item itemThree = new Item("Test Item Three", 3, true, false, new BigDecimal("14.95"));
        Item itemFour = new Item("Test Item Four", 4, false, false, new BigDecimal("1.99"));
        Cart cart = new Cart(Arrays.asList(itemOne, itemTwo, itemThree, itemFour));
        Receipt receipt = shoppingCartService.calculateReceipt(cart);
        assertEquals(new BigDecimal("21.37"), receipt.getSubTotal());
        assertEquals(new BigDecimal("20.38"), receipt.getSubTotalAfterDiscounts());
        assertEquals(new BigDecimal("14.95"), receipt.getTaxableSubTotalAfterDiscounts());
        assertEquals(new BigDecimal("1.23"), receipt.getTaxTotal());
        assertEquals(new BigDecimal("21.61"), receipt.getGrandTotal());
    }

    @Test
    public void fullHEBTest() throws IOException {
        File file = new File(
                this.getClass().getClassLoader().getResource("json/cart.json").getFile()
        );
        ObjectMapper mapper = new ObjectMapper();
        Cart cart = mapper.readValue(file, Cart.class);
        Receipt receipt = shoppingCartService.calculateReceipt(cart);
        assertEquals(new BigDecimal("221.41"), receipt.getSubTotal());
        assertEquals(new BigDecimal("217.78"), receipt.getSubTotalAfterDiscounts());
        assertEquals(new BigDecimal("130.50"), receipt.getTaxableSubTotalAfterDiscounts());
        assertEquals(new BigDecimal("10.77"), receipt.getTaxTotal());
        assertEquals(new BigDecimal("228.55"), receipt.getGrandTotal());
    }
}
