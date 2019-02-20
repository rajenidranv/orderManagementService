/**
 * 
 */
package com.hackerrank.sample;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.sample.model.Customer;
import com.hackerrank.sample.model.Order;
import com.hackerrank.sample.model.OrderLineItem;

/**
 * @author rajen.venkatraman
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderManagementApplicationTest {
	/**
	 * 
	 */
	@Autowired
	private WebApplicationContext context;
	/**
	 * 
	 */
	private Order order;
	/**
	 * 
	 */
	private MockMvc mvc;
	/**
	 * 
	 */
	private String path = "/tcs/hack/v1";

	/**
	 * 
	 */
	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void addOrderTest() throws Exception {

		order = new Order();
		order.setOrderId(Long.valueOf(1005));
		order.setCod(true);
		order.setOrderStatus("Finished");
		order.setCustomerId(Long.valueOf(1001));
		order.setPaymentChannel("DC");
		order.setTotalAmount(150.00);
		order.setShippingAddress("Englewood");
		order.setOrderCreatedOn(new java.sql.Date(new Date().getTime()));

		List<OrderLineItem> orderLineItemList = new ArrayList<OrderLineItem>();
		OrderLineItem orderLineItem = new OrderLineItem();

		orderLineItem.setOrderLineItemId(Long.valueOf(2002));
		orderLineItem.setSkuId(Long.valueOf(2002));
		orderLineItem.setOrderId(order.getOrderId());
		orderLineItemList.add(orderLineItem);

		order.setOrderLineItems(orderLineItemList);

		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.post(path + "/order").content(toJson(order))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.orderId").isNumber()).andReturn();

		JSONObject json = new JSONObject(result.getResponse().getContentAsString());

		System.out.println(result.getResponse().getContentAsString());

		mvc.perform(
				MockMvcRequestBuilders.get(path + "/order/" + json.get("orderId")).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.orderId").value(json.get("orderId")))
				.andExpect(jsonPath("$.orderLineItems.[0].orderLineItemId").value(2002));

		mvc.perform(MockMvcRequestBuilders.get(path + "/order").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$[0].orderId").isNumber());

		mvc.perform(MockMvcRequestBuilders.post(path + "/order").content(toJson(order))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(content().string("OrderLineItem with same ID exists"));
	}

	@Test
	public void getAllOrdersTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(path + "/order").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$[0].orderId").isNumber())
				.andExpect(jsonPath("$[0].orderLineItems.[0].orderLineItemId").value(3001))
				.andExpect(jsonPath("$[0].orderLineItems.[1].orderLineItemId").value(3002));
	}

	@Test
	public void main_test() {
		OrderManagementApplication.main(new String[] {});
	}

	private byte[] toJson(Object r) throws Exception {
		ObjectMapper map = new ObjectMapper();
		return map.writeValueAsString(r).getBytes();
	}
}
