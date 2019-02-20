/**
 * 
 */
package com.hackerrank.sample.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * 
 * @author rajen.venkatraman
 *
 */
@Entity
@Table(name = "orders")
public class Order {

	@Id
	private Long orderId;

	private Long customerId;

	private double totalAmount;

	private String paymentChannel;

	private String shippingAddress;

	private String orderStatus;

	private Date orderCreatedOn;

	private boolean cod;

	@OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL)
	private List<OrderLineItem> orderLineItems;

	public Order() {

	}

	public Order(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the orderId
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the customerId
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the totalAmount
	 */
	public double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 *            the totalAmount to set
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the paymentChannel
	 */
	public String getPaymentChannel() {
		return paymentChannel;
	}

	/**
	 * @param paymentChannel
	 *            the paymentChannel to set
	 */
	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	/**
	 * @return the shippingAddress
	 */
	public String getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * @param shippingAddress
	 *            the shippingAddress to set
	 */
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus
	 *            the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return the orderCreatedOn
	 */
	public Date getOrderCreatedOn() {
		return orderCreatedOn;
	}

	/**
	 * @param orderCreatedOn
	 *            the orderCreatedOn to set
	 */
	public void setOrderCreatedOn(Date orderCreatedOn) {
		this.orderCreatedOn = orderCreatedOn;
	}

	/**
	 * @return the cod
	 */
	public boolean isCod() {
		return cod;
	}

	/**
	 * @param cod
	 *            the cod to set
	 */
	public void setCod(boolean cod) {
		this.cod = cod;
	}

	/**
	 * @return the orderLineItems
	 */
	public List<OrderLineItem> getOrderLineItems() {
		return orderLineItems;
	}

	/**
	 * @param orderLineItems
	 *            the orderLineItems to set
	 */
	public void setOrderLineItems(List<OrderLineItem> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}

}
