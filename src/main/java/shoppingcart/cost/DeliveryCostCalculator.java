package shoppingcart.cost;

/**
 * 
 * @author Hakan
 *
 */
public interface DeliveryCostCalculator {

	double calculateFor(int numberOfDeliveries, int numberOfProducts);

}