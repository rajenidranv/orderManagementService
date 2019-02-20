/**
 * 
 */
package com.hackerrank.sample.dto;

import java.sql.Date;
import java.util.List;

import com.hackerrank.sample.model.Customer;

/**
 * @author rajen.venkatraman
 *
 */
public class OrderDto {

	private Long orderId;

	private CustomerDto customerDto;

	private double totalAmount;

	private String paymentChannel;

	private String shippingAddress;

	private String orderStatus;

	private Date orderCreatedOn;

	private boolean cod;

	private List<OrderLineItemDto> orderLineItems;

	public OrderDto() {

	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getOrderCreatedOn() {
		return orderCreatedOn;
	}

	public void setOrderCreatedOn(Date orderCreatedOn) {
		this.orderCreatedOn = orderCreatedOn;
	}

	public boolean isCod() {
		return cod;
	}

	public void setCod(boolean cod) {
		this.cod = cod;
	}

	public List<OrderLineItemDto> getOrderLineItems() {
		return orderLineItems;
	}

	public void setOrderLineItems(List<OrderLineItemDto> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}

	public CustomerDto getCustomerDto() {
		return customerDto;
	}

	public void setCustomerDto(CustomerDto customerDto) {
		this.customerDto = customerDto;
	}

}
