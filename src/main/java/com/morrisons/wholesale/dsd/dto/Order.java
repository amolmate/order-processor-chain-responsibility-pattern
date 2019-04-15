package com.morrisons.wholesale.dsd.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

	@JsonProperty("customerId")
	private String customerId;

	@JsonProperty("customerOrderId")
	private String customerOrderId;

	@JsonProperty("items")
	private List<Item> items;

	@JsonProperty("morrisonsOrderId")
	private String morrisonsOrderId;

	@JsonProperty("orderAmount")
	private String orderAmount;

	@JsonProperty("orderCategory")
	private String orderCategory;

	@JsonProperty("shipmentInformation")
	private ShipmentInformation shipmentInformation;

	@JsonProperty("status")
	private String status;
}