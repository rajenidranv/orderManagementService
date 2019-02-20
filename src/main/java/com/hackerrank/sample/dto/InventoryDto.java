/**
 * 
 */
package com.hackerrank.sample.dto;

/**
 * @author rajen.venkatraman
 *
 */
public class InventoryDto {

	private Long skuId;

	private String productName;

	private String productLabel;

	private int inventoryOnHand;

	private int minQtyReq;

	private double price;

	public InventoryDto() {

	}

	public InventoryDto(Long skuId, String productName) {
		super();
		this.skuId = skuId;
		this.productName = productName;
	}

	/**
	 * @return the skuId
	 */
	public Long getSkuId() {
		return skuId;
	}

	/**
	 * @param skuId
	 *            the skuId to set
	 */
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName
	 *            the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the productLabel
	 */
	public String getProductLabel() {
		return productLabel;
	}

	/**
	 * @param productLabel
	 *            the productLabel to set
	 */
	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}

	/**
	 * @return the inventoryOnHand
	 */
	public int getInventoryOnHand() {
		return inventoryOnHand;
	}

	/**
	 * @param inventoryOnHand
	 *            the inventoryOnHand to set
	 */
	public void setInventoryOnHand(int inventoryOnHand) {
		this.inventoryOnHand = inventoryOnHand;
	}

	/**
	 * @return the minQtyReq
	 */
	public int getMinQtyReq() {
		return minQtyReq;
	}

	/**
	 * @param minQtyReq
	 *            the minQtyReq to set
	 */
	public void setMinQtyReq(int minQtyReq) {
		this.minQtyReq = minQtyReq;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

}
