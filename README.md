# Shopping Cart

Shopping cart project.

# Requirements
- Java 8
- Maven

# Building Project and Run Tests
```
mvn clean package
```
# Run Project 

java -jar ShoppingCart.jar in the `target` directory.

# Test Data
  <b>Categories;</b>\
    -Hygenie\
    -Food\
    -Kitchen (Is also parent category of food)\
  <b>Products;</b>\
    -Shampoo 500TL, Hygenie\
    -Tooth Paste 175TL, Hygenie\
    -Soap 12.5TL, Hygenie\
    -Apple 100TL, Food\
    -Almond 150TL, Food\
   <b>Campaigns;</b>\
    -Food minimum quantity 3, discount 70%\
    -Hygenie minimum quantity 5, discount 5%\
    -Food minimum quantity 4, discount 50TL\
    -Kitchen minimum quantity 10, discount 1100TL\
   <b>Coupon;</b>\
    -100TL min puchase amount 10% discount\
   <b>Delivery Cost;</b>\
    -2TL for per product, 1TL for per delivery, fixed cost 2.99TL\
   <b>Shopping Cart;</b>\
    -Apple 13, Almond 1, Shampoo 1, Tooth Paste 2, Soap 9\
    
 # Test Data Output   
 <b>SHOPPING CART (26 Products)</b>\
<b>Category : Food</b>\
        Product:Apple, Unit Price:100.00TL Quantity: 13 Total Price: 1300.00TL\
        Product:Almond, Unit Price:150.00TL Quantity: 1 Total Price: 150.00TL\
<b>Category : Hygiene</b>\
        Product:Tooth Paste, Unit Price:175.00TL Quantity: 2 Total Price: 350.00TL\
        Product:Soap, Unit Price:12.50TL Quantity: 9 Total Price: 112.50TL\
        Product:Shampoo, Unit Price:500.00TL Quantity: 1 Total Price: 500.00TL\
<b>ORDER SUMMARY</b>\
<b>Total Products :</b> 2412.50TL\
Get minimum 10 items in Kitchen category applied. Campaign discount : 1100.00TL\
Coupon 10.0% for 1312.50 TL purhase applied. Coupon discount: 131.25TL\
<b>Shipping Total:</b>14.99TL\
<b>Total Amount:</b>1196.24TL\

