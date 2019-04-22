package com.morrisons.wholesale.dsd.validation;

import java.util.Map;

import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.validator.ItemBaseTypeValidator;
import com.morrisons.wholesale.dsd.validator.ItemCaseSizeValidator;
import com.morrisons.wholesale.dsd.validator.ItemCategoryValidator;
import com.morrisons.wholesale.dsd.validator.ItemIdValidator;
import com.morrisons.wholesale.dsd.validator.ItemShipToLocationIdValidator;
import com.morrisons.wholesale.dsd.validator.ItemUomValidator;

public class ValidationNodeBuilder {

	private static INode<Item> validationNode;

	private ValidationNodeBuilder() {

	}

	public static INode<Item> build(Customers customers, Map<String, Map<String, String>> redisCatalogueItems) {

		if (validationNode != null) {

			return validationNode;
		} else {

			validationNode = new ItemBaseTypeValidatonNode(new ItemBaseTypeValidator(customers),
					new ItemCaseSizeValidatonNode(new ItemCaseSizeValidator(customers, redisCatalogueItems), new ItemCategoryValidatonNode(
							new ItemCategoryValidator(customers), new ItemIdValidatonNode(
									new ItemIdValidator(customers, redisCatalogueItems), new ItemShipToLocationIdValidatonNode(
											new ItemShipToLocationIdValidator(customers), new ItemUomValidatonNode(
													new ItemUomValidator(customers), null))))));
			return validationNode;
		}
	}
}