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
	
	@JsonProperty("orderId")
	private String orderId;

	@JsonProperty("customerId")
	private String customerId;

	@JsonProperty("customerOrderId")
	private String customerOrderId;

	@JsonProperty("audit")
	private List<Audit> audit;
	
	@JsonProperty("items")
	private List<Item> items;

	@JsonProperty("morrisonsOrderId")
	private String morrisonsOrderId;
	
	@JsonProperty("supplierId")
	private String supplierId;
	
	@JsonProperty("channel")
	private String channel;

	@JsonProperty("orderAmount")
	private String orderAmount;

	@JsonProperty("orderCategory")
	private String orderCategory;
	
	@JsonProperty("orderSubCategory")
	private String orderSubCategory;

	@JsonProperty("shipmentInformation")
	private ShipmentInformation shipmentInformation;

	@JsonProperty("status")
	private String status;
	
	@JsonProperty("caseSize")
	private String caseSize;
	
	@JsonProperty("category")
	private String category;
	
	@JsonProperty("shipToLocationId")
	private String shipToLocationId;
	
	@JsonProperty("baseType")
	private String baseType;
}