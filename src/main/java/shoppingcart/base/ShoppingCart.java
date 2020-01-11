package shoppingcart.base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.var;
import lombok.extern.slf4j.Slf4j;
import shoppingcart.cost.FixedDeliveryCostCalculator;

/**
 * 
 * @author Hakan
 *
 */
@Slf4j
public class ShoppingCart {

	private Map<Product, Integer> products;
	private Map<Category, Set<Product>> categoryProductMapping;
	private double campaignDiscount = 0;
	private double couponDiscount = 0;
	private Campaign appliedCampaign;
	private Coupon appliedCoupon;

	public ShoppingCart() {
		this.products = new HashMap<>();
		this.categoryProductMapping = new HashMap<>();
	}

	/**
	 * Apply Discounts for Shopping Cart
	 * 
	 * @param campaigns
	 */
	public void applyCampaigns(Campaign... campaigns) {
		double maximumDiscountApplied = 0;
		for (var campaign : campaigns) {
			log.info("Applying discounts campaign for category: {}, amount: {}, minimum amount: {} ",
					campaign.getCategory(), campaign.getDiscountAmount(), campaign.getMinimumAmount());
			Integer totalProductOfCategory = calculateTotalProductCountOfCategory(campaign.getCategory());
			Double totalProductPriceOfCategory = calculateTotalProductAmountOfCategory(campaign.getCategory());
			// If the discount applicable for this campaign, check maximum discount applied
			log.info("Total product count {} and total product price is {} for this campaign in shopping cart.",
					totalProductOfCategory, totalProductPriceOfCategory);
			if (campaign.isDiscountApplicable(totalProductOfCategory)
					&& campaign.getCalculatedDiscountAmount(totalProductPriceOfCategory) > maximumDiscountApplied) {
				maximumDiscountApplied = campaign.getCalculatedDiscountAmount(totalProductPriceOfCategory);
				log.info("Discount is {} for this shopping cart", maximumDiscountApplied);
				this.appliedCampaign = campaign;
			}
		}
		campaignDiscount = maximumDiscountApplied;
		log.info("Maximum discount is {} for this shopping cart", campaignDiscount);
	}

	/**
	 * Apply Coupon for Shopping Cart
	 * 
	 * @param coupon
	 */
	public void applyCoupon(Coupon coupon) {
		if (coupon.isDiscountApplicable(getTotalAmount() - campaignDiscount)) {
			couponDiscount = coupon.getCalculatedDiscountAmount(getTotalAmount() - campaignDiscount);
			log.info("Coupon {} applied!", coupon);
			appliedCoupon = coupon;
		} else {
			log.error("Coupon {} could not be apply for this shoppin cart!", coupon);
		}
	}

	/**
	 * Add product item to product map.
	 * 
	 * @param product
	 * @param productQuantity
	 */
	public void addItem(Product product, int productQuantity) {
		products.put(product, products.getOrDefault(product, 0) + productQuantity);
		Category category = product.getCategory();
		// Add product for owning category and if exists parent categories
		while (category != null) {
			Set<Product> pSet = categoryProductMapping.getOrDefault(category, new HashSet<>());
			pSet.add(product);
			categoryProductMapping.put(category, pSet);
			category = category.getParentCategory();
		}
		log.info("{} item(s) {} added to shopping cart!", productQuantity, product); 
	}

	/**
	 * Get total amount of products.
	 * 
	 * @return
	 */
	public double getTotalAmount() {
		return products.entrySet().stream().mapToDouble(order -> order.getKey().getPrice() * order.getValue()).sum();
	}

	/**
	 * Calculate total product amount of category
	 * 
	 * @param category
	 * @return
	 */
	public double calculateTotalProductAmountOfCategory(Category category) {
		return categoryProductMapping.get(category).stream().mapToDouble(m -> m.getPrice() * products.get(m)).sum();
	}

	/**
	 * Calculate total product count of the category
	 * 
	 * @param category
	 * @return
	 */
	public int calculateTotalProductCountOfCategory(Category category) {
		return categoryProductMapping.get(category).stream().mapToInt(p -> products.get(p)).sum();
	}

	/**
	 * Get total amount after campaign and coupon discounts.
	 * 
	 * @return
	 */
	public double getTotalAmountAfterDiscount() {
		return getTotalAmount() - campaignDiscount - couponDiscount;
	}

	public double getCouponDiscount() {
		return couponDiscount;
	}

	public double getCampaignDiscount() {
		return campaignDiscount;
	}

	/**
	 * Get delivery cost for this shopping cart.
	 * @return
	 */
	public double getDeliveryCost() {
		FixedDeliveryCostCalculator deliveryCostCalculator = new FixedDeliveryCostCalculator(5, 1);
		return deliveryCostCalculator.calculateFixedDeliveryCostForCart(this);
	}

	/**
	 * Get number of products in shopping cart.
	 * @return
	 */
	public int getNumberOfProducts() {
		return products.size();
	}

	/**
	 * Group cart products with their categories
	 * @return
	 */
	private Map<Category, List<Product>> groupCartProductsByCampaign() {
		return products.keySet().stream().collect(Collectors.groupingBy(Product::getCategory));
	}

	/**
	 * Get total quantity of products in shopping cart.
	 * @return
	 */
	public int getQuantityOfProducts() {
		return products.values().stream().mapToInt(Integer::intValue).sum();
	}

	/**
	 * Number of deliveries is number of distinct categories in shopping cart.
	 * 
	 * @return
	 */
	public int getNumberOfDeliveries() {
		return products.entrySet().stream().map(p -> p.getKey().getCategory()).collect(Collectors.toSet()).size();
	}

	/**
	 * Print final shopping cart detail.
	 * @return
	 */
	public String print() {
		StringBuilder sb = new StringBuilder();
		sb.append("SHOPPING CART (" + getQuantityOfProducts() + " Products)\n");
		groupCartProductsByCampaign().entrySet().forEach(a -> {
			sb.append("Category : " + a.getKey().getTitle());
			sb.append("\n");
			a.getValue().forEach(p -> sb.append("\t"+ p + " Quantity: " + products.get(p) + " Total Price: "
					+ String.format("%.2f", products.get(p) * p.getPrice()) + "TL\n"));
		});
		sb.append("ORDER SUMMARY\n");
		sb.append("Total Products : " + String.format("%.2f", getTotalAmount()) + "TL\n");
		if (appliedCampaign != null)
			sb.append("Get minimum " + appliedCampaign.getMinimumAmount() + " items in "
					+ appliedCampaign.getCategory().getTitle() + " category applied. Campaign discount : "
					+ String.format("%.2f", campaignDiscount) + "TL\n");
		if (appliedCoupon != null)
			sb.append("Coupon " + appliedCoupon.getDiscountBaseAmountDescription() + " for "
					+ String.format("%.2f", getTotalAmount() - campaignDiscount)
					+ " TL purhase applied. Coupon discount: " + String.format("%.2f", couponDiscount) + "TL\n");
		sb.append("Shipping Total:" + String.format("%.2f", getDeliveryCost()) + "TL\n");
		sb.append("Total Amount:" + String.format("%.2f", getTotalAmountAfterDiscount() + getDeliveryCost()) + "TL\n");
		return sb.toString();
	}

}
