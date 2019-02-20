package com.hackerrank.sample.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "app")
@Component
public class ConfigProperty {

	/**
	 * 
	 */
	private String customerApiEndpoint;

	/**
	 * 
	 */

	private String inventoryAppEndPoint;

	/**
	 * 
	 */
	private String basicAutchInfo;
	/**
	 * 
	 */
	private boolean mailRequired;
	/**
	 * 
	 */
	private boolean customerApiCallRequired;

	/**
	 * 
	 */
	private String discountAppEndPointUrl;

	/**
	 * @return the customerApiEndpoint
	 */
	public String getCustomerApiEndpoint() {
		return customerApiEndpoint;
	}

	/**
	 * @param customerApiEndpoint
	 *            the customerApiEndpoint to set
	 */
	public void setCustomerApiEndpoint(String customerApiEndpoint) {
		this.customerApiEndpoint = customerApiEndpoint;
	}

	/**
	 * @return the basicAutchInfo
	 */
	public String getBasicAutchInfo() {
		return basicAutchInfo;
	}

	/**
	 * @param basicAutchInfo
	 *            the basicAutchInfo to set
	 */
	public void setBasicAutchInfo(String basicAutchInfo) {
		this.basicAutchInfo = basicAutchInfo;
	}

	/**
	 * @return the mailRequired
	 */
	public boolean isMailRequired() {
		return mailRequired;
	}

	/**
	 * @param mailRequired
	 *            the mailRequired to set
	 */
	public void setMailRequired(boolean mailRequired) {
		this.mailRequired = mailRequired;
	}

	/**
	 * @return the inventoryAppEndPoint
	 */
	public String getInventoryAppEndPoint() {
		return inventoryAppEndPoint;
	}

	/**
	 * @param inventoryAppEndPoint
	 *            the inventoryAppEndPoint to set
	 */
	public void setInventoryAppEndPoint(String inventoryAppEndPoint) {
		this.inventoryAppEndPoint = inventoryAppEndPoint;
	}

	/**
	 * @return the customerApiCallRequired
	 */
	public boolean isCustomerApiCallRequired() {
		return customerApiCallRequired;
	}

	/**
	 * @param customerApiCallRequired
	 *            the customerApiCallRequired to set
	 */
	public void setCustomerApiCallRequired(boolean customerApiCallRequired) {
		this.customerApiCallRequired = customerApiCallRequired;
	}

	/**
	 * @return the discountAppEndPointUrl
	 */
	public String setDiscountAppEndPointUrl() {
		return discountAppEndPointUrl;
	}

	/**
	 * @param discountAppEndPointUrl
	 *            the discountAppEndPointUrl to set
	 */
	public void setDiscountAppEndPointUrl(String discountAppEndPointUrl) {
		this.discountAppEndPointUrl = discountAppEndPointUrl;
	}
}