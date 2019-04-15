package com.morrisons.wholesale.dsd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipmentInformation {

	@JsonProperty("deliveryDate")
	private String deliveryDate;

	@JsonProperty("deliveryFrom")
	private String deliveryFrom;

	@JsonProperty("deliveryTo")
	private String deliveryTo;

	@JsonProperty("shipFromLocationId")
	private String shipFromLocationId;

	@JsonProperty("shipmentLocationId")
	private String shipmentLocationId;
}