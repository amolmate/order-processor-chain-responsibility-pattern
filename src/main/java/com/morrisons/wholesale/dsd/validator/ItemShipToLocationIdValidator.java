package com.morrisons.wholesale.dsd.validator;

import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Item;

public class ItemShipToLocationIdValidator extends BaseValidator<Item> {

	public ItemShipToLocationIdValidator(Customers customers) {
		
		super(customers);
	}

	@Override
	public boolean validate(Item data) {
		
		return false;
	}
}