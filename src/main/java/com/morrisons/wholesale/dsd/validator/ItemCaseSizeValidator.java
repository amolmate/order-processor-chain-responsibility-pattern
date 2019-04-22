package com.morrisons.wholesale.dsd.validator;

import java.util.Map;

import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Item;

public class ItemCaseSizeValidator extends BaseValidator<Item> {

	private Map<String, Map<String, String>> redisCatalogueItems;

	public ItemCaseSizeValidator(Customers customers) {

		super(customers);
	}

	public ItemCaseSizeValidator(Customers customers, Map<String, Map<String, String>> redisCatalogueItems) {

		super(customers);
		this.redisCatalogueItems = redisCatalogueItems;
	}

	@Override
	public boolean validate(Item data) {

		float itemCaseSizeFromRedisCatalogue = getItemCaseSizeFromRedisCatalogue();

		return data.getCaseSize() == itemCaseSizeFromRedisCatalogue;
	}

	private float getItemCaseSizeFromRedisCatalogue() {

		return 0;
	}
}