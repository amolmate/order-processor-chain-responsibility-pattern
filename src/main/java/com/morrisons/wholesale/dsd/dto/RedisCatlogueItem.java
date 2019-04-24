package com.morrisons.wholesale.dsd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedisCatlogueItem {

	@JsonProperty("status")
	private String status;
	
	@JsonProperty("orderCategory")
	private String orderCategory;
	
	@JsonProperty("productSubCategory")
	private String productSubCategory;
	
	@JsonProperty("singlePick")
	private String singlePick;
	
	@JsonProperty("minShelfLife")
	private String minShelfLife;
	
	@JsonProperty("pinCatchWeightIndicator")
	private String pinCatchWeightIndicator;
	
	@JsonProperty("minSellingUOM")
	private String minSellingUOM;
	
	@JsonProperty("pinNetWeight")
	private String pinNetWeight;
	 
	@JsonProperty("catchWeightType")
	private String catchWeightType;
	 
	@JsonProperty("clientId")
	private String clientId;
	 
	@JsonProperty("lpc")
	private String lpc;
	
	@JsonProperty("min")
	private String min;
	
	@JsonProperty("pin")
	private String pin;
	 
	@JsonProperty("wsp")
	private String wsp;
	 
	@JsonProperty("wscp")
	private String wscp;
	 
	@JsonProperty("cs")
	private String cs;
	 
	@JsonProperty("vat")
	private String vat;
	 
	@JsonProperty("currency")
	private String currency;
	 
	@JsonProperty("identifier")
	private String identifier;
	 
	@JsonProperty("itemDescription")
	private String itemDescription;
	
	@JsonProperty("availabilityStatus")
	private String availabilityStatus;
}