package com.morrisons.wholesale.dsd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AggregationPayload {

	@JsonProperty("id")
	private String id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("startTime")
	private String startTime;

	@JsonProperty("endTime")
	private String endTime;

	@JsonProperty("status")
	private String status;
}