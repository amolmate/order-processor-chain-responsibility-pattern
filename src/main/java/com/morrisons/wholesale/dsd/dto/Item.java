package com.morrisons.wholesale.dsd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

	@JsonProperty("caseSize")
	private String caseSize;

	@JsonProperty("deliveryDate")
	private String deliveryDate;

	@JsonProperty("itemId")
	private String itemId;

	@JsonProperty("itemIdentifierType")
	private String itemIdentifierType;

	@JsonProperty("orderPrice")
	private String orderPrice;

	@JsonProperty("quantity")
	private String quantity;

	@JsonProperty("referenceId")
	private String referenceId;

	@JsonProperty("status")
	private String status;

	@JsonProperty("uom")
	private String uom;
}