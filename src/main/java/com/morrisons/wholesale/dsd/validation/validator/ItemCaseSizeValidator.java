package com.morrisons.wholesale.dsd.validation.validator;

import java.util.ArrayList;
import java.util.List;

import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.RedisCatlogueItem;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

public class ItemCaseSizeValidator extends BaseValidator<Item> {

	public ItemCaseSizeValidator(IBaseGetEndPoint<RedisCatlogueItem> redisCacheEndPoint) {

		super(redisCacheEndPoint);
	}

	@Override
	public boolean validate(Item data) {

		float itemCaseSizeFromRedisCatalogue = getItemCaseSizeFromRedisCatalogue(
				getItemFromRedisCatlogue(getParameterMappings(data)));

		return data.getCaseSize() == itemCaseSizeFromRedisCatalogue;
	}

	private float getItemCaseSizeFromRedisCatalogue(RedisCatlogueItem redisCatlogueItem) {

		return redisCatlogueItem.getItemCaseSize();
	}

	private ParameterMappings getParameterMappings(Item data) {

		ParameterMappings mappings = new ParameterMappings();
		List<ParameterMapping> pathParams = new ArrayList<>();
		pathParams.add(new ParameterMapping("customerId", data.getCustomerId()));
		pathParams.add(new ParameterMapping("itemId", data.getItemId()));
		mappings.setPathParameters(pathParams);
		return mappings;
	}
}