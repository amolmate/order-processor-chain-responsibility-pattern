package com.morrisons.wholesale.dsd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Audit {

	@JsonProperty("when")
	private String when;

	@JsonProperty("who")
	private String who;
	
	@JsonProperty("eventName")
	private String eventName;
}