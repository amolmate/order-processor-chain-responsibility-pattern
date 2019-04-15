package com.morrisons.wholesale.dsd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginationMetaData {

	@JsonProperty("count")
	private float count;

	@JsonProperty("limit")
	private float limit;

	@JsonProperty("start")
	private float start;
}