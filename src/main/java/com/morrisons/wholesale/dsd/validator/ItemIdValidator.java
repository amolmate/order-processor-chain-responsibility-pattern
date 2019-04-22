package com.morrisons.wholesale.dsd.validator;

import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Item;

public class ItemIdValidator extends BaseValidator<Item> {

	public ItemIdValidator(Customers customers) {

		super(customers);
	}

	@Override
	public boolean validate(Item data) {

		return false;
	}

}