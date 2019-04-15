package com.morrisons.wholesale.dsd.validation;

import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.validator.BaseValidator;

public class ItemIdValidatonNode extends BaseValidationNode<Orders, NodeResult>  {

	public ItemIdValidatonNode(BaseValidator<Orders> validator, INode<Orders, NodeResult> nextNode) {
		super(validator, nextNode);
	}
}