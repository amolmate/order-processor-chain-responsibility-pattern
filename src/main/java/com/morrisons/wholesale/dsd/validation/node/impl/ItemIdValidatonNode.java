package com.morrisons.wholesale.dsd.validation.node.impl;

import com.morrisons.wholesale.dsd.constant.Constants;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.validation.node.INode;
import com.morrisons.wholesale.dsd.validation.validator.BaseValidator;

public class ItemIdValidatonNode extends BaseValidationNode<Item> {

	private static final String ITEM_NOT_FOUND = "itemNotFound";

	public ItemIdValidatonNode(BaseValidator<Item> validator, INode<Item> nextNode) {
		super(validator, nextNode);
	}

	@Override
	protected void setNodeResult(Item item) {

		item.setStatus(ITEM_NOT_FOUND);
		item.setOrderLevelStatus(Constants.VALIDATION_ERROR_AUTO);
	}
}