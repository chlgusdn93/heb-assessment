# Jeff Choi's Virtual Shopping Cart 

This service is written in Java utilizing Spring Boot. 
My preferred IDE is IntelliJ, but feel free to import this 
project into any IDE you prefer.

Once you import this project into an IDE, you can run 
`VscApplication` with your IDE (in Intellij, you can right click the file and there is an option to run)
and the service should start running.

You can go the manual route by compiling and running this
via your terminal using manual commands, but I'd recommend
utilizing your IDE.

Once you have the service running, you can simply call
`localhost:8080/shoppingCart/checkout` (I prefer through postman) 
as a POST request with the request body being formatted as 
`cart.json`.

Example:
```
{
  "items": [
    {
      "itemName": "H-E-B Two Bite Brownies",
      "sku": 85294241,
      "isTaxable": false,
      "ownBrand": true,
      "price": 3.61
    },
    {
      "itemName": "H-E-B Wild Caught Sockeye Salmon Fillet",
      "sku": 75821377,
      "isTaxable": true,
      "ownBrand": true,
      "price": 12.43
    }
  ]
}

```

Once you submit your request, your output will contain
a response with all 4 features implemented.

Example:
```
{
    "subTotal": 221.41,
    "discountTotal": 3.63,
    "subTotalAfterDiscounts": 217.78,
    "taxableSubTotalAfterDiscounts": 130.50,
    "taxTotal": 10.77,
    "grandTotal": 228.55
}
```

There is also a suite of unit test cases written in `com/heb/jeff/vsc/ShoppingCartServiceTest.java`.
