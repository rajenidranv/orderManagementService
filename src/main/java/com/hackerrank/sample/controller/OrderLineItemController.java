package com.hackerrank.sample.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.sample.dto.CustomerDto;
import com.hackerrank.sample.dto.OrderDto;
import com.hackerrank.sample.dto.OrderLineItemDto;
import com.hackerrank.sample.exception.BadResourceRequestException;
import com.hackerrank.sample.exception.NoSuchResourceFoundException;
import com.hackerrank.sample.model.Customer;
import com.hackerrank.sample.model.Order;
import com.hackerrank.sample.model.OrderLineItem;
import com.hackerrank.sample.service.OrderService;

@RestController
@RequestMapping("/tcs/hack/v1")
public class OrderLineItemController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/orderItem")
	public ResponseEntity<Object> getAllOrders() throws Exception {
		ResponseEntity<Object> response = null;
		try {
			List<Object> ordersList = orderService.getOrderLineItem();
			response = new ResponseEntity<Object>(ordersList, HttpStatus.OK);
		} catch (NoSuchResourceFoundException bex) {
			response = new ResponseEntity<Object>("Orders not available", HttpStatus.NOT_FOUND);
		}
		return response;
	}

}
