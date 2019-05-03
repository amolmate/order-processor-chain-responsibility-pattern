package com.morrisons.wholesale.dsd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DeliveryOpportunity {

	@JsonProperty("value")
	public String value;

	@JsonProperty("effectiveDate")
	public String effectiveDate;

	@JsonProperty("when")
	public String when;

	@JsonProperty("transitInformation")
	public TransitInformation transitInformation;
}