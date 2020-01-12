# Shopping Cart

Shopping cart project;

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
    
# Requirements
- Java 8
- Maven

# Building Project and Run Tests
```
mvn clean package
```
# Run Project 

java -jar ShoppingCart.jar in the `target` directory.

