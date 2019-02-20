/**
 * OrderController.java
 */
package com.hackerrank.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpStatus;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.*;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.sample.config.ConfigProperty;
import com.hackerrank.sample.dto.CustomerDto;
import com.hackerrank.sample.dto.OrderDto;
import com.hackerrank.sample.dto.OrderLineItemDto;
import com.hackerrank.sample.exception.BadResourceRequestException;
import com.hackerrank.sample.exception.NoSuchResourceFoundException;
import com.hackerrank.sample.model.Customer;
import com.hackerrank.sample.model.Order;
import com.hackerrank.sample.model.OrderLineItem;
import com.hackerrank.sample.service.OrderService;
import com.hackerrank.sample.service.RabbitMQSender;

/**
 * 
 * @author rajen.venkatraman
 *
 */
@RestController
@RequestMapping("/tcs/hack/v1")
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private ConfigProperty configProperty;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private JavaMailSender sender;

	@Autowired
	RabbitMQSender rabbitMQSender;

	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}

	@GetMapping("/order")
	public ResponseEntity<Object> getAllOrders() throws Exception {
		ResponseEntity<Object> response = null;
		try {
			List<Object> ordersList = orderService.getAllOrder();
			List<OrderDto> ordersDto = new ArrayList<OrderDto>();

			for (Object orderObject : ordersList) {
				if (orderObject instanceof Order) {
					List<OrderLineItemDto> orderLineItemDtoList = new ArrayList<OrderLineItemDto>();
					Order order = (Order) orderObject;
					OrderDto orderDto = new OrderDto();
					orderDto.setOrderId(order.getOrderId());
					HttpHeaders headers = getHeaders();
					HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
					
					if (configProperty.isCustomerApiCallRequired()) {
						String url = configProperty.getCustomerApiEndpoint() + order.getCustomerId().toString();
						ResponseEntity<CustomerDto> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
								requestEntity, CustomerDto.class, 1);
						CustomerDto customer = responseEntity.getBody();
						orderDto.setCustomerDto(customer);
					}
					orderDto.setOrderStatus(order.getOrderStatus());
					orderDto.setPaymentChannel(order.getPaymentChannel());
					orderDto.setTotalAmount(order.getTotalAmount());

					List<OrderLineItem> items = order.getOrderLineItems();
					items.forEach(item -> {
						OrderLineItemDto orderLineItemDto = new OrderLineItemDto();
						orderLineItemDto.setOrderLineItemId(item.getOrderLineItemId());
						orderLineItemDto.setOrderId(item.getOrderId());
						orderLineItemDto.setItemQty(item.getItemQty());
						orderLineItemDto.setSkuId(item.getSkuId());
						orderLineItemDtoList.add(orderLineItemDto);
					});

					orderDto.setOrderLineItems(orderLineItemDtoList);
					ordersDto.add(orderDto);
				}
			}

			response = new ResponseEntity<Object>(ordersDto, HttpStatus.OK);
		} catch (NoSuchResourceFoundException bex) {
			response = new ResponseEntity<Object>("Orders not available", HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@GetMapping("/order/{id}")
	public ResponseEntity<Object> getAllOrder(@PathVariable("id") Long id) throws Exception {
		Object order = orderService.getOrder(id);
		if (order != null) {
			return new ResponseEntity<Object>(order, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Order not found", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/order/customer/{id}")
	public ResponseEntity<Object> getAllOrderByCustomer(@PathVariable("id") Long id) throws Exception {
		List<Object> ordersList = orderService.getOrderByCustomer(id);
		if (!ordersList.isEmpty()) {
			return new ResponseEntity<Object>(ordersList, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Order not found", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/order")
	public ResponseEntity<Object> addOrder(@RequestBody Order order) throws Exception {
		ResponseEntity<Object> response = null;
		try {
			for (OrderLineItem item : order.getOrderLineItems()) {
				orderService.isOrderLineItemPresent(item);
			}
			response = new ResponseEntity<Object>(orderService.addOrder(order), HttpStatus.CREATED);
			
			orderService.getDiscountForOrder(order.getTotalAmount());

			List<OrderLineItem> orderItems = order.getOrderLineItems();

			if (!orderItems.isEmpty()) {

				orderItems.forEach(orderItem -> {
					OrderLineItemDto orderLIdto = new OrderLineItemDto();
					orderLIdto.setSkuId(orderItem.getSkuId());
					orderLIdto.setItemQty(orderItem.getItemQty());
					rabbitMQSender.send(orderLIdto);
				});
			}
			
			

			if (configProperty.isMailRequired())
				sendMail();
		} catch (BadResourceRequestException bex) {
			response = new ResponseEntity<Object>(bex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@PutMapping("/order/{id}")
	public ResponseEntity<Object> updateOrder(@RequestBody Order order, @PathVariable Long id) throws Exception {
		Object orderFromDb = orderService.getOrder(id);
		if (orderFromDb != null) {
			orderService.updateOrder(order);
			return new ResponseEntity<Object>(order, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("No order found to update", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/order/{id}")
	public ResponseEntity<Object> deleteOrder(@PathVariable("id") Long id) {
		Object order = orderService.getOrder(id);
		if (order != null) {
			orderService.deleteOrder(id);
			return new ResponseEntity<Object>("Order is deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Order not found to be deleted", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/orders")
	public ResponseEntity<Object> deleteAllOrders() {
		orderService.deleteAllOrder();
		return new ResponseEntity<Object>("Orders are deleted", HttpStatus.OK);
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

	/**
	 * 
	 * @return
	 */
	private String sendMail() {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setTo("rajendiranv@gmail.com");
			helper.setText("Greetings :)");
			helper.setSubject("Mail From Order Management");
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error while sending mail ..";
		}
		sender.send(message);
		return "Mail Sent Success!";
	}
}
