package com.morrisons.wholesale.dsd.validation;

import com.morrisons.wholesale.dsd.constant.Constants;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.validator.BaseValidator;

public class ItemBaseTypeValidatonNode extends BaseValidationNode<Item> {

	private static final String BASE_TYPE_MISMATCH = "baseTypeMismatch";

	public ItemBaseTypeValidatonNode(BaseValidator<Item> itemBaseTypeValidator, INode<Item> nextNode) {

		super(itemBaseTypeValidator, nextNode);
	}

	@Override
	protected void setNodeResult(Item item) {
		
		item.setStatus(BASE_TYPE_MISMATCH);
		item.setOrderLevelStatus(Constants.VALIDATION_ERROR_MANUAL);
	}
}