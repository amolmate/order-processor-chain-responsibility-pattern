package com.morrisons.wholesale.dsd.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

	@JsonProperty("caseSize")
	private float caseSize;

	@JsonProperty("deliveryDate")
	private String deliveryDate;

	@JsonProperty("itemId")
	private String itemId;

	@JsonProperty("itemIdentifierType")
	private String itemIdentifierType;

	@JsonProperty("orderPrice")
	private float orderPrice;

	@JsonProperty("quantity")
	private String quantity;

	@JsonProperty("referenceId")
	private String referenceId;

	@JsonProperty("itemBaseType")
	private String itemBaseType;

	@JsonProperty("quantityOrder")
	private float quantityOrder;

	@JsonProperty("customerId")
	private float customerId;

	@JsonProperty("audit")
	private List<Audit> audit;

	@JsonProperty("morrisonsOrderId")
	private String morrisonsOrderId;

	@JsonProperty("status")
	private String status;

	@JsonProperty("uom")
	private String uom;

	private String customerName;

	private String supplierName;
	
	private String orderLevelStatus;
	
	private boolean isItemValidated;
}