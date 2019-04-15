package com.morrisons.wholesale.dsd.validation;

import com.morrisons.wholesale.dsd.validator.BaseValidator;

public abstract class BaseValidationNode<T, R> implements INode<T, R> {

	protected INode<T, R> nextNode;
	
	protected BaseValidator<T> baseValidator;
	
	public BaseValidationNode(BaseValidator<T> validator, INode<T, R> node) {
		
		baseValidator = validator;
		this.nextNode = node;
	}
	
	@Override
	public R processNode(T data) {

		if(baseValidator.validate(data)) {
			//next step
			nextNode.processNode(data);
		} else {
			
			//return and continue for nextItem
		}
		
		return null;
	}
}