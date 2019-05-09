package com.morrisons.wholesale.dsd.validation.node.impl;

import com.morrisons.wholesale.dsd.constant.Constants;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.validation.node.INode;
import com.morrisons.wholesale.dsd.validation.validator.BaseValidator;

public class ItemCategoryValidatonNode extends BaseValidationNode<Item> {
	
	private static final String CATEGORY_MISMATCH = "categoryMismatch";

	public ItemCategoryValidatonNode(BaseValidator<Item> validator, INode<Item> nextNode) {
		
		super(validator, nextNode);
	}

	@Override
	protected void setNodeResult(Item item) {

		item.setStatus(CATEGORY_MISMATCH);
		item.setOrderLevelStatus(Constants.VALIDATION_ERROR_MANUAL);
	}
}