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
			
			//mark status at item and order level
			//return and continue for nextItem
			return getNodeResult(data);
		}
		
		return null;
	}
	
	protected abstract R getNodeResult(T data);
}