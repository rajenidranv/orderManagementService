/**
 * 
 */
package com.hackerrank.sample.dto;

/**
 * @author rajen.venkatraman
 *
 */
public class DiscountDto {

	private int id;
	private String valuePercentage;
	private String expiresBy;

	public DiscountDto() {

	}

	public DiscountDto(int id, String valuePercentage, String expiresBy) {
		this.id = id;
		this.valuePercentage = valuePercentage;
		this.expiresBy = expiresBy;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the valuePercentage
	 */
	public String getValuePercentage() {
		return valuePercentage;
	}

	/**
	 * @param valuePercentage
	 *            the valuePercentage to set
	 */
	public void setValuePercentage(String valuePercentage) {
		this.valuePercentage = valuePercentage;
	}

	/**
	 * @return the expiresBy
	 */
	public String getExpiresBy() {
		return expiresBy;
	}

	/**
	 * @param expiresBy
	 *            the expiresBy to set
	 */
	public void setExpiresBy(String expiresBy) {
		this.expiresBy = expiresBy;
	}

}
