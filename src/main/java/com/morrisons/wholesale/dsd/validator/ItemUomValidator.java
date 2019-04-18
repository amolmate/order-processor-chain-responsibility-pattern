package com.morrisons.wholesale.dsd.validator;

import com.morrisons.wholesale.dsd.dto.Item;

public class ItemUomValidator extends BaseValidator<Item> {

	@Override
	public boolean validate(Item data) {
		return false;
	}
}