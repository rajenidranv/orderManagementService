package com.hackerrank.sample.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "order_line_item")
public class OrderLineItem {

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderLineItemId;

	private Long skuId;

	// @ManyToOne
	@JoinColumn(name = "order_id")
	private Long orderId;

	private int itemQty;

	public OrderLineItem() {

	}

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

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public int getItemQty() {
		return itemQty;
	}

	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}

}
