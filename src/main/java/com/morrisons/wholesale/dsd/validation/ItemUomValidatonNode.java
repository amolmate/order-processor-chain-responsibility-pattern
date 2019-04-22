package com.morrisons.wholesale.dsd.validation;

import com.morrisons.wholesale.dsd.constant.Constants;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.validator.BaseValidator;

public class ItemUomValidatonNode extends BaseValidationNode<Item> {

	private static final String UOM_MISMATCH = "uomMismatch";

	public ItemUomValidatonNode(BaseValidator<Item> validator, INode<Item> nextNode) {
		super(validator, nextNode);
	}

	@Override
	protected void setNodeResult(Item item) {

		item.setStatus(UOM_MISMATCH);
		item.setOrderLevelStatus(Constants.VALIDATION_ERROR_MANUAL);
	}
}