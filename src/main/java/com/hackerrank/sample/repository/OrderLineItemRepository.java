package com.hackerrank.sample.repository;

import org.springframework.data.repository.CrudRepository;

import com.hackerrank.sample.model.OrderLineItem;

public interface OrderLineItemRepository extends CrudRepository<OrderLineItem, Long> {

}
