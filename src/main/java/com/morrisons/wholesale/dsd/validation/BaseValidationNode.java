package com.morrisons.wholesale.dsd.validation;

import com.morrisons.wholesale.dsd.validator.BaseValidator;

public abstract class BaseValidationNode<T> implements INode<T> {

	protected INode<T> nextNode;
	
	protected BaseValidator<T> baseValidator;
	
	public BaseValidationNode(BaseValidator<T> validator, INode<T> node) {
		
		baseValidator = validator;
		this.nextNode = node;
	}
	
	@Override
	public void processNode(T data) {

		if(baseValidator.validate(data)) {
			
			if(nextNode == null) {
				
				return;
			}
			
			//next step
			nextNode.processNode(data);
		} else {
			
			//mark status at item and order level
			//return and continue for nextItem
			setNodeResult(data);
		}
	}
	
	protected abstract void setNodeResult(T data);
}