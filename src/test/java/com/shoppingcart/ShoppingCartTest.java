package com.shoppingcart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import shoppingcart.base.Campaign;
import shoppingcart.base.Category;
import shoppingcart.base.Coupon;
import shoppingcart.base.Product;
import shoppingcart.base.ShoppingCart;
import shoppingcart.cost.FixedDeliveryCostCalculator;
import shoppingcart.enums.DiscountType;

public class ShoppingCartTest {

	private ShoppingCart cart;
	private Category hygiene;
	private Category kitchen;
	private Category food;
	private Product shampoo;
	private Product toothPaste;
	private Product apple;
	private Product almond;

	@Before
	public void initialize() {
		initializeCategory();
		initializeProducts();
		cart = new ShoppingCart(new FixedDeliveryCostCalculator(5, 1));
	}

	public void initializeCategory() {
		hygiene = new Category("Hygiene");
		kitchen = new Category("Kitchen");
		food = new Category("Food", kitchen);
	}

	public void initializeProducts() {
		shampoo = new Product("Shampoo", 500.0, hygiene);
		toothPaste = new Product("Tooth Paste", 175.0, hygiene);
		apple = new Product("Apple", 100.0, food);
		almond = new Product("Almond", 150.0, food);
	}

	@Test
	public void whenAddProductsToCartQuantityOfProductsThenShouldReturnTotalNumberOfProductsAdded() {
		cart.addItem(shampoo, 1);
		cart.addItem(toothPaste, 2);
		cart.addItem(shampoo, 3);
		assertEquals(6, cart.getQuantityOfProducts());
	}

	@Test
	public void whenAddProductsToCartNumberOfProductsThenShouldReturnNumberOfDifferentProductsInCart() {
		cart.addItem(shampoo, 1);
		cart.addItem(toothPaste, 2);
		cart.addItem(shampoo, 3);
		assertEquals(2, cart.getNumberOfProducts());
	}

	@Test
	public void whenAddProductsToCartNumberOfDeliveriesThenShouldReturnNumberOfDistinctCategoriesInCart() {
		cart.addItem(shampoo, 1);
		cart.addItem(toothPaste, 2);
		assertEquals(1, cart.getNumberOfDeliveries());
	}

	@Test
	public void whenCategoryHaveCampaignButCartNotEnoughProductToApplyThenThereShouldNoDiscount() {
		cart.addItem(shampoo, 1);
		cart.addItem(toothPaste, 2);
		Campaign hygenieCamp = new Campaign(hygiene, 5.0, 5, DiscountType.RATE);
		cart.applyCampaigns(hygenieCamp);
		assertEquals(850.0, cart.getTotalAmountAfterDiscount(), 0.001);
	}

	@Test
	public void whenCategoryHaveCampaignAndCartHasProductsThenThereShouldDiscount() {
		cart.addItem(shampoo, 1);
		cart.addItem(toothPaste, 2);
		Campaign hygenieCamp = new Campaign(hygiene, 5.0, 1, DiscountType.RATE);
		cart.applyCampaigns(hygenieCamp);

		assertEquals(42.50, cart.getCampaignDiscount(), 0.001);
		assertEquals(807.50, cart.getTotalAmountAfterDiscount(), 0.001);
	}

	@Test
	public void whenMoreThanOneCampaignGivenThenMaximumAmountDiscountShouldApply() {
		cart.addItem(almond, 30);
		cart.addItem(shampoo, 1);
		Campaign foodCamp = new Campaign(food, 70.0, 3, DiscountType.AMOUNT);
		Campaign hygenieCamp = new Campaign(hygiene, 5.0, 5, DiscountType.AMOUNT);
		cart.applyCampaigns(foodCamp, hygenieCamp);
		
		assertEquals(70.0, cart.getCampaignDiscount(), 0.001);
		assertEquals(foodCamp, cart.getAppliedCampaign());
		assertEquals(4930.0, cart.getTotalAmountAfterDiscount(), 0.001);
	}
	
	@Test
	public void whenProductCategoryDoesNotHaveCampaignButParentCategoryHaveThenItShouldAplyParentCategoryDiscount() {
		cart.addItem(almond, 30);
		Campaign kitchenCamp = new Campaign(kitchen, 70.0, 3, DiscountType.AMOUNT);
		cart.applyCampaigns(kitchenCamp);
		
		assertEquals(70.0, cart.getCampaignDiscount(), 0.001);
		assertEquals(kitchenCamp, cart.getAppliedCampaign());
		assertEquals(4430.0, cart.getTotalAmountAfterDiscount(), 0.001);
	}
	
	@Test
	public void whenCouponAppliedThenItShouldGiveDiscount() {
		cart.addItem(almond, 30);
		Coupon coupon = new Coupon(10, 10, DiscountType.RATE);
		cart.applyCoupon(coupon);
		
		assertEquals(450.0, cart.getCouponDiscount(), 0.001);
		assertEquals(4050.0, cart.getTotalAmountAfterDiscount(), 0.001);
	}
	
	@Test
	public void whenCartTotalAmountNotEnoughThenCouponShouldNotApply() {
		cart.addItem(almond, 30);
		Coupon coupon = new Coupon(10, 10000, DiscountType.RATE);
		cart.applyCoupon(coupon);
		
		assertNull(cart.getAppliedCoupon());
		assertEquals(4500.0, cart.getTotalAmountAfterDiscount(), 0.001);
	}
	
	@Test
	public void whenCampaignAndCouponAppliedThenShouldDiscount() {
		cart.addItem(almond, 30);
		Campaign foodCamp = new Campaign(food, 70.0, 3, DiscountType.AMOUNT);
		cart.applyCampaigns(foodCamp);
		
		Coupon coupon = new Coupon(10, 10, DiscountType.RATE);
		cart.applyCoupon(coupon);
		
		assertEquals(foodCamp, cart.getAppliedCampaign());
		assertEquals(coupon, cart.getAppliedCoupon());
		assertEquals(70.0, cart.getCampaignDiscount(), 0.001);
		assertEquals(443.0, cart.getCouponDiscount(), 0.001);
		assertEquals(3987.0, cart.getTotalAmountAfterDiscount(), 0.001);
	}
	
	@Test
	public void whenDeliveryCostCalculatedItShouldCountDistinctCategoriesInCart() {
		cart.addItem(almond, 30);
		cart.addItem(shampoo, 1);
		cart.addItem(toothPaste, 2);
        assertEquals(2, cart.getNumberOfDeliveries());
	}
	
	@Test
	public void whenGetDeliveryCostCalledThenItShouldGiveCalculatedDeliveryCost() {
		cart.addItem(almond, 30);
		cart.addItem(shampoo, 1);
		cart.addItem(toothPaste, 2);
        assertEquals(19.99, cart.getDeliveryCost(), 0.001);
	}
}
