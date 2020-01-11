package shoppingcart.base;

import lombok.Getter;

/**
 * 
 * @author Hakan
 *
 */
@Getter
public class Product {
	/**
	 * Title of the product
	 */
	private String title;
	/**
	 * Price of the product
	 */
	private double price;
	/**
	 * Product category
	 */
	private Category category;

	public Product(String title, double price, Category category) {
		super();
		this.title = title;
		this.price = price;
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product:" + title + ", Unit Price:" + String.format("%.2f", price) + "TL";
	}

}
