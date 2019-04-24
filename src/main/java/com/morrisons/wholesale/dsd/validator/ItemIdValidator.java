package com.morrisons.wholesale.dsd.validator;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.RedisCatlogueItem;
import com.morrisons.wholesale.dsd.endpoint.IRedisCacheEndPoint;

public class ItemIdValidator extends BaseValidator<Item> {

	private IRedisCacheEndPoint<String, RedisCatlogueItem> redisCacheEndPoint;

	public ItemIdValidator(Customers customers, IRedisCacheEndPoint<String, RedisCatlogueItem> redisCacheEndPoint) {

		super(customers);
		this.redisCacheEndPoint = redisCacheEndPoint;
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

		// get itemId from map
		return null;
	}
}