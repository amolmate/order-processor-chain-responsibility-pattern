package com.morrisons.wholesale.dsd.validator;

import com.morrisons.wholesale.dsd.dto.Order;

public class ItemCaseSizeValidator extends BaseValidator<Order>  {

	@Override
	public boolean validate(Order data) {
		return false;
	}
}