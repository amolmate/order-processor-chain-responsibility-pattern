package com.morrisons.wholesale.dsd.validator;

import com.morrisons.wholesale.dsd.dto.Item;

public class ItemCaseSizeValidator extends BaseValidator<Item> {

	@Override
	public boolean validate(Item data) {
		return false;
	}
}