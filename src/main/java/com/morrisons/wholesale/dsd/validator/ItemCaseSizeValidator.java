package com.morrisons.wholesale.dsd.validator;

import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.RedisCatlogueItem;
import com.morrisons.wholesale.dsd.endpoint.IRedisCacheEndPoint;

public class ItemCaseSizeValidator extends BaseValidator<Item> {

	private IRedisCacheEndPoint<String, RedisCatlogueItem> redisCacheEndPoint;

	public ItemCaseSizeValidator(IRedisCacheEndPoint<String, RedisCatlogueItem> redisCacheEndPoint) {

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