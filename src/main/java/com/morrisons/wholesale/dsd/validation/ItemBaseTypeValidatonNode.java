package com.morrisons.wholesale.dsd.validation;

import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.validator.BaseValidator;

public class ItemBaseTypeValidatonNode extends BaseValidationNode<Orders, NodeResult> {

	public ItemBaseTypeValidatonNode(BaseValidator<Orders> itemBaseTypeValidator, INode<Orders, NodeResult> nextNode) {

		super(itemBaseTypeValidator, nextNode);
	}
}