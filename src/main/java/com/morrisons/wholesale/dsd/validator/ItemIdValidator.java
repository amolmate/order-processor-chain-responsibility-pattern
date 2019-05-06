package com.morrisons.wholesale.dsd.validator;

import org.apache.commons.lang3.StringUtils;

import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.RedisCatlogueItem;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

public class ItemIdValidator extends BaseValidator<Item> {

	public ItemIdValidator(IBaseGetEndPoint<RedisCatlogueItem> redisCacheEndPoint) {

		super(redisCacheEndPoint);
	}

	@Override
	public boolean validate(Item data) {

		String itemIdFromRedisCatalogue = getItemIdFromRedisCatalogue(
				getItemFromRedisCatlogue(getParameterMappings(data)));

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

	private ParameterMappings getParameterMappings(Item data) {

		return null;
	}

	private String getItemIdFromRedisCatalogue(RedisCatlogueItem redisCatlogueItem) {

		// get itemId from CatlogueItem
		return redisCatlogueItem.getMin();
	}
}