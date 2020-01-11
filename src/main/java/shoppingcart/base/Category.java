package shoppingcart.base;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Hakan
 *
 */
@Getter
@Setter
public class Category {

	private String title;
	private Category parentCategory;

	public Category(String title) {
		this.title = title;
	}

	public Category(String title, Category parentCategory) {
		this.title = title;
		this.parentCategory = parentCategory;
	}

	@Override
	public String toString() {
		return "Category:" + title;
	}

}
