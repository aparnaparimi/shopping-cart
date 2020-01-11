package shoppingcart.base;

import lombok.Getter;
import lombok.Setter;
import shoppingcart.enums.DiscountType;

/**
 * 
 * @author Hakan
 *
 */
@Getter
@Setter
public abstract class Discount {

	/** Discount amount */
	public double discountAmount;

	/**
	 * The minimum amount to have a discount, it can be quantity for Campaign or it
	 * can be purchase amount for Coupon.
	 */
	public int minimumAmount;

	/**
	 * Discount Type possible values are; RATE, AMOUNT.
	 */
	public DiscountType discountType;

	public Discount(double discountAmount, int minimumAmount, DiscountType discountType) {
		super();
		this.minimumAmount = minimumAmount;
		this.discountAmount = discountAmount;
		this.discountType = discountType;
	}

	/**
	 * Checks if discount applicable for given amount, amount should greater or
	 * equal than minimumAmount value for applicable
	 * 
	 * @param amount
	 * @return
	 */
	public boolean isDiscountApplicable(double amount) {
		return amount >= minimumAmount;
	}
	/**
	 * Get discount calculated amount of this discount
	 * @param totalAmount
	 * @return
	 */
	public double getCalculatedDiscountAmount(double totalAmount) {
		return discountType == DiscountType.RATE ? totalAmount * (discountAmount * 0.01) : discountAmount;
	}

	public String getDiscountBaseAmountDescription() {
		return discountType == DiscountType.RATE ? discountAmount + "%" : discountAmount + "TL";
	}
}
