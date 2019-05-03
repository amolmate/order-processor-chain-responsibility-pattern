package com.morrisons.wholesale.dsd.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StoreCategory {

	@JsonProperty("name")
	public String name;

	@JsonProperty("deliveryOpportunities")
	public List<DeliveryOpportunity> deliveryOpportunities;
}