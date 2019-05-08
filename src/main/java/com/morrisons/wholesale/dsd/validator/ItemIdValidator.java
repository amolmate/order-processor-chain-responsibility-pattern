package com.morrisons.wholesale.dsd.validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.RedisCatlogueItem;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
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

		ParameterMappings mappings = new ParameterMappings();
		List<ParameterMapping> pathParams = new ArrayList<>();
		pathParams.add(new ParameterMapping("customerId", data.getCustomerId()));
		pathParams.add(new ParameterMapping("itemId", data.getItemId()));
		mappings.setPathParameters(pathParams);
		return mappings;
	}

	private String getItemIdFromRedisCatalogue(RedisCatlogueItem redisCatlogueItem) {

		// get itemId from CatlogueItem
		return redisCatlogueItem.getMin();
	}
}