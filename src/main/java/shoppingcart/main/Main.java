package shoppingcart.main;

import lombok.extern.slf4j.Slf4j;
import shoppingcart.base.Campaign;
import shoppingcart.base.Category;
import shoppingcart.base.Coupon;
import shoppingcart.base.Product;
import shoppingcart.base.ShoppingCart;
import shoppingcart.cost.FixedDeliveryCostCalculator;
import shoppingcart.enums.DiscountType;

/**
 * @author Hakan
 */
@Slf4j
public class Main {

	public static void main(String[] args) {
		log.info(initializeShoppingCart().print());
	}

	public static ShoppingCart initializeShoppingCart() {

		Category hygiene = new Category("Hygiene");
		Category kitchen = new Category("Kitchen");
		Category food = new Category("Food", kitchen);

		Product shampoo = new Product("Shampoo", 500.0, hygiene);
		Product toothPaste = new Product("Tooth Paste", 175.0, hygiene);
		Product apple = new Product("Apple", 100.0, food);
		Product almond = new Product("Almond", 150.0, food);
		Product soap = new Product("Soap", 12.5, hygiene);
		
		ShoppingCart cart = new ShoppingCart(new FixedDeliveryCostCalculator(2, 1));
		cart.addItem(apple, 3);
		cart.addItem(almond, 1);
		cart.addItem(shampoo, 1);
		cart.addItem(toothPaste, 2);
		cart.addItem(soap, 9);
		cart.addItem(apple, 10);
		
		Campaign campaign1 = new Campaign(food, 70.0, 3, DiscountType.RATE);
		Campaign campaign2 = new Campaign(hygiene, 5.0, 5, DiscountType.RATE);
		Campaign campaign3 = new Campaign(food, 50.0, 4, DiscountType.AMOUNT);
		Campaign campaign4 = new Campaign(kitchen, 1100.0, 10, DiscountType.AMOUNT);
		cart.applyCampaigns(campaign1, campaign2, campaign3, campaign4);

		//Coupon 100TL min purchase amount 10% discount
		Coupon coupon = new Coupon(10, 100, DiscountType.RATE);
		cart.applyCoupon(coupon);
		return cart;
	}

}
