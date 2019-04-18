package com.morrisons.wholesale.dsd.validation;

import com.morrisons.wholesale.dsd.constant.Constants;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.validator.BaseValidator;

public class ItemIdValidatonNode extends BaseValidationNode<Item, NodeResult> {

	private static final String ITEM_NOT_FOUND = "itemNotFound";
	
	public ItemIdValidatonNode(BaseValidator<Item> validator, INode<Item, NodeResult> nextNode) {
		super(validator, nextNode);
	}

	@Override
	protected NodeResult getNodeResult(Item item) {

		NodeResult result = new NodeResult();
		result.setItem(item);
		result.setItemLevelStatus(ITEM_NOT_FOUND);
		result.setOrderLevelStatus(Constants.VALIDATION_ERROR_AUTO);
		return result;
	}
}