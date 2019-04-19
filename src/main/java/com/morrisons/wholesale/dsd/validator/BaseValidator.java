package com.morrisons.wholesale.dsd.validator;

import com.morrisons.wholesale.dsd.endpoint.impl.OrderServiceConfigDescriptor;

public abstract class BaseValidator<T> {

	protected OrderServiceConfigDescriptor orderServiceConfigDescriptor;

	public BaseValidator(OrderServiceConfigDescriptor orderServiceConfigDescriptor) {

		this.orderServiceConfigDescriptor = orderServiceConfigDescriptor;
	}

	public abstract boolean validate(T data);
}