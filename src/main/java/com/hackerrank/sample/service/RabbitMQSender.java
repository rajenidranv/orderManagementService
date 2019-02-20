/**
 * 
 */
package com.hackerrank.sample.service;

/**
 * @author rajen.venkatraman
 *
 */
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.hackerrank.sample.dto.OrderLineItemDto;

@Component
public class RabbitMQSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Value("${hackerrank.rabbitmq.exchange}")
	private String exchange;

	@Value("${hackerrank.rabbitmq.routingkey}")
	private String routingkey;

	public void send(OrderLineItemDto order) {

		String skuwithItem = order.getSkuId() + "-" + order.getItemQty();
		System.out.println("Send msg = " + exchange + skuwithItem);
		rabbitTemplate.convertAndSend(exchange, routingkey, skuwithItem);
		System.out.println("Send msg = " + exchange + order.toString());

	}

}
