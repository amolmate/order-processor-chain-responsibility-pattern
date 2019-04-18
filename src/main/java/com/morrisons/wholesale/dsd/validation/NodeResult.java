package com.morrisons.wholesale.dsd.validation;

import com.morrisons.wholesale.dsd.dto.Item;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NodeResult {

	private String itemLevelStatus;

	private String orderLevelStatus;

	private Item item;
}