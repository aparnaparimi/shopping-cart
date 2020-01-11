package com.shoppingcart;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import shoppingcart.base.Category;
import shoppingcart.base.Product;
import shoppingcart.base.ShoppingCart;

public class ShoppingCartTest {

	private ShoppingCart cart;
	private Category hygiene;
	private Category kitchen;
	private Category food;
	private Product shampoo;
	private Product toothPaste;
	@Before
	public void initialize() {

		hygiene = new Category("Hygiene");
		kitchen = new Category("Kitchen");
		food = new Category("Food", kitchen);

		cart = new ShoppingCart();

		shampoo = new Product("Shampoo", 500.0, hygiene);
		toothPaste = new Product("Tooth Paste", 175.0, hygiene);
		
	}
	@Test
	public void whenAddProductsToCartQuantityOfProductsShouldReturnTotalNumberOfProductsAdded() {
		cart.addItem(shampoo, 1);
		cart.addItem(toothPaste, 2);
		cart.addItem(shampoo, 3);
		assertEquals(6, cart.getQuantityOfProducts());
	}

	@Test
	public void whenAddProductsToCartNumberOfProductsShouldReturnNumberOfDifferentProductsInCart() {
		cart.addItem(shampoo, 1);
		cart.addItem(toothPaste, 2);
		cart.addItem(shampoo, 3);
		assertEquals(2, cart.getNumberOfProducts());
	}

	@Test
	public void whenAddProductsToCartNumberOfDeliveriesShouldReturnNumberOfDistinctCategoriesInCart() {
		cart.addItem(shampoo, 1);
		cart.addItem(toothPaste, 2);
		assertEquals(1, cart.getNumberOfDeliveries());
	}

}
