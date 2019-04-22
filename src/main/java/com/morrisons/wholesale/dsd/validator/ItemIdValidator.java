package com.morrisons.wholesale.dsd.validator;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Item;

public class ItemIdValidator extends BaseValidator<Item> {

	private Map<String, Map<String, String>> redisCatalogueItems;

	public ItemIdValidator(Customers customers) {

		super(customers);
	}

	public ItemIdValidator(Customers customers, Map<String, Map<String, String>> redisCatalogueItems) {

		super(customers);
		this.redisCatalogueItems = redisCatalogueItems;
	}

	@Override
	public boolean validate(Item data) {

		String itemIdFromRedisCatalogue = getItemIdFromRedisCatalogue(getItemFromRedisCatlogue(data));
		
		if (itemIdFromRedisCatalogue == null) {

			return false;
		} else {

			if (StringUtils.isNotBlank(data.getItemId())) {

				return data.getItemId().equals(itemIdFromRedisCatalogue);
			} else {

				return false;
			}
		}
	}

	private String getItemIdFromRedisCatalogue(Map<String, Object> map) {

		//get itemId from map
		return null;
	}
}