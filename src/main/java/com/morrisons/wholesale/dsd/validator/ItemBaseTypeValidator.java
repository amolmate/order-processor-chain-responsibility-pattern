package com.morrisons.wholesale.dsd.validator;

import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.endpoint.impl.OrderServiceConfigDescriptor;

public class ItemBaseTypeValidator extends BaseValidator<Item> {

	
	
	public ItemBaseTypeValidator(OrderServiceConfigDescriptor orderServiceConfigDescriptor) {
		
		super(orderServiceConfigDescriptor);
	}

	@Override
	public boolean validate(Item data) {
		
		data.getItemBaseType();
		return false;
	}
}