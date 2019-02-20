package com.hackerrank.sample.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hackerrank.sample.config.ConfigProperty;
import com.hackerrank.sample.dto.CustomerDto;
import com.hackerrank.sample.dto.InventoryDto;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.*;
import org.springframework.http.HttpStatus;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author rajen.venkatraman
 */
@Component
public class SchedulerInventoryCheck {

	@Autowired
	RestTemplate restTemplateClient;

	@Autowired
	private ConfigProperty configProperty;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	private static final Logger logger = LoggerFactory.getLogger(SchedulerInventoryCheck.class);

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Scheduled(fixedRate = 600000)
	public void scheduleTaskWithFixedRate() {
		logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));

		ObjectMapper objectMapper = new ObjectMapper();

		HttpHeaders headers = getHeaders();
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		String url = configProperty.getInventoryAppEndPoint();
		ResponseEntity<String> result = restTemplateClient.exchange(url, HttpMethod.GET, requestEntity, String.class);

		try {
			List<InventoryDto> items = objectMapper.readValue(result.getBody().toString(),
					new TypeReference<List<InventoryDto>>() {
					});

			items.forEach(item -> {

				int inventoryOnHand = item.getInventoryOnHand();
				int minQty = item.getMinQtyReq();
				if (inventoryOnHand < minQty) {
					inventoryOnHand = inventoryOnHand + minQty + 10;
					item.setInventoryOnHand(inventoryOnHand);
				}

			});

		} catch (Exception e) {

		}

	}

	private HttpHeaders getHeaders() {
		String credential = configProperty.getBasicAutchInfo();
		String encodedCredential = new String(Base64.encodeBase64(credential.getBytes()));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Basic " + encodedCredential);
		return headers;
	}

	private LinkedHashMap<Object, Object> toObject(Object r) throws Exception {
		ObjectMapper map = new ObjectMapper();
		LinkedHashMap<Object, Object> obj = (LinkedHashMap<Object, Object>) map.readValue(r.toString(), Object.class);

		return obj;

	}

}