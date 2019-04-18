package com.morrisons.wholesale.dsd.validation;

import com.morrisons.wholesale.dsd.constant.Constants;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.validator.BaseValidator;

public class ItemCaseSizeValidatonNode extends BaseValidationNode<Item, NodeResult> {

	private static final String CASE_SIZE_MISMATCH = "caseSizeMismatch";

	public ItemCaseSizeValidatonNode(BaseValidator<Item> validator, INode<Item, NodeResult> nextNode) {
		super(validator, nextNode);
	}

	@Override
	protected NodeResult getNodeResult(Item item) {
		
		NodeResult result = new NodeResult();
		result.setItem(item);
		result.setItemLevelStatus(CASE_SIZE_MISMATCH);
		result.setOrderLevelStatus(Constants.VALIDATION_ERROR_MANUAL);
		return result;
	}
}