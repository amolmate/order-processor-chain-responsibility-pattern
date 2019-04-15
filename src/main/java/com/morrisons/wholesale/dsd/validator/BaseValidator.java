package com.morrisons.wholesale.dsd.validator;

public abstract class BaseValidator<T> {

	public abstract boolean validate(T data);

}
