package com.morrisons.wholesale.dsd.validator;

import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.RedisCatlogueItem;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;

public class ItemCaseSizeValidator extends BaseValidator<Item> {

	private IBaseGetEndPoint<RedisCatlogueItem> redisCacheEndPoint;

	public ItemCaseSizeValidator(IBaseGetEndPoint<RedisCatlogueItem> redisCacheEndPoint) {

		super(redisCacheEndPoint);
		this.redisCacheEndPoint = redisCacheEndPoint;
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