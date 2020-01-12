# Shopping Cart

Shopping cart project;

# Test Data
  Categories;
    -Hygenie    
    -Food
    -Kitchen (Is also parent category of food)
  Products;
    -Shampoo 500TL, Hygenie 
    -Tooth Paste 175TL, Hygenie 
    -Soap 12.5TL, Hygenie
    -Apple 100TL, Food 
    -Almond 150TL, Food 
   Campaigns;
    -Food minimum quantity 3, discount 70%
    -Hygenie minimum quantity 5, discount 5%
    -Food minimum quantity 4, discount 50TL
    -Kitchen minimum quantity 10, discount 1100TL 
   Coupon;
    -100TL min puchase amount 10% discount
   Delivery Cost;
    -2TL for per product, 1TL for per delivery, fixed cost 2.99TL
   Shopping Cart;
    -Apple 13, Almond 1, Shampoo 1, Tooth Paste 2, Soap 9
    
# Requirements
- Java 8
- Maven

# Building Project and Run Tests
```
mvn clean package
```
# Run Project 

java -jar ShoppingCart.jar in the `target` directory.

