package shoppingcart.cost;

import lombok.extern.slf4j.Slf4j;
import shoppingcart.base.ShoppingCart;

/**
 * Fixed Delivery Cost Calculator
 * 
 * @author Hakan
 *
 */
@Slf4j
public class FixedDeliveryCostCalculator implements DeliveryCostCalculator {
	
	private final double FIXED_COST = 2.99;
	/**
	 * Cost for per delivery
	 */
	private double costPerDelivery;
	/**
	 * Cost for per product
	 */
	private double costPerProduct;

	public FixedDeliveryCostCalculator(double costPerProduct, double costPerDelivery) {
		this.costPerProduct = costPerProduct;
		this.costPerDelivery = costPerDelivery;
	}

	public double calculateFixedDeliveryCostForCart(ShoppingCart cart) {
		return calculateFor(cart.getNumberOfDeliveries(), cart.getNumberOfProducts());
	}

	@Override
	public double calculateFor(int numberOfDeliveries, int numberOfProducts) {
		double result = 0;
		result = numberOfDeliveries * costPerDelivery + numberOfProducts * costPerProduct + FIXED_COST;
		log.info("Delivery cost calculated : {} for number of deliveries : {} and number of products : {} ", result,
				numberOfDeliveries, numberOfProducts);
		return result;
	}

}
