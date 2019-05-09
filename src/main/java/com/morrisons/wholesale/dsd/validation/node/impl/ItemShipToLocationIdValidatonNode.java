package com.morrisons.wholesale.dsd.validation.node.impl;

import com.morrisons.wholesale.dsd.constant.Constants;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.validation.node.INode;
import com.morrisons.wholesale.dsd.validation.validator.BaseValidator;

public class ItemShipToLocationIdValidatonNode extends BaseValidationNode<Item> {

	private static final String SOTRE_MAPPING_ERROR = "storeMappingError";

	public ItemShipToLocationIdValidatonNode(BaseValidator<Item> validator, INode<Item> nextNode) {

		super(validator, nextNode);
	}

	@Override
	protected void setNodeResult(Item item) {

		item.setStatus(SOTRE_MAPPING_ERROR);
		item.setOrderLevelStatus(Constants.VALIDATION_ERROR_MANUAL);
		item.setItemValidated(true);
	}
}