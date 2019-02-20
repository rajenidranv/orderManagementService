package com.hackerrank.sample.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hackerrank.sample.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

	List<Object> findByCustomerId(Long id);

}
