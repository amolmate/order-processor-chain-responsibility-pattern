package com.morrisons.wholesale.dsd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TransitInformation {

	@JsonProperty("supplyingDepotLocation")
	public String supplyingDepotLocation;

	@JsonProperty("supplyingShippingLocation")
	public String supplyingShippingLocation;

	@JsonProperty("virtualSellingLocation")
	public String virtualSellingLocation;
}