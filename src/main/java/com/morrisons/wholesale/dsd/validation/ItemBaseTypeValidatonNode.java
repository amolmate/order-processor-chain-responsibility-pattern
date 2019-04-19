package com.morrisons.wholesale.dsd.validation;

import com.morrisons.wholesale.dsd.constant.Constants;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.validator.BaseValidator;

public class ItemBaseTypeValidatonNode extends BaseValidationNode<Item, NodeResult> {

	private static final String BASE_TYPE_MISMATCH = "baseTypeMismatch";

	public ItemBaseTypeValidatonNode(BaseValidator<Item> itemBaseTypeValidator, INode<Item, NodeResult> nextNode) {

		super(itemBaseTypeValidator, nextNode);
	}

	@Override
	protected NodeResult getNodeResult(Item item) {

		NodeResult result = new NodeResult();
		result.setItem(item);
		result.setItemLevelStatus(BASE_TYPE_MISMATCH);
		item.setStatus(BASE_TYPE_MISMATCH);
		result.setOrderLevelStatus(Constants.VALIDATION_ERROR_MANUAL);
		return result;
	}
}