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
public class Campaign extends Discount {

	/**
	 * Category of this campaign
	 */
	private Category category;

	public Campaign(Category category, double amount, int minQuantity, DiscountType discountType) {
		super(amount, minQuantity, discountType);
		this.category = category;
	}

	@Override
	public String toString() {
		return "Campaign Category:" + category + ", Discount Amount:" + discountAmount + ", Minimum Amount:"
				+ minimumAmount + ", Discount Type:" + discountType;
	}

}
