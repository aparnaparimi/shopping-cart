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
public class Coupon extends Discount {

	public Coupon(double amount, int minAmount, DiscountType discountType) {
		super(amount, minAmount, discountType);
	}

	@Override
	public String toString() {
		return "Coupon [discountAmount=" + discountAmount + ", minimumAmount=" + minimumAmount + ", discountType="
				+ discountType + "]";
	}

}
