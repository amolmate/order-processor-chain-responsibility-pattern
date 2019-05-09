package com.morrisons.wholesale.dsd.validation.node.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.morrisons.wholesale.dsd.constant.Constants;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.validation.node.INode;
import com.morrisons.wholesale.dsd.validation.validator.BaseValidator;

public class ItemBaseTypeValidatonNode extends BaseValidationNode<Item> {

	private static final String BASE_TYPE_MISMATCH = "baseTypeMismatch";

	@Autowired
	public ItemBaseTypeValidatonNode(BaseValidator<Item> itemBaseTypeValidator, INode<Item> nextNode) {

		super(itemBaseTypeValidator, nextNode);
	}

	@Override
	protected void setNodeResult(Item item) {

		item.setStatus(BASE_TYPE_MISMATCH);
		item.setOrderLevelStatus(Constants.VALIDATION_ERROR_MANUAL);
	}
}