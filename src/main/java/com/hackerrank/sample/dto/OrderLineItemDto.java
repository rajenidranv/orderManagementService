/**
 * 
 */
package com.hackerrank.sample.dto;

/**
 * @author rajen.venkatraman
 *
 */
public class OrderLineItemDto {

	private Long orderLineItemId;

	private Long skuId;

	private int itemQty;

	private Long orderId;

	public Long getOrderLineItemId() {
		return orderLineItemId;
	}

	public void setOrderLineItemId(Long orderLineItemId) {
		this.orderLineItemId = orderLineItemId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public int getItemQty() {
		return itemQty;
	}

	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

}
