package com.morrisons.wholesale.dsd.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupportedSupplier {

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private String id;

	@JsonProperty("unsupportedStores")
	private List<Object> unsupportedStores;

	@JsonProperty("configuration")
	private Configuration configuration;

	@JsonProperty("itemBaseType")
	private String itemBaseType;

	@JsonProperty("identifier")
	private String identifier;
	
	@JsonProperty("uom")
	private String uom;
	
	@JsonProperty("itemCategory")
	private String itemCategory;
}