package com.morrisons.wholesale.dsd.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Categories {

	@JsonProperty("categories")
	List<StoreCategory> storeCategories = new ArrayList<>();

	@JsonProperty("storeId")
	public String storeId;

	@JsonProperty("revisionId")
	public UUID revisionId;

	@JsonProperty("customerName")
	public String customerName;
}