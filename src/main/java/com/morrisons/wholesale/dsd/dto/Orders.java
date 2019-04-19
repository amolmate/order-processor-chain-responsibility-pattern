
package com.morrisons.wholesale.dsd.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Orders {

	@JsonProperty("orders")
	private List<Order> orders;

	@JsonProperty("paginationMetaData")
	private PaginationMetaData paginationMetaData;
	
}