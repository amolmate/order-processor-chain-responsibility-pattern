package com.morrisons.wholesale.dsd.validator;

import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Item;

public class ItemUomValidator extends BaseValidator<Item> {

	public ItemUomValidator(Customers customers) {

		super(customers);
	}

	@Override
	public boolean validate(Item data) {

		return false;
	}
}