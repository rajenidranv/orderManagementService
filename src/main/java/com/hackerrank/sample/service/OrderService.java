package com.hackerrank.sample.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import com.hackerrank.sample.exception.BadResourceRequestException;
import com.hackerrank.sample.exception.NoSuchResourceFoundException;
import com.hackerrank.sample.config.ConfigProperty;
import com.hackerrank.sample.dto.DiscountDto;
import com.hackerrank.sample.model.Order;
import com.hackerrank.sample.model.OrderLineItem;
import com.hackerrank.sample.repository.OrderLineItemRepository;
import com.hackerrank.sample.repository.OrderRepository;

import org.springframework.web.client.*;

@org.springframework.stereotype.Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ConfigProperty configProperty;

	@Autowired
	RestTemplate restTemplate;

	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}
	
	@Autowired
	private OrderLineItemRepository orderLineItemRepository;

	public List<Object> getAllOrder() {
		List<Object> list = new ArrayList<Object>();
		orderRepository.findAll().forEach(e -> list.add(e));
		if (!list.isEmpty()) {
			return list;
		} else {
			throw new NoSuchResourceFoundException("Orders not available");
		}
	}

	public Order getOrder(Long id) {
		 java.util.Optional<Order> order = orderRepository.findById(id);
		return order.get();
	}
	
	public List<Object> getOrderByCustomer(Long id) {
		List<Object> list = new ArrayList<Object>();
		orderRepository.findByCustomerId(id).forEach(e -> list.add(e));
		if (!list.isEmpty()) {
			return list;
		} else {
			throw new NoSuchResourceFoundException("Orders not available");
		}
	}


	public Order addOrder(Order order) {
		Order saveOrder;
		System.out.println("OrderService addOrder ********* " + order.getOrderId());

		java.util.Optional<Order> orders = orderRepository.findById(order.getOrderId());

		if (orders.isPresent()) {
			throw new BadResourceRequestException("Order with same ID exists");
		}

		saveOrder = orderRepository.save(order);

		return saveOrder;
	}
	
	public List<Object> getOrderLineItem() {
		List<Object> list = new ArrayList<Object>();
		orderLineItemRepository.findAll().forEach(e -> list.add(e));
		if (!list.isEmpty()) {
			return list;
		} else {
			throw new NoSuchResourceFoundException("Orders not available");
		}
	}

	public boolean isOrderLineItemPresent(OrderLineItem orderLineItem) {
		OrderLineItem saveOrderItem;
		java.util.Optional<OrderLineItem> orderLineItems = orderLineItemRepository
				.findById(orderLineItem.getOrderLineItemId());

		if (orderLineItems.isPresent()) {
			throw new BadResourceRequestException("OrderLineItem with same ID exists");
		}
		return false;
	}
	
	public OrderLineItem addOrderLineItem(OrderLineItem orderLineItem) {
		OrderLineItem saveOrderItem;
		if (orderLineItemRepository.findById(orderLineItem.getOrderLineItemId()) != null) {
			System.out.println("**orderLineItemRepository.addOrderLineItem***");
			throw new BadResourceRequestException("Order with same ID exists");
		}

		saveOrderItem = orderLineItemRepository.save(orderLineItem);

		return saveOrderItem;
	}
	
	
	@HystrixCommand(fallbackMethod = "getDefaultDiscount", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000") })
	public DiscountDto getDiscountForOrder(double amount) {
		String url = configProperty.setDiscountAppEndPointUrl() + amount;
		System.out.println("**************" + url);
		HttpHeaders headers = getHeaders();
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<DiscountDto> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				DiscountDto.class, 1);
		DiscountDto discountDto = responseEntity.getBody();
		return discountDto;
	}
	
	private DiscountDto getDefaultDiscount(double amount) {
		System.out.println("**************getDefaultDiscount");
		return new DiscountDto(20, "50", "2019-05-01");
	}

	public void updateOrder(Order order) {
		orderRepository.save(order);
	}

	public void deleteOrder(Long id) {
		orderRepository.deleteById(id);
	}

	public void deleteAllOrder() {
		orderRepository.deleteAll();
	}
	
	/**
	 * 
	 * @return
	 */
	private HttpHeaders getHeaders() {
		String credential = configProperty.getBasicAutchInfo();
		String encodedCredential = new String(Base64.encodeBase64(credential.getBytes()));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Basic " + encodedCredential);
		return headers;
	}
}
