package com.morrisons.wholesale.dsd.validation;

import com.morrisons.wholesale.dsd.constant.Constants;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.validator.BaseValidator;

public class ItemCaseSizeValidatonNode extends BaseValidationNode<Item> {

	private static final String CASE_SIZE_MISMATCH = "caseSizeMismatch";

	public ItemCaseSizeValidatonNode(BaseValidator<Item> validator, INode<Item> nextNode) {
		
		super(validator, nextNode);
	}

	@Override
	protected void setNodeResult(Item item) {
		
		item.setStatus(CASE_SIZE_MISMATCH);
		item.setOrderLevelStatus(Constants.VALIDATION_ERROR_MANUAL);
	}
}