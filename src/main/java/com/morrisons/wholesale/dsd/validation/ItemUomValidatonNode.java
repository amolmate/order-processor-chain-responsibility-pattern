package com.morrisons.wholesale.dsd.validation;

import com.morrisons.wholesale.dsd.constant.Constants;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.validator.BaseValidator;

public class ItemUomValidatonNode extends BaseValidationNode<Item, NodeResult> {

	private static final String UOM_MISMATCH = "uomMismatch";
	
	public ItemUomValidatonNode(BaseValidator<Item> validator, INode<Item, NodeResult> nextNode) {
		super(validator, nextNode);
	}

	@Override
	protected NodeResult getNodeResult(Item item) {
		
		NodeResult result = new NodeResult();
		result.setItem(item);
		result.setItemLevelStatus(UOM_MISMATCH);
		result.setOrderLevelStatus(Constants.VALIDATION_ERROR_MANUAL);
		return result;
	}
}