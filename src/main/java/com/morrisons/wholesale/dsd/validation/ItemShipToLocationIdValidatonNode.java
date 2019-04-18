package com.morrisons.wholesale.dsd.validation;

import com.morrisons.wholesale.dsd.constant.Constants;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.validator.BaseValidator;

public class ItemShipToLocationIdValidatonNode extends BaseValidationNode<Item, NodeResult> {

	private static final String SOTRE_MAPPING_ERROR = "storeMappingError";
	
	public ItemShipToLocationIdValidatonNode(BaseValidator<Item> validator, INode<Item, NodeResult> nextNode) {
		super(validator, nextNode);
	}

	@Override
	protected NodeResult getNodeResult(Item item) {

		NodeResult result = new NodeResult();
		result.setItem(item);
		result.setItemLevelStatus(SOTRE_MAPPING_ERROR);
		result.setOrderLevelStatus(Constants.VALIDATION_ERROR_MANUAL);
		return result;
	}
}