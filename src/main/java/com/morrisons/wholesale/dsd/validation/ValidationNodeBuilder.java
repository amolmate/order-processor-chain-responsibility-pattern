package com.morrisons.wholesale.dsd.validation;

import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.endpoint.impl.OrderServiceConfigDescriptor;
import com.morrisons.wholesale.dsd.validator.ItemBaseTypeValidator;
import com.morrisons.wholesale.dsd.validator.ItemCaseSizeValidator;
import com.morrisons.wholesale.dsd.validator.ItemCategoryValidator;
import com.morrisons.wholesale.dsd.validator.ItemIdValidator;
import com.morrisons.wholesale.dsd.validator.ItemShipToLocationIdValidator;
import com.morrisons.wholesale.dsd.validator.ItemUomValidator;

public class ValidationNodeBuilder {

	private static INode<Item, NodeResult> validationNode;

	private ValidationNodeBuilder() {

	}

	public static INode<Item, NodeResult> build(OrderServiceConfigDescriptor orderServiceConfigDescriptor) {

		if (validationNode != null) {

			return validationNode;
		} else {

			validationNode = new ItemBaseTypeValidatonNode(new ItemBaseTypeValidator(orderServiceConfigDescriptor),
					new ItemCaseSizeValidatonNode(new ItemCaseSizeValidator(),
							new ItemCategoryValidatonNode(new ItemCategoryValidator(),
									new ItemIdValidatonNode(new ItemIdValidator(),
											new ItemShipToLocationIdValidatonNode(new ItemShipToLocationIdValidator(),
													new ItemUomValidatonNode(new ItemUomValidator(), null))))));
			return validationNode;
		}
	}
}