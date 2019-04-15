package com.morrisons.wholesale.dsd.validation;

import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.validator.BaseValidator;

public class ItemUomValidatonNode extends BaseValidationNode<Orders, NodeResult> {

	public ItemUomValidatonNode(BaseValidator<Orders> validator, INode<Orders, NodeResult> nextNode) {
		super(validator, nextNode);
	}
}